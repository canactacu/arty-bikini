package ru.arty_bikini.crm.web;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.dto.orders.google.OrderDataTypeDTO;
import ru.arty_bikini.crm.data.orders.google.OrderDataTypeEntity;
import ru.arty_bikini.crm.dto.packet.ordet_data.EditTypeRequest;
import ru.arty_bikini.crm.dto.packet.ordet_data.EditTypeResponse;
import ru.arty_bikini.crm.dto.packet.ordet_data.GetTypesResponse;
import ru.arty_bikini.crm.jpa.OrderDataTypeRepository;
import ru.arty_bikini.crm.jpa.SessionRepository;
import ru.arty_bikini.crm.servise.GoogleService;

import java.util.List;

///api/order-data/edit-type   изменить столбик гугол(имя, таргет в системе)
// /api/order-data/get-types       получить список всех столбиков
///api/order-data/import-google              Импорт данных из гугла
///api/order-data/import-result              получиьб результатц импорта
///api/order-data/link-order-to-import              привязать заказ к результату импорта
@RestController//контролерр
@RequestMapping("/api/order-data")
public class OrderDataController {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private OrderDataTypeRepository orderDataTypeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GoogleService googleService;



    @PostMapping("/get-types")//получить список всех столбиков
    @ResponseBody
    public GetTypesResponse getTypes(@RequestParam String key){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new GetTypesResponse("нет сессии", null);
        }
        //проверка на права доступа
        if(session.getUser().getGroup().canViewColumnForGoogle == true){

            //идем в бд колонок
            List<OrderDataTypeEntity> orderAll = orderDataTypeRepository.findAll();

            //переделываем в дто
            List<OrderDataTypeDTO> orderDataTypeDTOS = objectMapper.convertValue(orderAll, new TypeReference<List<OrderDataTypeDTO>>() {
            });

            //выдаем результат
            return new GetTypesResponse("список колонок гугол отправлен", orderDataTypeDTOS);

        }
        return new GetTypesResponse("нет сессии", null);
    }

    @PostMapping("/edit-type")//изменить столбик гугол(имя в системе)
    @ResponseBody
    public EditTypeResponse editType(@RequestParam String key, @RequestBody EditTypeRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new EditTypeResponse("нет сессии", null);
        }
        //проверка на права доступа
        if(session.getUser().getGroup().canEditColumnForGoogle == true){

            OrderDataTypeDTO orderDataTypeDTO = body.getOrderDataType();//столбик, кот нам передали

            //зайти в бд найти его(нужную колонку) там по ид например
            OrderDataTypeEntity orderDataTypeEntity = orderDataTypeRepository.getById(orderDataTypeDTO.getId());
            if(orderDataTypeEntity == null){//колонка и таким ид не найдена
                return new EditTypeResponse("колонка и таким ид не найдена", null);

            }

            //положить в бд новый
            orderDataTypeEntity.setName(body.getOrderDataType().getName());
            orderDataTypeEntity.setTarget(body.getOrderDataType().getTarget());
            //orderDataTypeEntity.setGoogleColumn(body.getOrderDataType().getGoogleColumn());
            //orderDataTypeEntity.setGoogleName(body.getOrderDataType().getGoogleName());

            //соранить бд
            OrderDataTypeEntity save = orderDataTypeRepository.save(orderDataTypeEntity);

            //переделать назад в дто
            OrderDataTypeDTO orderDataTypeDTO1 = objectMapper.convertValue(save, OrderDataTypeDTO.class);

            return new EditTypeResponse("колонка исправлена", orderDataTypeDTO1);
        }
        return new EditTypeResponse("нет сессии", null);
    }

    @PostMapping("/import-google")// Импорт данных из гугла
    @ResponseBody
    public String importGoogle(@RequestParam String key){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return ("нет сессии");
        }
        if(session.getUser().getGroup().canEditColumnForGoogle == true){

            googleService.doImport();

            return ("вошли в функцию");
        }
        return ("нет сессии");
    }
}


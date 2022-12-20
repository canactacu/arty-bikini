package ru.arty_bikini.crm.web;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.data.orders.google.DataGoogleEntity;
import ru.arty_bikini.crm.data.orders.google.MeasureVariantsEntity;
import ru.arty_bikini.crm.dto.orders.google.MeasureVariantsDTO;
import ru.arty_bikini.crm.dto.packet.ordet_data.*;
import ru.arty_bikini.crm.dto.orders.OrderDTO;
import ru.arty_bikini.crm.dto.orders.google.DataGoogleDTO;
import ru.arty_bikini.crm.dto.orders.google.OrderDataTypeDTO;
import ru.arty_bikini.crm.data.orders.google.OrderDataTypeEntity;
import ru.arty_bikini.crm.jpa.*;
import ru.arty_bikini.crm.servise.ColumnService;
import ru.arty_bikini.crm.servise.DictionaryService;
import ru.arty_bikini.crm.servise.GoogleService;
import ru.arty_bikini.crm.servise.OrderService;

import java.util.ArrayList;
import java.util.List;

///api/order-data/edit-type +  изменить столбик гугол(имя, таргет в системе)
// /api/order-data/get-types    +   получить список всех столбиков
///api/order-data/import-google         +     Импорт данных из гугла
///api/order-data/import-result       +       получить результаты импорта
///api/order-data/link-order-to-import      +        привязать заказ к результату импорта
///api/order-data/unlink-order-from-import      +        отвязать заказ от гугл данных

///api/order-data/get-measure-variants  + получить всех measure-variants
///api/order-data/edit-measure-variants  +  изменить,добавить measure-variants
///api/order-data/del-measure-variants  -   удалить,добавить measure-variants


//api/order-data/edit-value +

@RestController
@RequestMapping("/api/order-data")
public class OrderDataController {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private OrderDataTypeRepository orderDataTypeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GoogleService googleService;

    @Autowired
    private DataGoogleRepository dataGoogleRepository;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private MeasureVariantsRepository measureVariantsRepository;
    
    @Autowired
    private DictionaryService dictionaryService;
    
    @Autowired
    private ColumnService columnService;


    @PostMapping("/get-types")//получить список всех столбиков
    @ResponseBody
    public GetTypesResponse getTypes(@RequestParam String key){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new GetTypesResponse("нет сессии", null);
        }
        //проверка на права доступа
        if(session.getUser().getGroup().canViewColumn == true){

            //идем в бд колонок
            List<OrderDataTypeEntity> orderAll = orderDataTypeRepository.findAll();

            //переделываем в дто
            List<OrderDataTypeDTO> orderDataTypeDTOS = new ArrayList<>();
            for (int i = 0; i < orderAll.size(); i++) {
                orderDataTypeDTOS.add(columnService.toDTO(orderAll.get(i)));
            }
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
        if(session.getUser().getGroup().canEditColumn == true){
            OrderDataTypeDTO orderDataTypeDTO = body.getOrderDataType();//столбик, кот нам передали
            OrderDataTypeEntity orderDataTypeEntity;
            
            //добавляем колонку
            if (body.getOrderDataType().getId() == 0) {
                orderDataTypeEntity = new OrderDataTypeEntity();
                orderDataTypeEntity.setId(0);
                orderDataTypeEntity.setGoogleName("*Не импортируется*");
                orderDataTypeEntity.setGoogleColumn(-1);
            }
            else {//редактируем колонку
                 orderDataTypeEntity = orderDataTypeRepository.getById(orderDataTypeDTO.getId());
                if(orderDataTypeEntity == null){//колонка и таким ид не найдена
                    return new EditTypeResponse("колонка и таким ид не найдена", null);
        
                }
            }

            //положить в бд новый
            orderDataTypeEntity.setName(body.getOrderDataType().getName());
            orderDataTypeEntity.setTarget(body.getOrderDataType().getTarget());
            orderDataTypeEntity.setDisplayCategory(body.getOrderDataType().getDisplayCategory());
            orderDataTypeEntity.setDisplayPosition(body.getOrderDataType().getDisplayPosition());
            
            String json = dictionaryService.productToJson(body.getOrderDataType().getProducts());
            if (json == null){
                return new EditTypeResponse("json == null", null);
            }
            orderDataTypeEntity.setProductJson(json);
            //orderDataTypeEntity.setGoogleColumn(body.getOrderDataType().getGoogleColumn());
            //orderDataTypeEntity.setGoogleName(body.getOrderDataType().getGoogleName());

            //сохранить бд
            OrderDataTypeEntity save = orderDataTypeRepository.save(orderDataTypeEntity);

            //переделать назад в дто
            OrderDataTypeDTO saveDTO = columnService.toDTO(save);
            
            return new EditTypeResponse("колонка исправлена", saveDTO);
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
        if(session.getUser().getGroup().canEditOrder == true){

            googleService.doImport();

            return ("сделали импорт");
        }
        return ("нет сессии");
    }

    @PostMapping("/import-result")// получить результаты импорта
    @ResponseBody
    public ImportResultResponse importResult(@RequestParam String key){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new ImportResultResponse ("нет сессии", null);
        }
        //проверка на права доступа
        if(session.getUser().getGroup().canEditOrder == true){

            //достать из бд всех, кроме connected=true
            List<DataGoogleEntity> byConnected = dataGoogleRepository.getByConnected(false);

            List<DataGoogleDTO> dataGoogleDTOS = objectMapper.convertValue(byConnected, new TypeReference<List<DataGoogleDTO>>() {});
            if (dataGoogleDTOS.size() == 0){
                return new ImportResultResponse ("список отправлен, но он пуст", dataGoogleDTOS);
            }

            return new ImportResultResponse ("список отправлен", dataGoogleDTOS);
        }
        return new ImportResultResponse ("нет сессии", null);
    }

    @PostMapping("/link-order-to-import")//  привязать заказ к результату импорта
    @ResponseBody
    public LinkOrderToImportResponse linkOrderToImport(@RequestParam String key, @RequestBody LinkOrderToImportRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new LinkOrderToImportResponse ("нет сессии", null);
        }
        if(session.getUser().getGroup().canEditOrder == true){

            //найти в бд такого клиента
            OrderEntity order = orderRepository.getById(body.getIdOrder());
            if (order == null){
                return new LinkOrderToImportResponse ("нет такого клиента в бд", null);
            }

            //найти в бд такой гугол ответ
            DataGoogleEntity google = dataGoogleRepository.getById(body.getIdDataGoogle());
            if (google == null){
                return new LinkOrderToImportResponse ("нет такого клиента в гугол бд", null);
            }

            google.setConnected(true);
            dataGoogleRepository.save(google);

            order.setDataGoogle(google);
            OrderEntity save = orderRepository.save(order);
            OrderDTO toOrderDTO = orderService.toOrderDTO(save);
            orderService.savePackageTime(order);//сохранили и посчитали дату отправки
            return new LinkOrderToImportResponse ("соединили обьединили", toOrderDTO);

        }
        return new LinkOrderToImportResponse ("нет сессии", null);
    }

    @PostMapping("/unlink-order-from-import")//     отвязать заказ от гугл данных
    @ResponseBody
    public LinkOrderToImportResponse unlinkOrderFromImport(@RequestParam String key, @RequestBody UnlinkOrderFromImportRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new LinkOrderToImportResponse ("нет сессии", null);
        }
        if(session.getUser().getGroup().canEditOrder == true){

            //найти в бд такого клиента
            OrderEntity order = orderRepository.getById(body.getIdOrder());
            if (order == null){
                return new LinkOrderToImportResponse ("нет такого клиента в бд", null);
            }

            //найти в бд такой гугол ответ
            DataGoogleEntity dataGoogle = order.getDataGoogle();
            if (dataGoogle == null){
                return new LinkOrderToImportResponse ("клиент есть, не соединен с гуголом", null);
            }

            dataGoogle.setConnected(false);
            dataGoogleRepository.save(dataGoogle);

            order.setDataGoogle(null);
            if (order.getPersonalData() != null) {
                order.getPersonalData().setPackageTime(null);
            }
            orderService.savePackageTime(order);//сохранили и посчитали дату отправки
            
            OrderEntity save = orderRepository.save(order);
            OrderDTO toOrderDTO = orderService.toOrderDTO(save);
            return new LinkOrderToImportResponse ("разъединен", toOrderDTO);

        }
        return new LinkOrderToImportResponse ("нет сессии", null);
    }
    
    @PostMapping("/get-measure-variants")//    получить всех measure-variants
    @ResponseBody
    public GetMeasureVariantsResponse getMeasureVariants(@RequestParam String key){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new GetMeasureVariantsResponse ("нет сессии", null);
        }
        if(session.getUser().getGroup().canViewOrder == true){
            List<MeasureVariantsEntity> all = measureVariantsRepository.findAll();
    
            List<MeasureVariantsDTO> measureVariantsDTOS = objectMapper.convertValue(all, new TypeReference<List<MeasureVariantsDTO>>() {});
            return new GetMeasureVariantsResponse ("список отправлен", measureVariantsDTOS);
            
        }
        return new GetMeasureVariantsResponse ("нет сессии", null);
    }
    
    @PostMapping("/edit-measure-variants")  //  изменить, добавить   measure-variants
    @ResponseBody
    public EditMeasureVariantsResponse editMeasureVariants(@RequestParam String key, @RequestBody EditMeasureVariantsRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new EditMeasureVariantsResponse ("нет сессии", null);
        }
        if(session.getUser().getGroup().canEditColumn == true){
    
            MeasureVariantsEntity measureVariants;
            if(body.getMeasureVariantsDTO().getId() == 0){
                measureVariants = new MeasureVariantsEntity();
                measureVariants.setId(0);
            }
            else {
                measureVariants = measureVariantsRepository.getById(body.getMeasureVariantsDTO().getId());
                if(measureVariants == null){
                    return new EditMeasureVariantsResponse ("нет id", null);
                }
            }
            
            measureVariants.setName(body.getMeasureVariantsDTO().getName());
            measureVariants.setDescription(body.getMeasureVariantsDTO().getDescription());
            measureVariants.setGoogleName(body.getMeasureVariantsDTO().getGoogleName());
            measureVariants.setPriority(body.getMeasureVariantsDTO().getPriority());
    
            String json = dictionaryService.productToJson(body.getMeasureVariantsDTO().getProducts());
            if (json == null) {
                return new EditMeasureVariantsResponse ("нет product", null);
            }
            measureVariants.setProductsJson(json);
    
            OrderDataTypeEntity orderDataTypeEntity = orderDataTypeRepository.getById(body.getMeasureVariantsDTO().getOrderDataType().getId());
            if (orderDataTypeEntity == null){
                return new EditMeasureVariantsResponse ("orderDataTypeEntity == null", null);
            }
            measureVariants.setOrderDataType(orderDataTypeEntity);
    
            
            MeasureVariantsEntity save = measureVariantsRepository.save(measureVariants);
    
            MeasureVariantsDTO measureVariantsDTO = columnService.toDTO(save);
    
            return new EditMeasureVariantsResponse ("изменен, добавлен", measureVariantsDTO);
            
        }
        return new EditMeasureVariantsResponse ("нет сессии", null);
    }
    
    @PostMapping("/del-measure-variants")  //  удалить   measure-variants
    @ResponseBody
    public DelMeasureVariantsResponse editMeasureVariants(@RequestParam String key, @RequestBody DelMeasureVariantsRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new DelMeasureVariantsResponse ("нет сессии");
        }
        if(session.getUser().getGroup().canEditColumn == true){
            MeasureVariantsEntity measureVariants = measureVariantsRepository.getById(body.getMeasureVariantsDTO().getId());
            measureVariantsRepository.delete(measureVariants);
            return new DelMeasureVariantsResponse ("удален");
            
        }
        return new DelMeasureVariantsResponse ("нет сессии");
    }
    
    @PostMapping("/edit-value")//в форме мерках
    @ResponseBody
    public EditValueResponse editValue(@RequestParam String key, @RequestBody EditValueRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new EditValueResponse ("нет сессии", null);
        }
        if(session.getUser().getGroup().canEditOrder == true){
    
            OrderEntity order = orderRepository.getById(body.getOrderId());
            if (order == null) {
                return new EditValueResponse ("order == null", null);
            }
            
            OrderDataTypeEntity orderDataType = orderDataTypeRepository.getById(body.getOrderDataTypeId());
            if (orderDataType == null) {
                return new EditValueResponse ("orderDataType == null", null);
            }
    
    
            String measuresToJson = columnService.measuresToJson(order.getMeasuresJson(), orderDataType, body.getValue());
            order.setMeasuresJson(measuresToJson);
    
            OrderEntity save = orderRepository.save(order);
    
            OrderDTO orderDTO = orderService.toOrderDTO(save);
    
            return new EditValueResponse ("значения изменены", orderDTO);
        }
        return new EditValueResponse ("нет сессии", null);
    }
    
}


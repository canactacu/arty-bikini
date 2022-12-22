package ru.arty_bikini.crm.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.data.orders.stone.CalcPresetEntity;
import ru.arty_bikini.crm.data.orders.stone.OrderRhinestoneAmountEntity;
import ru.arty_bikini.crm.data.dict.RhinestoneTypeEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.dto.orders.stone.CalcPresetDTO;
import ru.arty_bikini.crm.dto.orders.stone.OrderRhinestoneAmountDTO;
import ru.arty_bikini.crm.dto.packet.order_stone.*;
import ru.arty_bikini.crm.jpa.*;
import ru.arty_bikini.crm.servise.OrderStoneService;

import java.util.List;

// Сохранить стразы + /api/order-stone/save на заказ
//Получить список пресетов + /api/order-stone/get-presets
//добавить 1 пресета + /api/order-stone/add-presets
//изменить 1 пресета  +  /api/order-stone/edit-presets

@RestController
@RequestMapping("/api/order-stone")
public class OrderStoneController {
    @Autowired
    private SessionRepository sessionRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private RhinestoneTypeRepository rhinestoneTypeRepository;
    
    @Autowired
    private OrderRhinestoneAmountRepository orderRARepository;
    
    @Autowired
    private CalcPresetRepository calcPresetRepository;
    
    @Autowired
    private OrderStoneService orderStoneService;
    
    @PostMapping("/save")//Сохранить стразы на заказ
    @ResponseBody
    public SaveResponse save(@RequestParam String key, @RequestBody SaveRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);//вытащили нужный кеу, если такого нет, вернули null
        if (session == null){
            return new SaveResponse("нет сессии");
        }
        //проверка прав на добавление
        if(session.getUser().getGroup().canEditOrder == true){
    
            OrderEntity order = orderRepository.getById(body.getIdOrder());
            if (order == null) {
                return new SaveResponse("нет такого id order");
            }
            //проверяем совпадают ли id заказа у всех переданных
            for (int i = 0; i < body.getOrderRhinestoneAmountDTOList().size(); i++) {
    
                if (body.getOrderRhinestoneAmountDTOList().get(i).getOrder()!= null) {
    
                 
                        if (i == 0) {
                            if (body.getOrderRhinestoneAmountDTOList().get(i).getOrder().getId() != body.getIdOrder()) {
                                return new SaveResponse("не совпадают id");
                            }
                        } else {
                            if (body.getOrderRhinestoneAmountDTOList().get(i).getOrder().getId() !=
                                    body.getOrderRhinestoneAmountDTOList().get(i - 1).getOrder().getId()) {
                                return new SaveResponse("не совпадают id");
                            }
                        }
                    
                }
                
            }
    
            List<OrderRhinestoneAmountEntity> list = orderRARepository.getByOrder(order);//удалили order из бд orderRARepository с нужными id
            for (int i = 0; i < list.size(); i++) {
                orderRARepository.delete(list.get(i));
            }
    
            for (int i = 0; i < body.getOrderRhinestoneAmountDTOList().size(); i++) {
                OrderRhinestoneAmountDTO orderRhinestoneAmountDTO = body.getOrderRhinestoneAmountDTOList().get(i);
    
                if (body.getOrderRhinestoneAmountDTOList().get(i).getRhinestoneType() == null) {
                    return new SaveResponse(" RhinestoneType== null в DTO");
                }
                RhinestoneTypeEntity rhinestoneType = rhinestoneTypeRepository.getById(body.getOrderRhinestoneAmountDTOList().get(i).getRhinestoneType().getId());
                if (rhinestoneType == null) {
                    return new SaveResponse(" RhinestoneType== null в бд");
                }
                //добавляем версию
                order.setVersion(order.getVersion() + 1);
                orderRepository.save(order);
    
                OrderRhinestoneAmountEntity orderRhinestoneAmountEntity = new OrderRhinestoneAmountEntity();
    
                orderRhinestoneAmountEntity.setId(0);
                orderRhinestoneAmountEntity.setOrder(order);
                orderRhinestoneAmountEntity.setRhinestoneType(rhinestoneType);
                orderRhinestoneAmountEntity.setCount(orderRhinestoneAmountDTO.getCount());
    
                OrderRhinestoneAmountEntity save = orderRARepository.save(orderRhinestoneAmountEntity);
            }
            
            return new SaveResponse("сохранили");
        }
        return new SaveResponse("нет сессии");
    }
    
    @PostMapping("/get-presets")//Получить список пресетов
    @ResponseBody
    public GetPresetsResponse getPresets(@RequestParam String key){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);//вытащили нужный кеу, если такого нет, вернули null
        if (session == null){
            return new GetPresetsResponse("нет сессии", null);
        }
        //проверка прав на добавление
        if(session.getUser().getGroup().canViewOrder == true){
            List<CalcPresetEntity> all = calcPresetRepository.findAll();
    
            List<CalcPresetDTO> calcPresetDTOList = orderStoneService.toDTOS(all);
            
            return new GetPresetsResponse("сохранили", calcPresetDTOList);
        }
        return new GetPresetsResponse("нет сессии", null);
    }
    
    @PostMapping("/add-presets") //добавить 1 пресета - /api/order-stone/add-presets
    @ResponseBody
    public AddPresetsResponse addPresets(@RequestParam String key, @RequestBody AddPresetsRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);//вытащили нужный кеу, если такого нет, вернули null
        if (session == null){
            return new AddPresetsResponse("нет сессии", null);
        }
        if(session.getUser().getGroup().canEditOrder == true){
    
            if (body.getCalcPresetDTO().getId() != 0) {
                return new AddPresetsResponse("id != 0", null);
            }
            CalcPresetEntity calcPresetEntity = new CalcPresetEntity();
            orderStoneService.toEntity(body.getCalcPresetDTO(), calcPresetEntity);
            
            CalcPresetEntity save = calcPresetRepository.save(calcPresetEntity);
            CalcPresetDTO calcPresetDTO = orderStoneService.toDTO(save);
    
            return new AddPresetsResponse("добавили", calcPresetDTO);
        }
        return new AddPresetsResponse("нет сессии", null);
    }
    
    @PostMapping("/edit-presets") //изменить 1 пресета - /api/order-stone/edit-presets
    @ResponseBody
    public EditPresetsResponse editPresets(@RequestParam String key, @RequestBody EditPresetsRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);//вытащили нужный кеу, если такого нет, вернули null
        if (session == null){
            return new EditPresetsResponse("нет сессии", null);
        }
        if(session.getUser().getGroup().canEditOrder == true){
            
            if (body.getCalcPresetDTO().getId() == 0) {
                return new EditPresetsResponse("id == 0", null);
            }
            
            CalcPresetEntity calcPreset = calcPresetRepository.getById(body.getCalcPresetDTO().getId());
            if (calcPreset == null) {
                return new EditPresetsResponse("id нет в бд", null);
            }
            orderStoneService.toEntity(body.getCalcPresetDTO(), calcPreset);
            
            CalcPresetEntity save = calcPresetRepository.save(calcPreset);
            
            CalcPresetDTO calcPresetDTO = orderStoneService.toDTO(save);
            
            return new EditPresetsResponse("изменили", calcPresetDTO);
        }
        return new EditPresetsResponse("нет сессии", null);
    }
}


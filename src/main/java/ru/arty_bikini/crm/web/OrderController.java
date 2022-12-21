package ru.arty_bikini.crm.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.data.dict.ExpressEntity;
import ru.arty_bikini.crm.data.dict.ProductTypeEntity;
import ru.arty_bikini.crm.data.dict.TrainerEntity;
import ru.arty_bikini.crm.data.orders.*;
import ru.arty_bikini.crm.data.orders.google.DataGoogleEntity;
import ru.arty_bikini.crm.data.orders.stone.CalcPresetRuleJson;
import ru.arty_bikini.crm.dto.PageDTO;
import ru.arty_bikini.crm.dto.enums.UserGroup;
import ru.arty_bikini.crm.dto.enums.personalData.ClientType;
import ru.arty_bikini.crm.dto.orders.OrderDTO;
import ru.arty_bikini.crm.dto.orders.stone.CalcPresetRuleDTO;
import ru.arty_bikini.crm.dto.packet.order.*;
import ru.arty_bikini.crm.jpa.*;
import ru.arty_bikini.crm.servise.OrderService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

///api/order/get-clients + //получить список клиентов
///api/order/get-leads  +  //получить список лидов и клиентов с предоплатой и без мерок или без дизайна
///api/order/add-client + //добавить лида-клиента
///api/order/edit-client + //изменить клиента
//Добавить точку + /api/order/get-order
///api/order/get-archive + //архив по страницам
///api/order/get-order-by-trainer +  //получить список заказов по тренеру

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ExpressRepository expressRepository;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private DataGoogleRepository dataGoogleRepository;
    
    @Autowired
    public ProductTypeRepository productTypeRepository;


    @PostMapping("/get-clients")//получить список клиентов
    @ResponseBody
    public GetClientsResponse getClients(@RequestParam String key){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new GetClientsResponse("нет сессии", null, null);
        }
        //проверка на права доступа
        if(session.getUser().getGroup().canViewOrder == true){

            //сходить в бд//получить клиентов
            List<OrderEntity> all = orderRepository.findAllByType(ClientType.CLIENT);//получила все клиентов

            //получить клиентов из всех


            //сделать DTO
            List<OrderDTO> orderDTOS = orderService.toOrderDTOList(all);

            return new GetClientsResponse("переданы клиенты", orderDTOS, null);
        }
        return new GetClientsResponse("нет сессии", null, null);
    }

    @PostMapping("/get-leads")//получить список лидов и клиентов с предоплатой и без мерок или без дизайна
    @ResponseBody
    public GetClientsResponse getLeads(@RequestParam String key){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new GetClientsResponse("нет сессии", null, null);
        }
        //проверка на права доступа
        if(session.getUser().getGroup().canViewOrder == true){

            List<OrderEntity> all = orderRepository.findAllByType(ClientType.LEAD);//список всех лидов
            List<OrderDTO> orderLeadDTOS = orderService.toOrderDTOList(all);
            
            //список только клиентов,  у кого нет мерок и дизайна
            List<OrderEntity> orderClientList =
                    orderRepository.findByTypeAndStatusInfoMeasureAllOrTypeAndStatusInfoDesignAll
                            (ClientType.CLIENT, false, ClientType.CLIENT,false);
            
            List<OrderDTO> orderClientDTOS = orderService.toOrderDTOList(orderClientList);
    
            //сделать DTO
            // List<OrderDTO> orderDTOS = orderService.toOrderDTOList(all);
            return new GetClientsResponse("переданы лиды", orderLeadDTOS, orderClientDTOS);
        }
        return new GetClientsResponse("нет сессии", null, null);
    }

    @PostMapping("/get-archive")//получить список архив
    @ResponseBody
    public GetArchiveResponse getArchive(@RequestParam String key, @RequestParam int page){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new GetArchiveResponse("нет сессии", null);
        }
        //проверка на права доступа
        if(session.getUser().getGroup().canViewOrder == true){

            //сходить в бд//получить клиентов
            Page<OrderEntity> all = orderRepository.findAllByType(ClientType.ARCHIVE, Pageable.ofSize(25).withPage(page));

            //сделать DTO

            PageDTO<OrderDTO> pageDTO = new PageDTO<>(
                    orderService.toOrderDTOList(all.getContent()),
                    all.getNumber(),
                    all.getSize(),
                    all.getTotalElements(),
                    all.getTotalPages()
            );

            return new GetArchiveResponse("переданы", pageDTO);
        }
        return new GetArchiveResponse("нет сессии", null);
    }

    @PostMapping("/add-client")//добавить лида-клиента
    @ResponseBody
    public AddClientResponse addClient(@RequestParam String key, @RequestParam String name, @RequestParam ClientType type){
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new AddClientResponse("нет сессии", null);
        }
        //проверка прав на добавление
        if(session.getUser().getGroup().canEditOrder == true){

            //создать 1 клиента
            OrderEntity order = new OrderEntity();

            //заполнили этого клиента
            order.setId(0);
            order.setName(name);
            order.setType(type);
    
            PersonalData personalData = new PersonalData();
            order.setPersonalData(personalData);
            order.getPersonalData().setDeliveryTime(21);
            order.getPersonalData().setCreatedTime(LocalDate.now());
    
            Design design = new Design();
            LeadInfo leadInfo = new LeadInfo();
            StatusInfo statusInfo = new StatusInfo();
            
            order.setDesign(design);
            order.setLeadInfo(leadInfo);
            order.setStatusInfo(statusInfo);
            
            //сохранить в бд
           orderRepository.save(order);

            //в dto
            OrderDTO orderDTO = orderService.toOrderDTO(order);
           return  new AddClientResponse("клиента-лида передали", orderDTO);
        }
        return new AddClientResponse("нет сессии", null);
    }

    @PostMapping("/edit-client")//изменить лида-клиента
    @ResponseBody
    public EditClientResponse editClient(@RequestParam String key, @RequestBody EditClientRequest body) {
    
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);//выташили нужный кеу,если такого нет, вернули null
        if (session == null) {
            return new EditClientResponse("нет сессии", null);
        }
        //проверка прав на: изменить клиента
        if (session.getUser().getGroup().canEditOrder == true) {
    
            //OrderDto->OrderEntity
            //получить из бд Order проверить,что не null
            OrderEntity order = orderRepository.getById(body.getOrder().getId());
            if (order == null) {
                return new EditClientResponse("нет такого Id", null);
            }
    
            //примитивы в OrderEntity заполнить в ручную @Embedable
            order.setName(body.getOrder().getName());
            order.setType(body.getOrder().getType());
            
    
            //все остальное заполнить через класс маппер
            PersonalData personalData = objectMapper.convertValue(body.getOrder().getPersonalData(), PersonalData.class);
            Design design = objectMapper.convertValue(body.getOrder().getDesign(), Design.class);//->DTO
            LeadInfo leadInfo = objectMapper.convertValue(body.getOrder().getLeadInfo(), LeadInfo.class);
            //проверить все вложенные *Entity (по ID сходить в бд и заменить на то, что вернет гибернайт)
    
            if (body.getOrder().getExpress() != null) {
                ExpressEntity express = expressRepository.getById(body.getOrder().getExpress().getId());
                if (express == null) {
                    return new EditClientResponse("express == null, не заполнено ", null);
                }
                order.setExpress(express);
            }
            
            if (body.getOrder().getProduct() != null) {
                ProductTypeEntity productType = productTypeRepository.getById(body.getOrder().getProduct().getId());
                if (productType == null) {
                    return new EditClientResponse("productType == null, не заполнено ", null);
                }
                order.setProduct(productType);
            }
    
            if (body.getOrder().getDataGoogle() != null) {
                DataGoogleEntity dataGoogle = dataGoogleRepository.getById(body.getOrder().getDataGoogle().getId());
                if (dataGoogle == null) {
                    return new EditClientResponse("dataGoogle == null, не заполнено ", null);
                }
                order.setDataGoogle(dataGoogle);
            }
    
            if (personalData != null) {
        
                if (personalData.getTrainer() != null) {
                    TrainerEntity trainer = trainerRepository.getById(personalData.getTrainer().getId());
                    if (trainer == null) {
                        return new EditClientResponse("trainer == null, не заполнено ", null);
                    }
                    personalData.setTrainer(trainer);
                }
            }
            
            //проставляем, кто заполнил мерки и дизайн и статусы
            StatusInfo statusInfo = order.getStatusInfo();
            if (body.getOrder().getStatusInfo() != null){
                if (statusInfo == null) {
                    statusInfo = new StatusInfo();
                }
                statusInfo.setMeasureAll(body.getOrder().getStatusInfo().isMeasureAll());
                statusInfo.setDesignAll(body.getOrder().getStatusInfo().isDesignAll());
                
                if (session.getUser().getGroup() == UserGroup.TANYA) {
                    statusInfo.setMeasureAllTanya(body.getOrder().getStatusInfo().isMeasureAllTanya());
                    if (!statusInfo.isMeasureAll()){
                        statusInfo.setMeasureAll(true);
                        statusInfo.setMeasureBy(session.getUser());
                    }
                    statusInfo.setDesignAllTanya(body.getOrder().getStatusInfo().isDesignAllTanya());
                    if (!statusInfo.isDesignAll()){
                        statusInfo.setDesignAll(true);
                        statusInfo.setDesignBy(session.getUser());
                    }
                }
                
                if (statusInfo.isDesignAll() && (order.getStatusInfo() == null || order.getStatusInfo().isDesignAll() == false)) {
                    statusInfo.setDesignBy(session.getUser());
                }
                
                if (statusInfo.isMeasureAll() && (order.getStatusInfo() == null || order.getStatusInfo().isMeasureAll() == false)) {
                    statusInfo.setMeasureBy(session.getUser());
                }
            }
            
            //заполняем
            order.setPersonalData(personalData);
            order.setDesign(design);
            order.setLeadInfo(leadInfo);
            order.setStatusInfo(statusInfo);
    
            //ставим дату предоплаты
            if (order.getPersonalData()!= null) {
    
                if (order.getPersonalData().getOrderTime() == null) {
    
                    if (body.getOrder().getPersonalData() != null) {
    
                        if (body.getOrder().getPersonalData().getPrepayment() != null) {
    
                            if (order.getPersonalData() == null) {
    
                                order.setPersonalData(new PersonalData());
                            }
    
                            order.getPersonalData().setOrderTime(LocalDate.now());
                        }
                    }
                }
            }
    
            //считаем дату отправки
            orderService.savePackageTime(order);
            
            
            //ставим дату попадания в архив
            if (order.getType() == null){
                return new EditClientResponse("нет статуса у клиента", null);
            }
            else {
                if((order.getType().equals(ClientType.ARCHIVE))){
                    if (order.getPersonalData()== null){
                        PersonalData personalData1 = new PersonalData();
                        order.setPersonalData(personalData1);
                        order.getPersonalData().setArchiveTime(LocalDate.now());
                        
                        if (body.getOrder().getLeadInfo()!= null){
                            order.getLeadInfo().setCommentArchive(body.getOrder().getLeadInfo().getCommentArchive());
                        }
                        else {
                            return new EditClientResponse("нет комментария архива", null);
                        }
                    }
                    else {
                        if (order.getPersonalData().getArchiveTime() == null){
                            order.getPersonalData().setArchiveTime(LocalDate.now());
                            
                            if (body.getOrder().getLeadInfo()!= null){
                                order.getLeadInfo().setCommentArchive(body.getOrder().getLeadInfo().getCommentArchive());
                            }
                            else {
                                return new EditClientResponse("нет комментария архива", null);
                            }
                        }
                    }
                    
                }
            }
            
           //сохраняем поле presetRulesJson и presetRules
            if (body.getOrder().getPresetRules()!= null) {
                if (body.getOrder().getPresetRules().size() != 0) {
                   
                    String presetRulesJson = null;
                    List<CalcPresetRuleDTO> calcPresetRuleDTOList = body.getOrder().getPresetRules();
                    List<CalcPresetRuleJson> calcPresetRuleJsonList = new ArrayList<>();
                    
                    for (int i = 0; i < calcPresetRuleDTOList.size(); i++) {
                        
                        calcPresetRuleJsonList.add(objectMapper.convertValue(calcPresetRuleDTOList.get(i), CalcPresetRuleJson.class));
                        
                        if (calcPresetRuleDTOList.get(i).getStone() != null) {
                            calcPresetRuleJsonList.get(i).setStoneId(calcPresetRuleDTOList.get(i).getStone().getId());
                        }
                    }
                    try {
                        presetRulesJson = objectMapper.writeValueAsString(calcPresetRuleJsonList);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    order.setPresetRulesJson(presetRulesJson);
        
                }
            }
            
            //сохранить в бд
            OrderEntity save = orderRepository.save(order);
    
    
            //переделать в DTO
            OrderDTO orderDTO = orderService.toOrderDTO(save);
            return new EditClientResponse("данные исправлены", orderDTO);
        }
        return new EditClientResponse("нет сессии", null);
    }
    
    @PostMapping("/get-order")//получить 1
    @ResponseBody
    public GetOrderResponse getOrder(@RequestParam String key, @RequestBody GetOrderRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new GetOrderResponse("нет сессии", false,null, null);
        }
        //проверка на права доступа
        if(session.getUser().getGroup().canViewOrder == true){
    
            OrderEntity order = orderRepository.getById(body.getIdOrder());
    
            OrderDTO orderDTO = orderService.toOrderDTO(order);
    
            return new GetOrderResponse("переданы клиенты", true, null, orderDTO);
        }
        return new GetOrderResponse("нет сессии", false,null, null);
    }

    @PostMapping("/get-order-by-trainer")//получить список заказов по тренеру
    @ResponseBody
    public GetOrderByTrainerResponse getOrderByTrainer(@RequestParam String key, @RequestBody GetOrderByTrainerRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);//выташили нужный кеу,если такого нет, вернули null
        if (session == null){
            return new GetOrderByTrainerResponse("нет сессии", null);
        }
        //проверка прав на добавление
        if(session.getUser().getGroup().canViewTrainer == true){
        //проверка, есть ли такой тренер
            TrainerEntity trainer = trainerRepository.getById(body.getIdTrainer());//наш тренер
            if (trainer == null){
                return new GetOrderByTrainerResponse("нет такого тренера", null);
            }
            List<OrderEntity> order = orderRepository.getByPersonalDataTrainer(trainer);

            List<OrderDTO> orderDTOS1 = orderService.toOrderDTOList(order);

            return new GetOrderByTrainerResponse("список отправлен", orderDTOS1);
        }
        return new GetOrderByTrainerResponse("нет сессии", null);
    }
}


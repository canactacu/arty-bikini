package ru.arty_bikini.crm.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import ru.arty_bikini.crm.Utils;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.data.dict.ExpressEntity;
import ru.arty_bikini.crm.data.dict.ProductTypeEntity;
import ru.arty_bikini.crm.data.dict.TrainerEntity;
import ru.arty_bikini.crm.data.orders.*;
import ru.arty_bikini.crm.data.orders.stone.CalcPresetRuleJson;
import ru.arty_bikini.crm.dto.PageDTO;
import ru.arty_bikini.crm.dto.dict.PriceDTO;
import ru.arty_bikini.crm.dto.enums.SortDirection;
import ru.arty_bikini.crm.dto.enums.UserGroup;
import ru.arty_bikini.crm.dto.enums.personalData.ClientType;
import ru.arty_bikini.crm.dto.orders.OrderDTO;
import ru.arty_bikini.crm.dto.orders.stone.CalcPresetRuleDTO;
import ru.arty_bikini.crm.dto.packet.order.*;
import ru.arty_bikini.crm.jpa.*;
import ru.arty_bikini.crm.servise.OrderService;

import javax.persistence.criteria.Predicate;
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

    @PostMapping("/get-orders-page")//Выдает постанично список заказов с фильтрацией
    @ResponseBody
    public GetOrdersPageResponse getOrderPage(@RequestParam String key, @RequestBody GetOrdersPageRequest body){
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new GetOrdersPageResponse(false,"нет сессии", null, null);
        }
        //проверка на права доступа
        if(session.getUser().getGroup().canViewOrder == true) {
            PageRequest pageable = PageRequest.ofSize(25).withPage(body.getFilter().getPage());
            Sort.Direction direction;

            if (body.getFilter().getOrderColumn() != null) {

                if (body.getFilter().getOrderDirection().equals(SortDirection.ASC)) {
                    direction = Sort.Direction.ASC;
                } else {
                    direction = Sort.Direction.DESC;
                }

                Sort sort = Sort
                        .by(direction, body.getFilter().getOrderColumn())
                        .and(Sort.by(Sort.Direction.ASC, "id"));
                pageable.withSort(sort);
            } else {
                pageable.withSort(Sort.Direction.ASC, "id");
            }

            Specification<OrderEntity> specification = Specification.where(
                    (root, query, criteriaBuilder) -> {
                        List<Predicate> rules = new ArrayList<>();
                        if (body.getFilter().getType() != null) {
                            Predicate rule = criteriaBuilder.equal(root.<ClientType>get("type"), body.getFilter().getType());
                            rules.add(rule);
                        }
                        if (body.getFilter().getArchive() != null) {
                            Predicate rule = criteriaBuilder.equal(root.get("archive"), body.getFilter().getArchive());
                            rules.add(rule);
                        }
                        if (body.getFilter().getTrainer()!=null){
                            Predicate rule = criteriaBuilder.equal(root.get("personalData").get("trainer"), body.getFilter().getTrainer());
                            rules.add(rule);
                        }
                        if (body.getFilter().getCreatedTimeFrom()!=null){
                            LocalDate localDate = Utils.toDate(body.getFilter().getCreatedTimeFrom());
                            Predicate rule = criteriaBuilder.greaterThanOrEqualTo(root.get("personalData").get("createdTime"), localDate);
                            rules.add(rule);
                        }
                        if (body.getFilter().getCreatedTimeTo()!=null){
                            LocalDate localDate = Utils.toDate(body.getFilter().getCreatedTimeTo());
                            Predicate rule = criteriaBuilder.lessThanOrEqualTo(root.get("personalData").get("createdTime"), localDate);
                            rules.add(rule);
                        }

                        return criteriaBuilder.and(rules.toArray(new Predicate[0]));

                    }
            );
            Page<OrderEntity> all = orderRepository.findAll(specification, pageable);

            PageDTO<OrderDTO> pageDTO = new PageDTO<>(
                    orderService.toOrderDTOList(all.getContent()),
                    all.getNumber(),
                    all.getSize(),
                    all.getTotalElements(),
                    all.getTotalPages()
            );

            return new GetOrdersPageResponse(true, "переданы страницы", null, pageDTO);
        }
        return new GetOrdersPageResponse(false,"нет сессии", null, null);

    }


    @PostMapping("/get-clients")//получить список клиентов
    @ResponseBody
    public GetClientsResponse getClients(@RequestParam String key){
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new GetClientsResponse("нет сессии", null, null);
        }
        //проверка на права доступа
        if(session.getUser().getGroup().canViewOrder == true){

            //сходить в бд//получить клиентов
            List<OrderEntity> all =  orderRepository.findAllByArchiveAndType(false, ClientType.CLIENT);//получила все клиентов

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

            List<OrderEntity> all = orderRepository.findAllByArchiveAndType(false, ClientType.LEAD);//список всех лидов
            List<OrderDTO> orderLeadDTOS = orderService.toOrderDTOList(all);
            
            //список только клиентов,  у кого нет мерок и дизайна
            List<OrderEntity> orderClientList =
                    orderRepository.findByArchiveAndTypeAndStatusInfoMeasureAllOrArchiveAndTypeAndStatusInfoDesignAll
                            (false, ClientType.CLIENT, false, false, ClientType.CLIENT,false);
            
            List<OrderDTO> orderClientDTOS = orderService.toOrderDTOList(orderClientList);
    
            //сделать DTO
            // List<OrderDTO> orderDTOS = orderService.toOrderDTOList(all);
            return new GetClientsResponse("переданы лиды", orderLeadDTOS, orderClientDTOS);
        }
        return new GetClientsResponse("нет сессии", null, null);
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
            
            order.setVersion(order.getVersion() + 1);
            
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
            order.setArchive(body.getOrder().getArchive());
            
    
            //все остальное заполнить через класс маппер
            PersonalData personalData = objectMapper.convertValue(body.getOrder().getPersonalData(), PersonalData.class);
            Design design = objectMapper.convertValue(body.getOrder().getDesign(), Design.class);//->DTO
            LeadInfo leadInfo = objectMapper.convertValue(body.getOrder().getLeadInfo(), LeadInfo.class);
            //проверить все вложенные *Entity (по ID сходить в бд и заменить на то, что вернет гибернайт)
    
            //проставляем срочность
            if (body.getOrder().getExpress() != null) {//от клиента пришла не 0
                
                //срочность поменялась
                if ((order.getExpress() == null)||(order.getExpress().getId() != body.getOrder().getExpress().getId())) {
                    ExpressEntity express = expressRepository.getById(body.getOrder().getExpress().getId());
                    if (express == null) {
                        return new EditClientResponse("в бд не заполнено ", null);
                    }
                    order.setExpress(express);
    
                    order.getPersonalData().setPackageOld(false);
                    order.getPersonalData().setPackageManagerOld(null);
                }
                
            } else {
                if (order.getExpress() != null) {
                    order.setExpress(null);
                    
                    order.getPersonalData().setPackageOld(false);
                    order.getPersonalData().setPackageManagerOld(null);
                    
                }
            }
            
            if (body.getOrder().getProduct() != null) {
                ProductTypeEntity productType = productTypeRepository.getById(body.getOrder().getProduct().getId());
                if (productType == null) {
                    return new EditClientResponse("productType == null, не заполнено ", null);
                }
                order.setProduct(productType);
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
    
            //считаем дату отправки OrderEntity order, String user, boolean packageNow, OrderDTO orderDTO, int idDataGoogle
            int dataGoogleId = -1;
            if (order.getDataGoogle()!=null) {
                dataGoogleId = order.getDataGoogle().getId();
            }
            orderService.savePackageTime(
                    order,
                    session.getUser().getLogin(),
                    body.getOrder().getPersonalData().getPackageNow(),
                    body.getOrder(),
                    dataGoogleId
            );
            
            
            //ставим дату попадания в архив
            if (order.getType() == null){
                return new EditClientResponse("нет статуса у клиента", null);
            }
            else {
                if((order.isArchive())){
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
            
            ////сохраняем поле priceJson; и price
            if (body.getOrder().getPrice()!=null){
                if (body.getOrder().getPrice().size()!=0){
                    
                    String priceJson = null;
                    
                    List<PriceDTO> priceDTOList = body.getOrder().getPrice();
                    List<Integer> priceEntityList = new ArrayList<>();
    
                    for (int i = 0; i < priceDTOList.size(); i++) {
                        
                        priceEntityList.add(priceDTOList.get(i).getId());
                        
                        //добавить поля
                    }
                    try {
                         priceJson = objectMapper.writeValueAsString(priceEntityList);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    order.setPriceJson(priceJson);
                }
            }
            order.setVersion(order.getVersion() + 1);
            
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
}


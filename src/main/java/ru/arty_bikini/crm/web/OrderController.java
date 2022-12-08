package ru.arty_bikini.crm.web;

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
import ru.arty_bikini.crm.dto.PageDTO;
import ru.arty_bikini.crm.dto.enums.personalData.ClientType;
import ru.arty_bikini.crm.dto.orders.OrderDTO;
import ru.arty_bikini.crm.dto.packet.order.*;
import ru.arty_bikini.crm.jpa.*;
import ru.arty_bikini.crm.servise.OrderService;

import java.time.LocalDate;
import java.util.List;

import static ru.arty_bikini.crm.dto.enums.personalData.ClientType.*;

///api/order/get-clients + //получить список клиентов
///api/order/get-leads  + - //получить список лидов и клиентов с предоплатой и без мерок или без дизайна
///api/order/add-client + //добавить лида-клиента
///api/order/edit-client + //изменить клиента
///api/order/get-client //получить 1 клиента?
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
        if(session.getUser().getGroup().canViewClients == true){

            //сходить в бд//получить клиентов
            List<OrderEntity> all = orderRepository.findAllByType(CLIENT);//получила все клиентов

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
        if(session.getUser().getGroup().canViewLeads == true){

            //сходить в бд//получить клиентов
            List<OrderEntity> all = orderRepository.findAllByType(LEAD);
            List<OrderEntity> orderList = orderRepository.getByStatusInfoMeasureAllOrStatusInfoDesignAll(false, false);
            List<OrderDTO> orderDTOS1 = orderService.toOrderDTOList(orderList);
    
            //сделать DTO
            List<OrderDTO> orderDTOS = orderService.toOrderDTOList(all);
            return new GetClientsResponse("переданы лиды", orderDTOS, orderDTOS1);
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
        if(session.getUser().getGroup().canViewArchive == true){

            //сходить в бд//получить клиентов
            Page<OrderEntity> all = orderRepository.findAllByType(ARCHIVE, Pageable.ofSize(25).withPage(page));

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
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);//выташили нужный кеу,если такого нет, вернули null
        if (session == null){
            return new AddClientResponse("нет сессии", null);
        }
        //проверка прав на добавление
        if(session.getUser().getGroup().canAddClientsLeads == true){

            //создать 1 клиента
            OrderEntity order = new OrderEntity();

            //заполнили этого клиента
            order.setId(0);
            order.setName(name);
            order.setType(type);
    
            PersonalData personalData = new PersonalData();
            order.setPersonalData(personalData);
            order.getPersonalData().setDeliveryTime(21);
            //сохранить в бд
           orderRepository.save(order);

            //в dto
            OrderDTO orderDTO = orderService.toOrderDTO(order);
           return  new AddClientResponse("клиента-лида передали", orderDTO);
        }
        return new AddClientResponse("нет сессии", null);
    }

    @PostMapping("/edit-client")//изменить клиента
    @ResponseBody
    public EditClientResponse editClient(@RequestParam String key, @RequestBody EditClientRequest body) {
    
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);//выташили нужный кеу,если такого нет, вернули null
        if (session == null) {
            return new EditClientResponse("нет сессии", null);
        }
        //проверка прав на: изменить клиента
        if (session.getUser().getGroup().canEditClients == true) {
    
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
            StatusInfo statusInfo = objectMapper.convertValue(body.getOrder().getStatusInfo(), StatusInfo.class);
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
            
            //проставляем, кто заполнил мерки и дизайн
            if (statusInfo != null){
                if (statusInfo.isDesignAll() && (order.getStatusInfo() == null || order.getStatusInfo().isDesignAll() == false)) {
                    statusInfo.setDesignBy(session.getUser());
                } else if (order.getStatusInfo() != null) {
                    statusInfo.setDesignBy(order.getStatusInfo().getDesignBy());
                }
                
                if (statusInfo.isMeasureAll() && (order.getStatusInfo() == null || order.getStatusInfo().isMeasureAll() == false)) {
                    statusInfo.setMeasureBy(session.getUser());
                } else if (order.getStatusInfo() != null) {
                    statusInfo.setMeasureBy(order.getStatusInfo().getMeasureBy());
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
            System.out.println("/////////////////////////5////////////////////////////////2");
    
            //считаем дату отправки
            if (personalData != null) {
                System.out.println("/////////////////////////51//s//////////////////////////////2");
    
                if (order.getPersonalData().getDeliveryTime() != 0) {
                    System.out.println("/////////////////////////////////////////////////////////2");
                    System.out.println(order.getPersonalData().getNeededTime());
//                    System.out.println(order.getDataGoogle().getNeededDate());
//                    System.out.println(order.getDataGoogle().getCompetition());
                    System.out.println("ok..............");
        
                    if (order.getPersonalData().getNeededTime() != null) { //менеджер дата, когда нужен
                        LocalDate date = order.getPersonalData().getNeededTime().minusDays(order.getPersonalData().getDeliveryTime());
                        date = date.minusDays(3);
                        order.getPersonalData().setPackageTime(date);//сохраняем
                        System.out.println(date);
                        System.out.println("................");
            
                    } else if (order.getPersonalData().getCompetitionTime() != null) {//менеджер дата соревнований
            
                        LocalDate date = order.getPersonalData().getCompetitionTime().minusDays(order.getPersonalData().getDeliveryTime());
                        date = date.minusDays(3);
                        order.getPersonalData().setPackageTime(date);//сохраняем
                        System.out.println(date);
                        System.out.println("................");
                    } else if (order.getDataGoogle() != null && order.getDataGoogle().getNeededDate() != null) {//клиент дата, когда нужен
            
                        LocalDate date = order.getDataGoogle().getNeededDate().minusDays(order.getPersonalData().getDeliveryTime());
                        date = date.minusDays(3);
                        order.getPersonalData().setPackageTime(date);//сохраняем
                        System.out.println(date);
                        System.out.println("................");
                    } else if (order.getDataGoogle() != null && order.getDataGoogle().getCompetition() != null) {//клиент дата соревнований
            
                        LocalDate date = order.getDataGoogle().getNeededDate().minusDays(order.getPersonalData().getDeliveryTime());
                        date = date.minusDays(3);
                        order.getPersonalData().setPackageTime(date);//сохраняем
                        System.out.println(date);
                        System.out.println("................");
                    }
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

    @PostMapping("/get-order-by-trainer")//получить список заказов по тренеру
    @ResponseBody
    public GetOrderByTrainerResponse getOrderByTrainer(@RequestParam String key, @RequestBody GetOrderByTrainerRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);//выташили нужный кеу,если такого нет, вернули null
        if (session == null){
            return new GetOrderByTrainerResponse("нет сессии", null);
        }
        //проверка прав на добавление
        if(session.getUser().getGroup().canAddClientsLeads == true){
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


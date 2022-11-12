package ru.arty_bikini.crm.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.data.dict.TrainerEntity;
import ru.arty_bikini.crm.dto.PageDTO;
import ru.arty_bikini.crm.dto.enums.ClientType;
import ru.arty_bikini.crm.data.orders.Design;
import ru.arty_bikini.crm.data.orders.LeadInfo;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.data.orders.PersonalData;
import ru.arty_bikini.crm.dto.orders.OrderDTO;
import ru.arty_bikini.crm.dto.packet.order.*;
import ru.arty_bikini.crm.jpa.OrderRepository;
import ru.arty_bikini.crm.jpa.SessionRepository;
import ru.arty_bikini.crm.jpa.TrainerRepository;
import ru.arty_bikini.crm.jpa.UserRepository;

import java.util.List;

import static ru.arty_bikini.crm.dto.enums.ClientType.*;

///api/order/get-clients + //получить список клиентов
///api/order/get-leads  + //получить список лидов
///api/order/add-client + //добавить лида-клиента
///api/order/edit-client + //изменить клиента
///api/order/get-client //получить 1 клиента?
///api/order/get-archive + //архив по страницам
///api/order/get-order-by-trainer +  //получить список заказов по тренеру

@RestController//контролерр
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


    @PostMapping("/get-clients")//получить список клиентов
    @ResponseBody
    public GetClientsResponse getClients(@RequestParam String key){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new GetClientsResponse("нет сессии", null);
        }
        //проверка на права доступа
        if(session.getUser().getGroup().canViewClients == true){

            //сходить в бд//получить клиентов
            List<OrderEntity> all = orderRepository.findAllByType(CLIENT);//получила все клиентов

            //получить клиентов из всех


            //сделать DTO
            List<OrderDTO> dto = objectMapper.convertValue(all, new TypeReference<List<OrderDTO>>() {});

            return new GetClientsResponse("переданы клиенты", dto);
        }
        return new GetClientsResponse("нет сессии", null);
    }

    @PostMapping("/get-leads")//получить список лидов
    @ResponseBody
    public GetClientsResponse getLeads(@RequestParam String key){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new GetClientsResponse("нет сессии", null);
        }
        //проверка на права доступа
        if(session.getUser().getGroup().canViewLeads == true){

            //сходить в бд//получить клиентов
            List<OrderEntity> all = orderRepository.findAllByType(LEAD);

            //сделать DTO
            List<OrderDTO> dto = objectMapper.convertValue(all, new TypeReference<List<OrderDTO>>() {});

            return new GetClientsResponse("переданы лиды", dto);
        }
        return new GetClientsResponse("нет сессии", null);
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
                    objectMapper.convertValue(all.getContent(), new TypeReference<List<OrderDTO>>() {}),
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

            //сохранить в бд
           orderRepository.save(order);

            //в dto
            OrderDTO rezult = objectMapper.convertValue(order, new TypeReference<OrderDTO>() {});

           return  new AddClientResponse("клиента-лида передали", rezult);
        }
        return new AddClientResponse("нет сессии", null);
    }

    @PostMapping("/edit-client")//изменить клиента
    @ResponseBody
    public EditClientResponse editClient(@RequestParam String key, @RequestBody EditClientRequest body){

        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);//выташили нужный кеу,если такого нет, вернули null
        if (session == null){
            return new EditClientResponse("нет сессии", null);
        }
        //проверка прав на: изменить клиента
        if(session.getUser().getGroup().canEditClients == true){

            //OrderDto->OrderEntity
            //получить из бд Order проверить,что не null
            OrderEntity order = orderRepository.getById(body.getOrder().getId());
            if(order == null){
                return new EditClientResponse("нет такого Id", null);
            }

            //примитивы в OrderEntity заполнить в ручную
            order.setName(body.getOrder().getName());
            order.setType(body.getOrder().getType());

            //все остальное заполнить через класс маппер
            PersonalData personalData = objectMapper.convertValue(body.getOrder().getPersonalData(), PersonalData.class);
            Design design = objectMapper.convertValue(body.getOrder().getDesign(), Design.class);//->DTO
            LeadInfo leadInfo = objectMapper.convertValue(body.getOrder().getLeadInfo(), LeadInfo.class);

            //проверить все вложенные *Entity (по ID сходить в бд и заменить на то,что вернет гибернайт)
            if (personalData != null) {

                if (personalData.getTrainer() != null) {
                    TrainerEntity trainer = trainerRepository.getById(personalData.getTrainer().getId());
                    if (trainer == null) {
                        return new EditClientResponse("trainer == null, не заполнино ", null);
                    }
                    personalData.setTrainer(trainer);
                }
            }

            if (design != null) {
                if (design.getDesigner() != null){
                    UserEntity user = userRepository.getById(design.getDesigner().getId());
                    if (user == null){
                        return new EditClientResponse("user == null, не заполнино ", null);
                    }
                    design.setDesigner(user);
                }
            }

            //заполняем
            order.setPersonalData(personalData);
            order.setDesign(design);
            order.setLeadInfo(leadInfo);

            //сохранить в бд
            OrderEntity save = orderRepository.save(order);

            //переделать в DTO
            OrderDTO saveDTO = objectMapper.convertValue(save, OrderDTO.class);

            return new EditClientResponse("данные исправлены", saveDTO);
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
            List<OrderDTO> orderDTOS = objectMapper.convertValue(order, new TypeReference<List<OrderDTO>>() {});

            return new GetOrderByTrainerResponse("список отправлен", orderDTOS);
        }
        return new GetOrderByTrainerResponse("нет сессии", null);
    }
}


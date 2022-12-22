package ru.arty_bikini.crm.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.data.work.IntervalEntity;
import ru.arty_bikini.crm.data.work.TourEntity;
import ru.arty_bikini.crm.data.work.WorkEntity;
import ru.arty_bikini.crm.dto.orders.OrderDTO;
import ru.arty_bikini.crm.dto.packet.work.*;
import ru.arty_bikini.crm.dto.work.IntervalDTO;
import ru.arty_bikini.crm.dto.work.WorkDTO;
import ru.arty_bikini.crm.jpa.*;
import ru.arty_bikini.crm.servise.OrderService;
import ru.arty_bikini.crm.servise.WorkService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static ru.arty_bikini.crm.Utils.toDate;

//api/work/add-work  + //добавить работу
//api/work/edit-work  + //изменить работу
//api/work/del-work  + //удалить работу

//api/work/add-interval + //добавить интервал
//api/work/edit-interval + ///изменить интервал

//api/work/div-interval  + //разделить интервал(добавить встречу)
//api/work/del-interval + //удалить встречу(только, если нет работ)
//api/work/assign-work-to-tour + //добавить работу на отдельную встречу
//api/work/del-work-from-tour   + //удалить работу со встречи

//api/work/get-interval-work   +  //получить список работ в интервалах, интервалов по дате началу длинна = 35 дней

@RestController
@RequestMapping("/api/work")
public class WorkController {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IntervalRepository intervalRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private TourRepository tourRepository;
    
    @Autowired
    private WorkService workService;

    @PostMapping("/add-work")//добавить работу
    @ResponseBody
    public AddWorkResponse addWork(@RequestParam String key, @RequestBody AddWorkRequest body) {

        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new AddWorkResponse(false, "нет сессии", null, null);
        }

        //проверка на права доступа
        if (session.getUser().getGroup().canEditWork == true) {
            OrderEntity order;
            if (body.getWorkDTO().getOrder()!=null) {
                order = orderRepository.getById(body.getWorkDTO().getOrder().getId());
                if (order == null) {
                    return new AddWorkResponse(false, "order нет в бд", null,null);
                }
            }
            else {
                    return new AddWorkResponse(false, "order нет в body", null,null);
            }
            
            UserEntity userWork;
            if (body.getWorkDTO().getUser()!=null) {
                userWork = userRepository.getById(body.getWorkDTO().getUser().getId());
                if (userWork == null) {
                    return new AddWorkResponse(false, "userWork нет в бд", null,null);
                }
            }
            else {
                return new AddWorkResponse(false, "userWork нет в body", null,null);
            }
    
            IntervalEntity interval;
            if (body.getWorkDTO().getInterval()!=null) {
                interval = intervalRepository.getById(body.getWorkDTO().getInterval().getId());
                if (interval == null) {
                    return new AddWorkResponse(false, "interval нет в бд", null,null);
                }
            }
            else {
                return new AddWorkResponse(false, "interval нет в body", null,null);
            }

            WorkEntity work = new WorkEntity();//создали новую работу
            work.setId(0);//заполнить работу
            work.setOrder(order);
            work.setUser(userWork);
            work.setInterval(interval);//id
            //из string в список
            if (body.getWorkDTO().getWorks()!= null) {
                work.setWorksJson(workService.toString(body.getWorkDTO().getWorks()));
            }
            
            work.setPriority(body.getWorkDTO().getPriority());
            
            //сохранить бд
            WorkEntity save = workRepository.save(work);

            //перевести работу в DTO
            WorkDTO saveDTO = objectMapper.convertValue(save, WorkDTO.class);
            saveDTO.setWorks(workService.toListDto(save.getWorksJson()));
            
            
            return new AddWorkResponse(true, "работу заполнили",null, saveDTO);
        }
        return new AddWorkResponse(false, "нет сессии", null, null);
    }
    
    @PostMapping("/edit-work-progress")//изменить работу
    @ResponseBody
    public EditWorkResponse editWorkProgress(@RequestParam String key, @RequestBody EditWorkRequest body) {
        
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new EditWorkResponse(false, "нет сессии", null, null);
        }
        
        //проверка на права доступа
        if (session.getUser().getGroup().canViewWork == true) {
            
            WorkEntity work = workRepository.getById(body.getWorkDTO().getId());
            if (work == null) {
                return new EditWorkResponse(false,"work нет в бд", null, null);
            }
            
            UserEntity userWork = work.getUser();
            if (userWork == null) {
                return new EditWorkResponse(false,"userWork нет в бд", null, null);
            }
    
            if (session.getUser()!=userWork) {
                return new EditWorkResponse(false,"userWork нет в бд",
                        "эта работа назначена не вам", null);
            }
            else {
                work.setProgress(body.getWorkDTO().getProgress());
                work.setUserApproved(userWork);
            }
            
            //сохранить бд
            WorkEntity save = workRepository.save(work);
            
            //перевести работу в DTO
            WorkDTO saveDTO = objectMapper.convertValue(save, WorkDTO.class);
            saveDTO.setWorks(workService.toListDto(save.getWorksJson()));
            
            return new EditWorkResponse(true,"работу изменили", null, saveDTO);
        }
        return new EditWorkResponse(false, "нет сессии", null, null);
    }
    
    @PostMapping("/edit-work")//изменить работу
    @ResponseBody
    public EditWorkResponse editWork(@RequestParam String key, @RequestBody EditWorkRequest body) {
        
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new EditWorkResponse(false, "нет сессии", null, null);
        }
        
        //проверка на права доступа
        if (session.getUser().getGroup().canEditWork == true) {
            if (body.getWorkDTO().getOrder() == null){
                return new EditWorkResponse(false,"Order нет в body", null, null);
            }
            OrderEntity order = orderRepository.getById(body.getWorkDTO().getOrder().getId());
            if (order == null) {
                return new EditWorkResponse(false,"order нет в бд", null, null);
            }
            
            WorkEntity work = workRepository.getById(body.getWorkDTO().getId());
            if (work == null) {
                return new EditWorkResponse(false,"work нет в бд", null, null);
            }
            
            if (body.getWorkDTO().getUser() == null){
                return new EditWorkResponse(false,"user нет в body", null, null);
            }
            UserEntity userWork = userRepository.getById(body.getWorkDTO().getUser().getId());
            if (userWork == null) {
                return new EditWorkResponse(false,"userWork нет в бд", null, null);
            }
    
            if (body.getWorkDTO().getInterval() == null){
                return new EditWorkResponse(false,"Interval нет в body", null, null);
            }
            IntervalEntity interval = intervalRepository.getById(body.getWorkDTO().getInterval().getId());
            if (interval == null) {
                return new EditWorkResponse(false,"interval нет в бд", null, null);
            }
            
            work.setOrder(order);
            work.setUser(userWork);
            work.setInterval(interval);
            if (body.getWorkDTO().getWorks()!=null) {
                work.setWorksJson(workService.toString(body.getWorkDTO().getWorks()));
            }
            else {
                return new EditWorkResponse(false,"надо заполнить работы в словаре", null, null);
            }
            
            work.setPriority(body.getWorkDTO().getPriority());
            
            if (body.getWorkDTO().getApproved()!=work.isApproved()) {
                work.setApproved(body.getWorkDTO().getApproved());
                work.setUserApproved(session.getUser());
            }
            
            //сохранить бд
            WorkEntity save = workRepository.save(work);
            
            //перевести работу в DTO
            WorkDTO saveDTO = objectMapper.convertValue(save, WorkDTO.class);
            saveDTO.setWorks(workService.toListDto(save.getWorksJson()));
            
            return new EditWorkResponse(true,"работу изменили", null, saveDTO);
        }
        return new EditWorkResponse(false, "нет сессии", null, null);
    }

    @PostMapping("/add-interval")//добавить интервал
    @ResponseBody
    public AddIntervalResponse addInterval(@RequestParam String key, @RequestBody AddIntervalRequest body) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new AddIntervalResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditWork == true) {

            //проверить поступающий интервал на адекватность(должен быть больше, чем последний),
            // или больше, чем сегодня, если первый
            IntervalEntity last = intervalRepository.getLast();//выяснили последнее
            if (last == null) {//если там нет интервалов. интервал первый//первый раз добавляем дату
                IntervalEntity intervalEntity = new IntervalEntity();

                intervalEntity.setId(0);
                intervalEntity.setDateStart(toDate(body.getDateFinish()).minusDays(7));
                intervalEntity.setDateFinish(toDate(body.getDateFinish()));

                IntervalEntity save = intervalRepository.save(intervalEntity);

                IntervalDTO saveDTO = objectMapper.convertValue(save, IntervalDTO.class);
                return new AddIntervalResponse("заполнили интервал первый", saveDTO);
            }
            //передаваемая дата = предыдущей
            if (toDate(body.getDateFinish()).isEqual(last.getDateFinish())) {
                return new AddIntervalResponse("заполняемая дата = прошлой", null);
            }

            //передаваемая дата < предыдущей
            if (toDate(body.getDateFinish()).isBefore(last.getDateFinish())) {
                return new AddIntervalResponse("заполняемая дата < прошлой", null);
            }

            IntervalEntity intervalEntity = new IntervalEntity();//создать новый интервал

            intervalEntity.setId(0);
            intervalEntity.setDateStart(last.getDateFinish());
            intervalEntity.setDateFinish(toDate(body.getDateFinish()));//заполнить

            IntervalEntity save = intervalRepository.save(intervalEntity);//сохранить в бд

            IntervalDTO saveDTO = objectMapper.convertValue(save, IntervalDTO.class);//перевести в DTO
            return new AddIntervalResponse("заполнили интервал", saveDTO);
        }
        return new AddIntervalResponse("нет сессии", null);
    }

    @PostMapping("/edit-interval")//изменить интервал
    @ResponseBody
    public EditIntervalResponse editInterval(@RequestParam String key, @RequestBody EditIntervalRequest body) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new EditIntervalResponse("нет сессии", null, null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditWork == true) {

            //надо понять, что существующий существует, идем проверять в бд
            IntervalEntity interval = intervalRepository.getById(body.getIdInterval());//изменяемый интервал
            if (interval == null) {
                return new EditIntervalResponse("interval == null ошибка", null, null);
            }

            //проверяем дату встречи новую
            //передаваемая дата = предыдущей
            LocalDate dateStart = interval.getDateStart();//это предыдущая дата финиша
            if (toDate(body.getDateFinish()).isEqual(dateStart)) {
                return new EditIntervalResponse("заполняемая дата = прошлой", null, null);
            }

            //передаваемая дата < предыдущей
            if (toDate(body.getDateFinish()).isBefore(dateStart)) {
                return new EditIntervalResponse("заполняемая дата < прошлой", null, null);
            }
            //выясняем есть ли следущий интервал, и если есть, то проверяем новую дату,не выползает ли она за пределы
            IntervalEntity nextInterval = intervalRepository.getByDateStart(interval.getDateFinish());//поиск интервала после данного
            if (nextInterval != null) {
                //проверка новой даты
                if (toDate(body.getDateFinish()).isEqual(nextInterval.getDateFinish())) {
                    return new EditIntervalResponse("заполняемая дата = следущей", null, null);
                }
                if (toDate(body.getDateFinish()).isAfter(nextInterval.getDateFinish())) {
                    return new EditIntervalResponse("заполняемая дата > следущей", null, null);
                }

                interval.setDateFinish(toDate(body.getDateFinish()));//поменяли дату
                IntervalEntity save = intervalRepository.save(interval); //сохрали бд
                IntervalDTO saveDTO = objectMapper.convertValue(save, IntervalDTO.class);//переделали в DTO

                nextInterval.setDateStart(toDate(body.getDateFinish()));//перезаполнили следущую
                IntervalEntity save1 = intervalRepository.save(nextInterval); //сохрали бд
                IntervalDTO saveDTO1 = objectMapper.convertValue(save1, IntervalDTO.class);//переделали в DTO

                return new EditIntervalResponse("переправили даты в текущем и след. интервале", saveDTO, saveDTO1);
            }
            interval.setDateFinish(toDate(body.getDateFinish()));//поменяли дату
            IntervalEntity save = intervalRepository.save(interval);            //сохрали бд
            IntervalDTO saveDTO = objectMapper.convertValue(save, IntervalDTO.class);            //переделали в DTO

            return new EditIntervalResponse("переправили дату, следущих интервалов нет", saveDTO, null);
        }
        return new EditIntervalResponse("нет сессии", null, null);
    }

    @PostMapping("/div-interval")//разделить интервал(добавить встречу)
    @ResponseBody
    public DivIntervalResponse divInterval(@RequestParam String key, @RequestBody DivIntervalRequest body) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new DivIntervalResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditWork == true) {
            //проверить интервал на наличие
            IntervalEntity interval = intervalRepository.getById(body.getIdInterval());//текущий интервал, в кот надо добавить встречу
            if (interval == null) {
                return new DivIntervalResponse("нет такого интервала", null);
            }
            //проверяем дату, которую хотим поставить, она должна быть в рамках интервала
            LocalDate tour = toDate(body.getTour());//текущая встреча

            if ((tour.isEqual(interval.getDateStart())) || (tour == interval.getDateFinish())) {
                return new DivIntervalResponse("встреча = границе интервала", null);
            }
            if ((interval.getDateStart().isAfter(tour)) || (tour.isAfter(interval.getDateFinish()))) {
                return new DivIntervalResponse("встреча выходит за рамки интервала", null);
            }
            TourEntity tourEntity = new TourEntity();

            tourEntity.setId(0);
            tourEntity.setTour(tour);
            tourEntity = tourRepository.save(tourEntity);

            interval.setTour(tourEntity);//заполнить данные
            IntervalEntity save = intervalRepository.save(interval);//сохранить данные в бд
            IntervalDTO saveDTO = objectMapper.convertValue(save, IntervalDTO.class);//переделать в DTO

            return new DivIntervalResponse("добавили встречу", saveDTO);
        }
        return new DivIntervalResponse("нет сессии", null);
    }

    @PostMapping("/del-interval")//удалить встречу(только, если нет работ)
    @ResponseBody
    public DelIntervalResponse delInterval(@RequestParam String key, @RequestBody DelIntervalRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new DelIntervalResponse("нет сессии");
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditWork == true){

            //если ли такая встреча(возможно, встречи нет)
            IntervalEntity interval = intervalRepository.getById(body.getIdInterval());//текущий интервал
            if(interval == null){
                return new DelIntervalResponse("interval == null");
            }
            if (interval.getTour() == null){
                return new DelIntervalResponse("возможно нет такой встречи");
            }
            //есть ли работы на встрече
            List<WorkEntity> workByTour = workRepository.getByTour(interval.getTour());//все работы на встрече
            if(workByTour != null){
                return new DelIntervalResponse("есть работы, удалять встречу нельзя");
            }

            interval.setTour(null);//удалили встречу

            IntervalEntity save = intervalRepository.save(interval);//сохранили бд
            IntervalDTO saveDTO = objectMapper.convertValue(save, IntervalDTO.class);

        }
        return new DelIntervalResponse("нет сессии");
    }

    @PostMapping("/assign-work-to-tour")//добавить работу на отдельную встречу
    @ResponseBody
    public AssignWorkToTourResponse AssignWorkToTour(@RequestParam String key, @RequestBody AssignWorkToTourRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new AssignWorkToTourResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditWork == true){
            //найти эту работу в бд
            WorkEntity work = workRepository.getById(body.getIdWork());//работа в которую надо назначить на встречу
            if (work == null){
                return new AssignWorkToTourResponse("в бд нет такой работы", null);
            }

            TourEntity tour =  work.getInterval().getTour();//нашли встречу в данном интервале(ее может не быть)
            if (tour == null){
                return new AssignWorkToTourResponse("надо создать встречу, работа не перенесена", null);
            }

            work.setTour(tour);//добавили встречу к работе
            WorkEntity save = workRepository.save(work);
            WorkDTO saveDTO = objectMapper.convertValue(save, WorkDTO.class);
            return new AssignWorkToTourResponse("работа перенесена на встречу", saveDTO);
        }
        return new AssignWorkToTourResponse("нет сессии", null);
    }

    @PostMapping("/del-work-from-tour")//удалить работу со встречи
    @ResponseBody
    public DelWorkFromTourResponse delWorkFromTour(@RequestParam String key, @RequestBody DelWorkFromTourRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new DelWorkFromTourResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditWork == true){
            //найти эту работу в бд
            WorkEntity work = workRepository.getById(body.getIdWork());//работа в которую надо назначить на встречу
            if (work == null){
                return new DelWorkFromTourResponse("в бд нет такой работы", null);
            }
            TourEntity tour = work.getTour();
            if (tour == null){
                return new DelWorkFromTourResponse("у данной работы tour == null, встреча не удалена", null);
            }
            work.setTour(null);
            WorkEntity save = workRepository.save(work);
            WorkDTO workDTO = objectMapper.convertValue(save, WorkDTO.class);

            return new DelWorkFromTourResponse("встреча удалена", workDTO);
        }
        return new DelWorkFromTourResponse("нет сессии", null);
    }

    @PostMapping("/del-work")//удалить работу
    @ResponseBody
    public DelWorkResponse delWork(@RequestParam String key, @RequestBody DelWorkRequest body) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new DelWorkResponse("нет сессии");
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditWork == true) {

            //проверить, есть ли такая работа
            WorkEntity work = workRepository.getById(body.getIdWorc());
            if (work == null) {
                return new DelWorkResponse("не удалили, нет такой работы");
            }
            workRepository.delete(work);
            return new DelWorkResponse("работа удалена");
        }
        return new DelWorkResponse("нет сессии");
    }

    @PostMapping("/get-interval-work")//получить список работ в интервалах, интервалов по дате началу длинна = 35 дней
    @ResponseBody
    public GetIntervalWorkResponse getIntervalWork(@RequestParam String key, @RequestBody GetIntervalWorkRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new GetIntervalWorkResponse("нет сессии", null, null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canViewWork == true){
            IntervalEntity intervalLast = intervalRepository.getLast();//последний интервал
            IntervalEntity intervalFirst = intervalRepository.getFirst();//первый интервал

            LocalDate end = toDate(body.getDateTime()).plusDays(35);//дата окончания
            List<IntervalEntity> intervalList = intervalRepository.getIntervalFromStartToEnd(toDate(body.getDateTime()), end);
            if(intervalList.size() == 0){
                return new GetIntervalWorkResponse("интервалов нет", Collections.emptyList(), Collections.emptyList());
            }
            List<IntervalDTO> intervalDTOS = objectMapper.convertValue(intervalList, new TypeReference<List<IntervalDTO>>() {});
    
            
            List<WorkEntity> workList = workRepository.getByIntervalList(intervalList);//описание в репозитории

            List<WorkDTO> workDTOList = objectMapper.convertValue(workList, new TypeReference<List<WorkDTO>>() {});
            
            for (int i = 0; i < workDTOList.size(); i++) {
                WorkEntity workEntity = workList.get(i);
                WorkDTO workDTO = workDTOList.get(i);
    
                OrderDTO order = workDTO.getOrder();
                //где-то стоит null по json надо его найти
                List<WorkEntity> orderWorkEntityList = workRepository.getByOrder(workEntity.getOrder());
                List<WorkDTO> orderWorkDTOList = objectMapper.convertValue(orderWorkEntityList, new TypeReference<List<WorkDTO>>() {});
                for (int j = 0; j < orderWorkDTOList.size(); j++) {
                    orderWorkDTOList.get(j).setWorks(workService.toListDto(orderWorkEntityList.get(j).getWorksJson()));
                }
              
                order.setWorks(orderWorkDTOList);
                
               
                workDTO.setWorks(workService.toListDto(workEntity.getWorksJson()));
            }
    
            return new GetIntervalWorkResponse("переданы", workDTOList, intervalDTOS);
        }
        return new GetIntervalWorkResponse("нет сессии", null, null);
    }
}


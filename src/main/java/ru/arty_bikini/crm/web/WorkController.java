package ru.arty_bikini.crm.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.data.enums.TypeWork;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.data.work.IntervalEntity;
import ru.arty_bikini.crm.data.work.TourEntity;
import ru.arty_bikini.crm.data.work.WorkEntity;
import ru.arty_bikini.crm.dto.work.IntervalDTO;
import ru.arty_bikini.crm.dto.work.WorkDTO;
import ru.arty_bikini.crm.jpa.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

//api/work/add-work  + //добавить работу
//api/work/edit-work//изменить работу?
//api/work/add-interval + //добавить интервал
//api/work/edit-interval + ///изменить интервал
//api/work/del-work  + //удалить работу
//api/work/div-interval  + //разделить интервал(добавить встречу
//api/work/assign-work-to-tour + //добавить работу на отдельную встречу
//api/work/del-work-from-tour   + //удалить работу со встречи
//api/work/del-interval + //удалить встречу(только, если нет работ)
//api/work/get-interval-work   +  //получить список работ в интервалах, интервалов по дате началу длинна = 35 дней

@RestController//контролерр
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
    private TourRepository tourRepository;

    @PostMapping("/add-work")//добавить работу
    @ResponseBody
    public AddWorkResponse addWork(@RequestParam String key, @RequestBody AddWorkRequest body) {

        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new AddWorkResponse("нет сессии", null);
        }

        //проверка на права доступа
        if (session.getUser().getGroup().canAddWork == true) {

            //найти и проверить все id, что они есть
            OrderEntity order = orderRepository.getById(body.getIdOrder());
            if (order == null) {
                return new AddWorkResponse("order == null", null);
            }

            UserEntity userWork = userRepository.getById(body.getIdUserWork());
            if (userWork == null) {
                return new AddWorkResponse("userWork == null", null);
            }

            IntervalEntity interval = intervalRepository.getById(body.getIdInterval());
            if (interval == null) {
                return new AddWorkResponse("interval == null", null);
            }

            WorkEntity work = new WorkEntity();//создали новую работу
            work.setId(0);//заполнить работу
            work.setOrder(order);
            work.setUser(userWork);
            work.setInterval(interval);//id
            work.setWorks(new HashSet<>(body.getTypeWork()));//создали set и скопировали все значения листа

            //сохранить бд
            WorkEntity save = workRepository.save(work);

            //перевести работу в DTO
            WorkDTO saveDTO = objectMapper.convertValue(save, WorkDTO.class);

            return new AddWorkResponse("работу заполнили", saveDTO);
        }
        return new AddWorkResponse("нет сессии", null);
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
        if (session.getUser().getGroup().canAddWork == true) {

            //проверить поступающий интервал на адекватность(должен быть больше, чем последний),
            // или больше, чем сегодня, если первый
            IntervalEntity last = intervalRepository.getLast();//выяснили последнее
            if (last == null) {//если там нет интервалов.интервал первый
                //первый раз добавляем дату
                IntervalEntity intervalEntity = new IntervalEntity();

                intervalEntity.setId(0);
                intervalEntity.setDateStart(body.getDatefinish().minusDays(7));
                intervalEntity.setDateFinish(body.getDatefinish());

                IntervalEntity save = intervalRepository.save(intervalEntity);

                IntervalDTO saveDTO = objectMapper.convertValue(save, IntervalDTO.class);
                return new AddIntervalResponse("заполнили интервал первый", saveDTO);
            }
            //передаваемая дата = предыдущей
            if (body.getDatefinish().isEqual(last.getDateFinish())) {
                return new AddIntervalResponse("заполняемая дата = прошлой", null);
            }

            //передаваемая дата < предыдущей
            if (body.getDatefinish().isBefore(last.getDateFinish())) {
                return new AddIntervalResponse("заполняемая дата < прошлой", null);
            }

            IntervalEntity intervalEntity = new IntervalEntity();//создать новый интервал

            intervalEntity.setId(0);
            intervalEntity.setDateStart(last.getDateFinish());
            intervalEntity.setDateFinish(body.getDatefinish());//заполнить

            IntervalEntity save = intervalRepository.save(intervalEntity);//сохранить в бд

            IntervalDTO saveDTO = objectMapper.convertValue(save, IntervalDTO.class);//перевести в DTO
            return new AddIntervalResponse("заполнили интервал", saveDTO);
        }
        return new AddIntervalResponse("нет сессии", null);
    }

    @PostMapping("/edit-interval")//изменить интервал
    @ResponseBody
    public EditIntervalResponse editInterval(@RequestParam String key, @RequestBody EdiIntervalRequest body) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new EditIntervalResponse("нет сессии", null, null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canAddWork == true) {

            //надо понять,что существующий существует, идем проверять в бд
            IntervalEntity interval = intervalRepository.getById(body.getIdInterval());//изменяемый интервал
            if (interval == null) {
                return new EditIntervalResponse("interval == null ошибка", null, null);
            }

            //проверяем дату встречи новую
            //передаваемая дата = предыдущей
            LocalDateTime dateStart = interval.getDateStart();//это предыдущая дата финиша
            if (body.getDateFinish().isEqual(dateStart)) {
                return new EditIntervalResponse("заполняемая дата = прошлой", null, null);
            }

            //передаваемая дата < предыдущей
            if (body.getDateFinish().isBefore(dateStart)) {
                return new EditIntervalResponse("заполняемая дата < прошлой", null, null);
            }
            //выясняем есть ли следущий интервал, и если есть, то проверяем новую дату,не выползает ли она за пределы
            IntervalEntity nextInterval = intervalRepository.getByStart(interval.getDateFinish());//поиск интервала после данного
            if (nextInterval != null) {
                //проверка новой даты
                if (body.getDateFinish().isEqual(nextInterval.getDateFinish())) {
                    return new EditIntervalResponse("заполняемая дата = следущей", null, null);
                }
                if (body.getDateFinish().isAfter(nextInterval.getDateFinish())) {
                    return new EditIntervalResponse("заполняемая дата > следущей", null, null);
                }

                interval.setDateFinish(body.getDateFinish());//поменяли дату
                IntervalEntity save = intervalRepository.save(interval); //сохрали бд
                IntervalDTO saveDTO = objectMapper.convertValue(save, IntervalDTO.class);//переделали в DTO

                nextInterval.setDateStart(body.getDateFinish());//перезаполнили следущую
                IntervalEntity save1 = intervalRepository.save(nextInterval); //сохрали бд
                IntervalDTO saveDTO1 = objectMapper.convertValue(save1, IntervalDTO.class);//переделали в DTO

                return new EditIntervalResponse("переправили даты в текущем и след. интервале", saveDTO, saveDTO1);
            }
            interval.setDateFinish(body.getDateFinish());//поменяли дату
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
        if (session.getUser().getGroup().canAddWork == true) {
            //проверить интервал на наличие
            IntervalEntity interval = intervalRepository.getById(body.getIdInterval());//текущий интервал, в кот надо добавить встречу
            if (interval == null) {
                return new DivIntervalResponse("нет такого интервала", null);
            }
            //проверяем дату, которую хотим поставить, она должна быть в рамках интервала
            LocalDateTime tour = body.getTour();//текущая встреча
            if ((tour == interval.getDateStart()) || (tour == interval.getDateFinish())) {
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
        if (session.getUser().getGroup().canAddWork == true){

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
        if (session.getUser().getGroup().canAddWork == true){
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
        if (session.getUser().getGroup().canAddWork == true){
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
        if (session.getUser().getGroup().canAddWork == true) {

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
        if (session.getUser().getGroup().canAddWork == true){
            IntervalEntity intervalLast = intervalRepository.getLast();//последний интервал
            IntervalEntity intervalFirst = intervalRepository.getFirst();//первый интервал

            LocalDateTime end = body.getDateTime().plusDays(35);//дата окончания
            List<IntervalEntity> intervalList = intervalRepository.getIntervalFromStartToEnd(body.getDateTime(), end);
            if(intervalList.size() == 0){
                return new GetIntervalWorkResponse("интервалов нет", Collections.emptyList(), Collections.emptyList());
            }
            List<WorkEntity> workList = workRepository.getByIntervalList(intervalList);//описание в репазитории

            List<IntervalDTO> intervalDTOS = objectMapper.convertValue(intervalList, new TypeReference<List<IntervalDTO>>() {});
            List<WorkDTO> workEntities = objectMapper.convertValue(workList, new TypeReference<List<WorkDTO>>() {});

            return new GetIntervalWorkResponse("нет сессии", workEntities, intervalDTOS);
        }
        return new GetIntervalWorkResponse("нет сессии", null, null);
    }
}
//тело для:получить список работ...
class GetIntervalWorkRequest {
    private LocalDateTime dateTime;//получаем дату начала

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}

//ответ для:получить список работ...
class GetIntervalWorkResponse{
    private final String statusCode;//статус
    private final List<WorkDTO> workDTOList; //список работ одним списком
    private final List<IntervalDTO> intervalDTOList;//список интервалов

    public GetIntervalWorkResponse(String statusCode, List<WorkDTO> workDTOList, List<IntervalDTO> intervalDTOList) {
        this.statusCode = statusCode;
        this.workDTOList = workDTOList;
        this.intervalDTOList = intervalDTOList;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public List<WorkDTO> getWorkDTOList() {
        return workDTOList;
    }

    public List<IntervalDTO> getIntervalDTOList() {
        return intervalDTOList;
    }

}

//тело для:удалить работу со встречи
class DelWorkFromTourRequest {
    private int idWork;//id работы, которую хотим удалить со встречу

    public int getIdWork() {
        return idWork;
    }

    public void setIdWork(int idWork) {
        this.idWork = idWork;
    }
}

//ответ для:удалить работу со встречи
class DelWorkFromTourResponse {
    private final String statusCode;//статус
    private final WorkDTO workDTO;

    public String getStatusCode() {
        return statusCode;
    }

    public WorkDTO getWorkDTO() {
        return workDTO;
    }

    public DelWorkFromTourResponse(String statusCode, WorkDTO workDTO) {
        this.statusCode = statusCode;
        this.workDTO = workDTO;
    }
}

//тело для:добавить работу на отдельную встречу
class AssignWorkToTourRequest{
    private int idWork;//id работы, которую хотим добавить на встречу

    public int getIdWork() {
        return idWork;
    }

    public void setIdWork(int idWork) {
        this.idWork = idWork;
    }
}

//ответ для:добавить работу на отдельную встречу
class AssignWorkToTourResponse{
    private final String statusCode;//статус
    private final WorkDTO workDTO;

    public AssignWorkToTourResponse(String statusCode, WorkDTO workDTO) {
        this.statusCode = statusCode;
        this.workDTO = workDTO;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public WorkDTO getWorkDTO() {
        return workDTO;
    }
}

//тело для:удалить встречу(только, если нет работ)
class DelIntervalRequest{
    private int idInterval;//id интервала, в котором будем удалять встречу

    public int getIdInterval() {
        return idInterval;
    }

    public void setIdInterval(int idInterval) {
        this.idInterval = idInterval;
    }
}

//ответ для:удалить встречу(только, если нет работ)
class DelIntervalResponse{
    private final String statusCode;//статус

    public String getStatusCode() {
        return statusCode;
    }

    public DelIntervalResponse(String statusCode) {
        this.statusCode = statusCode;
    }
}

//тело для:разделить интервал(добавить встречу)
class DivIntervalRequest{
    private int idInterval;//id интервала, в котором будем добавлять встречу
    private LocalDateTime tour;//дата встречи

    public LocalDateTime getTour() {
        return tour;
    }

    public void setTour(LocalDateTime tour) {
        this.tour = tour;
    }

    public int getIdInterval() {
        return idInterval;
    }

    public void setIdInterval(int idInterval) {
        this.idInterval = idInterval;
    }
}

//ответ для: разделить интервал(добавить встречу)
class DivIntervalResponse{
    private final String statusCode;//статус
    private final IntervalDTO intervalDTO;

    public DivIntervalResponse(String statusCode, IntervalDTO intervalDTO) {
        this.statusCode = statusCode;
        this.intervalDTO = intervalDTO;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public IntervalDTO getIntervalDTO() {
        return intervalDTO;
    }
}

//тело для:удалить работу
class DelWorkRequest {
    private int idWorc;//id удаляемой работы

    public int getIdWorc() {
        return idWorc;
    }

    public void setIdWorc(int idWorc) {
        this.idWorc = idWorc;
    }
}

//ответ для: удалить работу
class DelWorkResponse{
    private final String statusCode;//статус

    public String getStatusCode() {
        return statusCode;
    }

    public DelWorkResponse(String statusCode) {
        this.statusCode = statusCode;
    }
}

//тело для: изменить интервал
class EdiIntervalRequest {
    private int idInterval;//уже существующий
    private LocalDateTime dateFinish;//дата встречи новая

    public int getIdInterval() {
        return idInterval;
    }

    public void setIdInterval(int idInterval) {
        this.idInterval = idInterval;
    }

    public LocalDateTime getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(LocalDateTime dateFinish) {
        this.dateFinish = dateFinish;
    }
}

//ответ для: изменить интервал
class EditIntervalResponse{
    private final String statusCode;//статус
    private final IntervalDTO intervalDTO;//интервал
    private final IntervalDTO intervalDTONext;//следующий интервал

    public EditIntervalResponse(String statusCode, IntervalDTO intervalDTO, IntervalDTO intervalDTONext) {
        this.statusCode = statusCode;
        this.intervalDTO = intervalDTO;
        this.intervalDTONext = intervalDTONext;
    }

    public IntervalDTO getIntervalDTONext() {
        return intervalDTONext;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public IntervalDTO getIntervalDTO() {
        return intervalDTO;
    }
}

//тело для: добавить интервал
class AddIntervalRequest{
    private LocalDateTime datefinish;//дата встречи

    public LocalDateTime getDatefinish() {
        return datefinish;
    }

    public void setDatefinish(LocalDateTime datefinish) {
        this.datefinish = datefinish;
    }
}

//ответ для: добавить интервал
class AddIntervalResponse{
    private final String statusCode;//статус
    private final IntervalDTO intervalDTO;//интервал

    public AddIntervalResponse(String statusCode, IntervalDTO intervalDTO) {
        this.statusCode = statusCode;
        this.intervalDTO = intervalDTO;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public IntervalDTO getIntervalDTO() {
        return intervalDTO;
    }
}

//ответ для: добавить работу
class AddWorkResponse {
    private final String statusCode;//статус
    private final WorkDTO workDTO;//работа

    public AddWorkResponse(String statusCode, WorkDTO workDTO) {
        this.statusCode = statusCode;
        this.workDTO = workDTO;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public WorkDTO getWorkDTO() {
        return workDTO;
    }
}

//тело для: добавить работу
class AddWorkRequest {
    private int idOrder;  //ид заказа,
    private int idUserWork; // ид сотрудника,
    private int idInterval;// ид интервала,
    private List<TypeWork> typeWork; // типы работ,которые будут сделаны

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdUserWork() {
        return idUserWork;
    }

    public void setIdUserWork(int idUserWork) {
        this.idUserWork = idUserWork;
    }

    public int getIdInterval() {
        return idInterval;
    }

    public void setIdInterval(int idInterval) {
        this.idInterval = idInterval;
    }

    public List<TypeWork> getTypeWork() {
        return typeWork;
    }

    public void setTypeWork(List<TypeWork> typeWork) {
        this.typeWork = typeWork;
    }
}

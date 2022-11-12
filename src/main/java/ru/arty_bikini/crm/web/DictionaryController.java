package ru.arty_bikini.crm.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.data.dict.TrainerEntity;
import ru.arty_bikini.crm.dto.dict.TrainerDTO;
import ru.arty_bikini.crm.jpa.SessionRepository;
import ru.arty_bikini.crm.jpa.TrainerRepository;

import java.time.LocalDateTime;
import java.util.List;

//api/dict/get-trainers + получить всех тренеров
//api/dict/add-trainer  + добавить тренара
//api/dict/edit-trainer + изменить тренера

@RestController//контролерр
@RequestMapping("/api/dict")
public class DictionaryController {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    TrainerRepository trainerRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/get-trainers")//получить всех тренеров
    @ResponseBody
    public GetTrainersResponse getTrainers(@RequestParam String key) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new GetTrainersResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canViewLeads == true) {

            List<TrainerEntity> trainer = trainerRepository.findAll();//найти всех
            List<TrainerDTO> trainerDTOS = objectMapper.convertValue(trainer, new TypeReference<List<TrainerDTO>>() {});
            return new GetTrainersResponse("тренеров передали", trainerDTOS);
        }
        return new GetTrainersResponse("нет сессии", null);

    }

    @PostMapping("/add-trainer")//добавить тренара
    @ResponseBody
    public AddTrainersResponse addTrainers(@RequestParam String key, @RequestBody AddTrainersRequest body){
            //проверка на key
            SessionEntity session = sessionRepository.getByKey(key);
            if (session == null) {
                return new AddTrainersResponse("нет сессии", null);
            }
            //проверка на права доступа
            if (session.getUser().getGroup().canViewLeads == true){

                TrainerEntity trainer = new TrainerEntity();

                trainer.setId(0);
                trainer.setName(body.getName());

                TrainerEntity save = trainerRepository.save(trainer);
                TrainerDTO trainerDTO = objectMapper.convertValue(save, TrainerDTO.class);

                return new AddTrainersResponse("тренер добавлен", trainerDTO);
            }

        return new AddTrainersResponse("нет сессии", null);
    }

    @PostMapping("/edit-trainer")//изменить тренара
    @ResponseBody
    public EditTrainersResponse editTrainers(@RequestParam String key, @RequestBody EditTrainersRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new EditTrainersResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canViewLeads == true){

            TrainerEntity trainer = trainerRepository.getById(body.getIdTrainers());//наш тренер
            trainer.setName(body.getName());

            TrainerEntity save = trainerRepository.save(trainer);
            TrainerDTO trainerDTO = objectMapper.convertValue(save, TrainerDTO.class);

            return new EditTrainersResponse("изменили тренера", trainerDTO);
        }
        return new EditTrainersResponse("нет сессии", null);
    }

}

//тело для:изменить тренара.
class EditTrainersRequest {
    private int idTrainers;
    private String name;//имя тренера

    public int getIdTrainers() {
        return idTrainers;
    }

    public void setIdTrainers(int idTrainers) {
        this.idTrainers = idTrainers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

//ответ для:изменить тренара
class EditTrainersResponse{
    private final String statusCode;//статус
    private final TrainerDTO trainerDTO;//измененный тренер

    public EditTrainersResponse(String statusCode, TrainerDTO trainerDTO) {
        this.statusCode = statusCode;
        this.trainerDTO = trainerDTO;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public TrainerDTO getTrainerDTO() {
        return trainerDTO;
    }
}

//тело для:добавить тренара.
class AddTrainersRequest {
    private String name;//имя тренера

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

//ответ для:добавить тренара
class AddTrainersResponse{
    private final String statusCode;//статус
    private final TrainerDTO trainerDTO;//добавленный тренер

    public AddTrainersResponse(String statusCode, TrainerDTO trainerDTO) {
        this.statusCode = statusCode;
        this.trainerDTO = trainerDTO;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public TrainerDTO getTrainerDTO() {
        return trainerDTO;
    }
}

//ответ для:получить список работ...
class GetTrainersResponse{
    private final String statusCode;//статус
    private final List<TrainerDTO> trainerList;//список тренеров

    public GetTrainersResponse(String statusCode, List<TrainerDTO> trainerList) {
        this.statusCode = statusCode;
        this.trainerList = trainerList;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public List<TrainerDTO> getTrainerList() {
        return trainerList;
    }
}
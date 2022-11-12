package ru.arty_bikini.crm.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.data.dict.TrainerEntity;
import ru.arty_bikini.crm.dto.dict.TrainerDTO;
import ru.arty_bikini.crm.dto.packet.dict.*;
import ru.arty_bikini.crm.jpa.SessionRepository;
import ru.arty_bikini.crm.jpa.TrainerRepository;

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


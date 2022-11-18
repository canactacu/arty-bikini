package ru.arty_bikini.crm.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.data.dict.ProductTypeEntity;
import ru.arty_bikini.crm.data.dict.RhinestoneTypeEntity;
import ru.arty_bikini.crm.data.dict.TrainerEntity;
import ru.arty_bikini.crm.dto.dict.ProductTypeDTO;
import ru.arty_bikini.crm.dto.dict.RhinestoneTypeDTO;
import ru.arty_bikini.crm.dto.dict.TrainerDTO;
import ru.arty_bikini.crm.dto.packet.dict.*;
import ru.arty_bikini.crm.jpa.ProductTypeRepository;
import ru.arty_bikini.crm.jpa.RhinestoneTypeRepository;
import ru.arty_bikini.crm.jpa.SessionRepository;
import ru.arty_bikini.crm.jpa.TrainerRepository;

import java.util.List;

//api/dict/get-trainers + получить всех тренеров
//api/dict/add-trainer  + добавить тренара
//api/dict/edit-trainer + изменить тренера

///api/dict/get-product-types + Список типов купальников
//      /api/dict/edit-product-type + Добавить,изменить тип купальника
//    /api/dict/get-rhinestone-type + Список типов страз
//  /api/dict/edit-rhinestone-type + Добавить изменить тип страз
//это контроллер

@RestController//контролерр
@RequestMapping("/api/dict")
public class DictionaryController {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ProductTypeRepository productTypeRepository;
    
   @Autowired
   private RhinestoneTypeRepository rhinestoneTypeRepository;


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
    
    @PostMapping("/get-product-types")//Список типов купальников
    @ResponseBody
    public GetProductTypesResponse getProductTypes(@RequestParam String key){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new GetProductTypesResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canViewProductTypes == true){
    
            List<ProductTypeEntity> all = productTypeRepository.findAll();
            List<ProductTypeDTO> productTypeDTOS = objectMapper.convertValue(all, new TypeReference<List<ProductTypeDTO>>() {});
            return new GetProductTypesResponse("список отправлен", productTypeDTOS);
        }
        return new GetProductTypesResponse("нет сессии", null);
    }
    
    @PostMapping("/edit-product-type")// Добавить, изменить тип продукта
    @ResponseBody
    public EditProductTypeResponse editProductType(@RequestParam String key, @RequestBody EditProductTypeRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new EditProductTypeResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canViewProductTypes == true){
            body.getProductTypeDTO();
            ProductTypeEntity productType = productTypeRepository.getById(body.getProductTypeDTO().getId());
            if(productType == null){//не нашли такого, добавляем нового
                productType = new ProductTypeEntity();
                productType.setId(0);
            }
            productType.setName(body.getProductTypeDTO().getName());
            productType.setPaymentNonStone(body.getProductTypeDTO().getPaymentNonStone());
            productType.setCategoryMeasure(body.getProductTypeDTO().getCategoryMeasure());
    
            ProductTypeEntity save = productTypeRepository.save(productType);
    
            ProductTypeDTO productTypeDTO = objectMapper.convertValue(save, ProductTypeDTO.class);
    
            return new EditProductTypeResponse("добавлен, изменен", productTypeDTO);
    
        }
        return new EditProductTypeResponse("нет сессии", null);
    }
    
    @PostMapping("/get-rhinestone-type")// Список типов страз
    @ResponseBody
    public GetRhinestoneTypeResponse getRhinestoneType(@RequestParam String key){
    
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new GetRhinestoneTypeResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canViewProductTypes == true){
            List<RhinestoneTypeEntity> all = rhinestoneTypeRepository.findAll();
            List<RhinestoneTypeDTO> rhinestoneTypeDTOS = objectMapper.convertValue(all, new TypeReference<List<RhinestoneTypeDTO>>() {});
    
            return new GetRhinestoneTypeResponse("список страз отправлен", rhinestoneTypeDTOS);
        }
        return new GetRhinestoneTypeResponse("нет сессии", null);
    }
    
    @PostMapping("/edit-rhinestone-type")// Добавить тип страз
    @ResponseBody
    public EditRhinestoneTypeResponse editRhinestoneType(@RequestParam String key,@RequestBody EditRhinestoneTypeRequest body){
        
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new EditRhinestoneTypeResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canViewProductTypes == true){
            RhinestoneTypeEntity rhinestoneType = rhinestoneTypeRepository.getById(body.getRhinestoneTypeDTO().getId());
            if(rhinestoneType ==null){
                rhinestoneType = new RhinestoneTypeEntity();
                rhinestoneType.setId(0);
            }
            rhinestoneType.setPrice(body.getRhinestoneTypeDTO().getPrice());
            rhinestoneType.setManufacturer(body.getRhinestoneTypeDTO().getManufacturer());
            rhinestoneType.setSizeTypeRhinston(body.getRhinestoneTypeDTO().getSizeTypeRhinston());
    
            RhinestoneTypeEntity save = rhinestoneTypeRepository.save(rhinestoneType);
            RhinestoneTypeDTO rhinestoneTypeDTO = objectMapper.convertValue(save, RhinestoneTypeDTO.class);
    
            return new EditRhinestoneTypeResponse("добавили, изменили", rhinestoneTypeDTO);
    
        }
        return new EditRhinestoneTypeResponse("нет сессии", null);
    }
}


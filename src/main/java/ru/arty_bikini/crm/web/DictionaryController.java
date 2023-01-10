package ru.arty_bikini.crm.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.data.dict.*;
import ru.arty_bikini.crm.data.file.FileEntity;
import ru.arty_bikini.crm.data.other.NoteEntity;
import ru.arty_bikini.crm.data.work.WorkTypeEntity;
import ru.arty_bikini.crm.dto.dict.*;
import ru.arty_bikini.crm.dto.other.NoteDTO;
import ru.arty_bikini.crm.dto.packet.dict.*;
import ru.arty_bikini.crm.dto.work.WorkTypeDTO;
import ru.arty_bikini.crm.jpa.*;
import ru.arty_bikini.crm.servise.DictionaryService;

import java.util.List;

//api/dict/get-trainers + получить всех тренеров
//api/dict/add-trainer  + добавить тренара
//api/dict/edit-trainer + изменить тренера

///api/dict/get-product-types + Список типов купальников
//      /api/dict/edit-product-type + Добавить,изменить тип купальника

//    /api/dict/get-rhinestone-type + Список типов страз
//  /api/dict/edit-rhinestone-type + Добавить изменить тип страз

//api/dict/get-express + получить все цены за срочность
//api/dict/add-express  + добавить новую цену срочности
//api/dict/edit-express  + изменить цену срочности

//api/dict/get-price  + получить всех
//api/dict/add-price + добавить
//api/dict/edit-price  + изменить

//api/dict/get-straps  +  получить всех straps
//api/dict/add-straps +  добавить straps
//api/dict/edit-straps +  изменить straps

//api/dict/get-script-stage  +  получить всех script_stage
//api/dict/add-script-stage +  добавить script_stage
//api/dict/edit-script-stage +  изменить script_stage

//api/dict/get-work-type +  получить всех work_type
//api/dict/add-work-type  + добавить work_type
//api/dict/edit-work-type  +  изменить work_type

//api/dict/get-note +  получить всех note
//api/dict/add-note  + добавить note
//api/dict/edit-note  +  изменить note

@RestController
@RequestMapping("/api/dict")
public class DictionaryController {
    
    @Autowired
    private ExpressRepository expressRepository;
    
    @Autowired
    private StrapsRepository strapsRepository;

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
   
   @Autowired
   private PriceRepository priceRepository;

    @Autowired
    private FileRepository fileRepository;
    
    
    @Autowired
    private WorkTypeRepository workTypeRepository;
    
    @Autowired
    private DictionaryService dictionaryService;
    
    @Autowired
    private ScriptStageRepository scriptStageRepository;

    @Autowired
    private NoteRepository noteRepository;

    @PostMapping("/get-note")//получить всех note
    @ResponseBody
    public GetNoteResponse getNote(@RequestParam String key) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new GetNoteResponse(false, "нет сессии", null, null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canViewDict == true) {

            List<NoteEntity> all = noteRepository.findAll();
            List<NoteDTO> noteDTOList = objectMapper.convertValue(all, new TypeReference<List<NoteDTO>>() {});

            return new GetNoteResponse(true, "передали", null, noteDTOList);
        }
        return new GetNoteResponse(false, "нет сессии", null, null);
    }

    @PostMapping("/get-work-type")//получить всех work-type
    @ResponseBody
    public GetWorkTypeResponse getWorkType(@RequestParam String key) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new GetWorkTypeResponse(false, "нет сессии", null, null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canViewDict == true) {
    
            List<WorkTypeEntity> list = workTypeRepository.findAll();
            List<WorkTypeDTO> workTypeDTOS = objectMapper.convertValue(list, new TypeReference<List<WorkTypeDTO>>() {});
            //добавляем поле productJson
            for (int i = 0; i < list.size(); i++) {
                workTypeDTOS.get(i).setProductList(dictionaryService.productTypeDTOList(list.get(i).getProductJson()));
            }
            return new GetWorkTypeResponse(true, "передали", null, workTypeDTOS);
        }
        return new GetWorkTypeResponse(false, "нет сессии", null, null);
    }

    @PostMapping("/add-note")//добавить add-note
    @ResponseBody
    public AddNoteResponse addNote(@RequestParam String key, @RequestBody AddNoteRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new AddNoteResponse(false, "нет сессии", null, null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditDict == true){
;
            NoteEntity note = noteRepository.getById(body.getNote().getId());
            if (note != null) {
                return new AddNoteResponse(false, "есть такой id", null, null);
            }

            note = new NoteEntity();
            note.setId(0);
            note.setPath(body.getNote().getPath());
            note.setContent(body.getNote().getContent());

             if (body.getNote().getFile()!= null) {
                 FileEntity file = fileRepository.getById(body.getNote().getFile().getId());
                 if (file == null) {
                     return new AddNoteResponse(false, "нет такого файла в бд", null, null);
                 }
                 note.setFile(file);
             }

            NoteEntity save = noteRepository.save(note);
            NoteDTO noteDTO = objectMapper.convertValue(save, NoteDTO.class);

            return new AddNoteResponse(true, "добавлен", null, noteDTO);
        }

        return new AddNoteResponse(false, "нет сессии", null, null);
    }
    
    @PostMapping("/add-work-type")//добавить add-work-type
    @ResponseBody
    public AddWorkTypeResponse addWorkType(@RequestParam String key, @RequestBody AddWorkTypeRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new AddWorkTypeResponse(false, "нет сессии", null, null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditDict == true){
            
            WorkTypeEntity workType = new WorkTypeEntity();
            workType.setId(0);
            workType.setName(body.getWorkType().getName());
            
            workType.setPriority(body.getWorkType().getPriority());
            workType.setVisible(body.getWorkType().getVisible());
            
            workType.setSeamstress(body.getWorkType().getSeamstress());
            workType.setGluer(body.getWorkType().getGluer());
            workType.setGluerAndSeamstress(body.getWorkType().getGluerAndSeamstress());
            
            workType.setPaySeamstress(body.getWorkType().getPaySeamstress());
    
            if (body.getWorkType().getPrice() != null) {
                PriceEntity price = priceRepository.getById(body.getWorkType().getPrice().getId());
                if (price == null) {
                    return new AddWorkTypeResponse(false, "ошибка price==null", null, null);
                }
                workType.setPrice(price);
            }
            if (body.getWorkType().getProductList()!=null) {
                String productJson = dictionaryService.productToJson(body.getWorkType().getProductList());
                workType.setProductJson(productJson);
            }
            
            WorkTypeEntity save = workTypeRepository.save(workType);
            WorkTypeDTO workTypeDTO = objectMapper.convertValue(save, WorkTypeDTO.class);
    
            return new AddWorkTypeResponse(true, "добавлен", null, workTypeDTO);
        }
    
        return new AddWorkTypeResponse(false, "нет сессии", null, null);
    }
    
    @PostMapping("/edit-note")//изменить work-type
    @ResponseBody
    public EditNoteResponse editNote(@RequestParam String key, @RequestBody EditNoteRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new EditNoteResponse(false, "нет сессии", null, null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditDict == true){

            NoteEntity noteEntity = noteRepository.getById(body.getNote().getId());
            if (noteEntity == null) {
                return new EditNoteResponse(false, "не нашли в бд", null, null);
            }
            noteEntity.setPath(body.getNote().getPath());
            noteEntity.setContent(body.getNote().getContent());

            FileEntity file = fileRepository.getById(body.getNote().getId());
            if (file == null) {
                return new EditNoteResponse(false, "не нашли в бд файл", null, null);
            }
            noteEntity.setFile(file);

            NoteEntity save = noteRepository.save(noteEntity);

            NoteDTO noteDTO = objectMapper.convertValue(save, NoteDTO.class);


            return new EditNoteResponse(true, "изменили", null, noteDTO);
        }
        return new EditNoteResponse(false, "нет сессии", null, null);
    }

    @PostMapping("/edit-work-type")//изменить work-type
    @ResponseBody
    public EditWorkTypeResponse editWorkType(@RequestParam String key, @RequestBody EditWorkTypeRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new EditWorkTypeResponse(false, "нет сессии", null, null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditDict == true){

            WorkTypeEntity workType = workTypeRepository.getById(body.getWorkType().getId());
            if (workType == null) {
                return new EditWorkTypeResponse(false, "не нашли в бд", null, null);
            }
            workType.setName(body.getWorkType().getName());

            workType.setPriority(body.getWorkType().getPriority());
            workType.setVisible(body.getWorkType().getVisible());

            workType.setSeamstress(body.getWorkType().getSeamstress());
            workType.setGluer(body.getWorkType().getGluer());
            workType.setGluerAndSeamstress(body.getWorkType().getGluerAndSeamstress());

            workType.setPaySeamstress(body.getWorkType().getPaySeamstress());

            if (body.getWorkType().getPrice() != null) {
                PriceEntity price = priceRepository.getById(body.getWorkType().getPrice().getId());
                if (price == null) {
                    return new EditWorkTypeResponse(false, "ошибка price==null", null, null);
                }
                workType.setPrice(price);
            } else {
                workType.setPrice(null);
            }

            String productJson = dictionaryService.productToJson(body.getWorkType().getProductList());
            workType.setProductJson(productJson);

            WorkTypeEntity save = workTypeRepository.save(workType);
            WorkTypeDTO workTypeDTO = objectMapper.convertValue(save, WorkTypeDTO.class);


            return new EditWorkTypeResponse(true, "изменили", null, null);
        }
        return new EditWorkTypeResponse(false, "нет сессии", null, null);
    }


    @PostMapping("/get-script-stage")//получить всех
    @ResponseBody
    public GetScriptStageResponse getScriptStage(@RequestParam String key) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new GetScriptStageResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canViewOrder == true) {
    
            List<ScriptStageEntity> all = scriptStageRepository.findAll();
            List<ScriptStageDTO> scriptStageDTOS = objectMapper.convertValue(all, new TypeReference<List<ScriptStageDTO>>() {});
    
            return new GetScriptStageResponse("список передали", scriptStageDTOS);
        }
        return new GetScriptStageResponse("нет сессии", null);
    }
    
    @PostMapping("/add-script-stage")//добавить add-script-stage
    @ResponseBody
    public AddScriptStageResponse addScriptStage(@RequestParam String key, @RequestBody AddScriptStageRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new AddScriptStageResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditOrder == true){
            ScriptStageEntity scriptStage = new ScriptStageEntity();
    
            scriptStage.setId(0);
            scriptStage.setName(body.getScriptStageDTO().getName());
    
            ScriptStageEntity save = scriptStageRepository.save(scriptStage);
            ScriptStageDTO scriptStageDTO = objectMapper.convertValue(save, ScriptStageDTO.class);
    
            return new AddScriptStageResponse("добавлен", scriptStageDTO);
        }
        
        return new AddScriptStageResponse("нет сессии", null);
    }
    
    @PostMapping("/edit-script-stage")//изменить
    @ResponseBody
    public EditScriptStageResponse editScriptStage(@RequestParam String key, @RequestBody EditScriptStageRequest body){
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new EditScriptStageResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditOrder == true){
    
            ScriptStageEntity scriptStage = scriptStageRepository.getById(body.getScriptStageDTO().getId());
            if (scriptStage == null) {
                return new EditScriptStageResponse("scriptStage == null", null);
            }
            scriptStage.setName(body.getScriptStageDTO().getName());
    
            scriptStage.setPriority(body.getScriptStageDTO().getPriority());
            scriptStage.setVisible(body.getScriptStageDTO().getVisible());
            
            scriptStage.setNeedDatePostpone(body.getScriptStageDTO().getNeedDatePostpone());
            scriptStage.setNeedComment(body.getScriptStageDTO().getNeedComment());
    
            ScriptStageEntity save = scriptStageRepository.save(scriptStage);
            ScriptStageDTO scriptStageDTO = objectMapper.convertValue(save, ScriptStageDTO.class);
            
            return new EditScriptStageResponse("изменили", scriptStageDTO);
        }
        return new EditScriptStageResponse("нет сессии", null);
    }
    
    
    @PostMapping("/get-trainers")//получить всех тренеров
    @ResponseBody
    public GetTrainersResponse getTrainers(@RequestParam String key) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new GetTrainersResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canViewDict == true) {

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
            if (session.getUser().getGroup().canEditDict == true){

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
        if (session.getUser().getGroup().canEditDict == true){

            TrainerEntity trainer = trainerRepository.getById(body.getTrainerDTO().getId());//наш тренер
            trainer.setName(body.getTrainerDTO().getName());
    
            trainer.setPayCount(body.getTrainerDTO().getPayCount());
            trainer.setPayPercent(body.getTrainerDTO().getPayPercent());
            
            trainer.setDiscountCount(body.getTrainerDTO().getDiscountCount());
            trainer.setDiscountPercent(body.getTrainerDTO().getDiscountPercent());
            
            trainer.setPriority(body.getTrainerDTO().getPriority());
            trainer.setVisible(body.getTrainerDTO().getVisible());
            
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
        if (session.getUser().getGroup().canViewDict == true){
    
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
        if (session.getUser().getGroup().canEditDict == true){
            body.getProductTypeDTO();
            ProductTypeEntity productType;
            if(body.getProductTypeDTO().getId() == 0){//не нашли такого, добавляем нового
                productType = new ProductTypeEntity();
                productType.setId(0);
            } else {
                productType = productTypeRepository.getById(body.getProductTypeDTO().getId());
            }
            productType.setName(body.getProductTypeDTO().getName());
            productType.setPaymentNonStone(body.getProductTypeDTO().getPaymentNonStone());
    
            productType.setPriority(body.getProductTypeDTO().getPriority());
            productType.setVisible(body.getProductTypeDTO().getVisible());
            
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
        if (session.getUser().getGroup().canViewDict == true){
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
        if (session.getUser().getGroup().canEditDict == true){
            RhinestoneTypeEntity rhinestoneType;
            if(body.getRhinestoneTypeDTO().getId() == 0){
                rhinestoneType = new RhinestoneTypeEntity();
                rhinestoneType.setId(0);
            }else {
                rhinestoneType =  rhinestoneTypeRepository.getById(body.getRhinestoneTypeDTO().getId());
            }
    
            rhinestoneType.setManufacturer(body.getRhinestoneTypeDTO().getManufacturer());
            rhinestoneType.setSizeType(body.getRhinestoneTypeDTO().getSizeType());
    
            rhinestoneType.setPrice(body.getRhinestoneTypeDTO().getPrice());
            rhinestoneType.setPayGluerCount(body.getRhinestoneTypeDTO().getPayGluerCount());
            rhinestoneType.setPayGluerPercent(body.getRhinestoneTypeDTO().getPayGluerPercent());
    
            rhinestoneType.setPriority(body.getRhinestoneTypeDTO().getPriority());
            rhinestoneType.setVisible(body.getRhinestoneTypeDTO().getVisible());
            
            RhinestoneTypeEntity save = rhinestoneTypeRepository.save(rhinestoneType);
            RhinestoneTypeDTO rhinestoneTypeDTO = objectMapper.convertValue(save, RhinestoneTypeDTO.class);
    
            return new EditRhinestoneTypeResponse("добавили, изменили", rhinestoneTypeDTO);
    
        }
        return new EditRhinestoneTypeResponse("нет сессии", null);
    }
    
    @PostMapping("/get-express")//получить все цены за срочность
    @ResponseBody
    public GetExpressResponse getExpress(@RequestParam String key) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new GetExpressResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canViewDict == true) {
    
            List<ExpressEntity> all = expressRepository.findAll();
    
            List<ExpressDTO> expressDTOS = objectMapper.convertValue(all, new TypeReference<List<ExpressDTO>>() {});
    
    
            return new GetExpressResponse("список цен на срочность передан", expressDTOS);
        }
        return new GetExpressResponse("нет сессии", null);
        
    }
    
    @PostMapping("/add-express")//добавить новую цену срочности
    @ResponseBody
    public AddExpressResponse addExpress(@RequestParam String key, @RequestBody AddExpressRequest body) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new AddExpressResponse("нет сессии", null);
        }
        if (session.getUser().getGroup().canEditDict == true) {
            
            if (body.getExpressDTO() == null){
                return new AddExpressResponse("ExpressDTO() == null", null);
    
            }
            ExpressEntity express = new ExpressEntity();
            express.setId(0);
            express.setMaxDays(body.getExpressDTO().getMaxDays());
            express.setMinDays(body.getExpressDTO().getMinDays());
            express.setCost(body.getExpressDTO().getCost());
    
            ExpressEntity save = expressRepository.save(express);
            ExpressDTO expressDTO = objectMapper.convertValue(save, ExpressDTO.class);
    
            return new AddExpressResponse("создали срочность", expressDTO);
        }
        return new AddExpressResponse("нет сессии", null);
        
    }
    
    @PostMapping("/edit-express")//изменить цену срочности
    @ResponseBody
    public EditExpressResponse editExpress(@RequestParam String key, @RequestBody EditExpressRequest body) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new EditExpressResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditDict == true) {
    
            if (body.getExpressDTO() == null){
                return new EditExpressResponse("ExpressDTO() == null", null);
        
            }
            ExpressEntity express = expressRepository.getById(body.getExpressDTO().getId());
            if(express == null){
                return new EditExpressResponse("нет такой Id срочности", null);
            }
            express.setMaxDays(body.getExpressDTO().getMaxDays());
            express.setMinDays(body.getExpressDTO().getMinDays());
            express.setCost(body.getExpressDTO().getCost());
    
            ExpressEntity save = expressRepository.save(express);
            ExpressDTO expressDTO = objectMapper.convertValue(save, ExpressDTO.class);
            return new EditExpressResponse("изменили срочность", expressDTO);
        }
        return new EditExpressResponse("нет сессии", null);
        
    }
    
    @PostMapping("/get-straps")//получить все straps
    @ResponseBody
    public GetStrapsResponse getStraps(@RequestParam String key) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new GetStrapsResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canViewDict == true) {
            List<StrapsEntity> list = strapsRepository.findAll();
    
            List<StrapsDTO> strapsDTOS = objectMapper.convertValue(list, new TypeReference<List<StrapsDTO>>() {});
            return new GetStrapsResponse("список передан", strapsDTOS);
        }
        return new GetStrapsResponse("нет сессии", null);
        
    }
    
    @PostMapping("/add-straps")//добавить новую StrapsEntity
    @ResponseBody
    public AddStrapsResponse addStraps(@RequestParam String key, @RequestBody AddStrapsRequest body) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new AddStrapsResponse("нет сессии", null);
        }
        if (session.getUser().getGroup().canEditDict == true) {
            
            if (body.getStrapsDTO() == null){
                return new AddStrapsResponse("StrapsDTO() == null", null);
                
            }
            StrapsEntity straps = new StrapsEntity();
            straps.setId(0);
            straps.setName(body.getStrapsDTO().getName());
            straps.setCount(body.getStrapsDTO().getCount());
    
            straps.setPriority(body.getStrapsDTO().getPriority());
            straps.setVisible(body.getStrapsDTO().getVisible());
    
            StrapsEntity save = strapsRepository.save(straps);
            StrapsDTO strapsDTO = objectMapper.convertValue(save, StrapsDTO.class);
    
            return new AddStrapsResponse("создали StrapsEntity", strapsDTO);
        }
        return new AddStrapsResponse("нет сессии", null);
        
    }
    
    @PostMapping("/edit-straps")//изменить StrapsEntity
    @ResponseBody
    public EditStrapsResponse editStraps(@RequestParam String key, @RequestBody EditStrapsRequest body) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new EditStrapsResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditDict == true) {
            
            if (body.getStrapsDTO() == null){
                return new EditStrapsResponse("StrapsDTO() == null", null);
            }
    
            StrapsEntity straps = strapsRepository.getById(body.getStrapsDTO().getId());
            if(straps == null){
                return new EditStrapsResponse("нет такой Id", null);
            }
            
            straps.setName(body.getStrapsDTO().getName());
            straps.setCount(body.getStrapsDTO().getCount());
            
            straps.setPriority(body.getStrapsDTO().getPriority());
            straps.setVisible(body.getStrapsDTO().getVisible());
    
            StrapsEntity save = strapsRepository.save(straps);
            StrapsDTO strapsDTO = objectMapper.convertValue(save, StrapsDTO.class);
    
            return new EditStrapsResponse("изменили", strapsDTO);
        }
        return new EditStrapsResponse("нет сессии", null);
        
    }
    
    @PostMapping("/get-price")//получить все price
    @ResponseBody
    public GetPriceResponse getPrice(@RequestParam String key) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new GetPriceResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canViewDict == true) {
    
            List<PriceEntity> all = priceRepository.findAll();
    
            List<PriceDTO> priceDTOS = objectMapper.convertValue(all, new TypeReference<List<PriceDTO>>() {});
            return new GetPriceResponse("список передан", priceDTOS);
        }
        return new GetPriceResponse("нет сессии", null);
        
    }
    
    @PostMapping("/add-price")//добавить новую price
    @ResponseBody
    public AddPriceResponse addPrice(@RequestParam String key, @RequestBody AddPriceRequest body) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new AddPriceResponse("нет сессии", null);
        }
        if (session.getUser().getGroup().canEditDict == true) {
            
            if (body.getPriceDTO() == null){
                return new AddPriceResponse("PriceDTO() == null", null);
            }
            PriceEntity price = new PriceEntity();
            
            price.setId(0);
            price.setName(body.getPriceDTO().getName());
            price.setCount(body.getPriceDTO().getCount());
    
            price.setVisible(body.getPriceDTO().getVisible());
            price.setGroup(body.getPriceDTO().getGroup());
            price.setPriority(body.getPriceDTO().getPriority());
    
            price.setPayGluerCount(body.getPriceDTO().getPayGluerCount());
            price.setPayGluerPercent(body.getPriceDTO().getPayGluerPercent());
    
            PriceEntity save = priceRepository.save(price);
    
            PriceDTO priceDTO = objectMapper.convertValue(save, PriceDTO.class);
    
            return new AddPriceResponse("создали priceDTO", priceDTO);
        }
        return new AddPriceResponse("нет сессии", null);
        
    }
    
    @PostMapping("/edit-price")//изменить price
    @ResponseBody
    public EditPriceResponse editPrice(@RequestParam String key, @RequestBody EditPriceRequest body) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new EditPriceResponse("нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditDict == true) {
            
            if (body.getPriceDTO() == null){
                return new EditPriceResponse("PriceDTO() == null", null);
            }
    
            PriceEntity price = priceRepository.getById(body.getPriceDTO().getId());
            if(price == null){
                return new EditPriceResponse("нет такой Id", null);
            }
    
            price.setName(body.getPriceDTO().getName());
            price.setCount(body.getPriceDTO().getCount());
            
            price.setVisible(body.getPriceDTO().getVisible());
            price.setGroup(body.getPriceDTO().getGroup());
            price.setPriority(body.getPriceDTO().getPriority());
            
            price.setPayGluerCount(body.getPriceDTO().getPayGluerCount());
            price.setPayGluerPercent(body.getPriceDTO().getPayGluerPercent());
            
            PriceEntity save = priceRepository.save(price);
    
            PriceDTO priceDTO = objectMapper.convertValue(save, PriceDTO.class);
    
            return new EditPriceResponse("изменили", priceDTO);
        }
        return new EditPriceResponse("нет сессии", null);
        
    }
}


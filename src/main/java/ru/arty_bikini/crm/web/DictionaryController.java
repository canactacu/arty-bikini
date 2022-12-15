package ru.arty_bikini.crm.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.data.dict.*;
import ru.arty_bikini.crm.dto.dict.*;
import ru.arty_bikini.crm.dto.packet.dict.*;
import ru.arty_bikini.crm.jpa.*;

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
            ProductTypeEntity productType;
            if(body.getProductTypeDTO().getId() == 0){//не нашли такого, добавляем нового
                productType = new ProductTypeEntity();
                productType.setId(0);
            } else {
                productType = productTypeRepository.getById(body.getProductTypeDTO().getId());
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
            RhinestoneTypeEntity rhinestoneType;
            if(body.getRhinestoneTypeDTO().getId() == 0){
                rhinestoneType = new RhinestoneTypeEntity();
                rhinestoneType.setId(0);
            }else {
                rhinestoneType =  rhinestoneTypeRepository.getById(body.getRhinestoneTypeDTO().getId());
            }
            rhinestoneType.setPrice(body.getRhinestoneTypeDTO().getPrice());
            rhinestoneType.setManufacturer(body.getRhinestoneTypeDTO().getManufacturer());
            rhinestoneType.setSizeType(body.getRhinestoneTypeDTO().getSizeType());
    
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
        if (session.getUser().getGroup().canEditColumnForGoogle == true) {
    
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
        if (session.getUser().getGroup().canEditColumnForGoogle == true) {
            
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
        if (session.getUser().getGroup().canEditColumnForGoogle == true) {
    
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
        if (session.getUser().getGroup().canEditColumnForGoogle == true) {
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
        if (session.getUser().getGroup().canEditColumnForGoogle == true) {
            
            if (body.getStrapsDTO() == null){
                return new AddStrapsResponse("StrapsDTO() == null", null);
                
            }
            StrapsEntity straps = new StrapsEntity();
            straps.setId(0);
            straps.setName(body.getStrapsDTO().getName());
            straps.setCount(body.getStrapsDTO().getCount());
    
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
        if (session.getUser().getGroup().canEditColumnForGoogle == true) {
            
            if (body.getStrapsDTO() == null){
                return new EditStrapsResponse("StrapsDTO() == null", null);
            }
    
            StrapsEntity straps = strapsRepository.getById(body.getStrapsDTO().getId());
            if(straps == null){
                return new EditStrapsResponse("нет такой Id", null);
            }
            
            straps.setName(body.getStrapsDTO().getName());;
            straps.setCount(body.getStrapsDTO().getCount());
    
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
        if (session.getUser().getGroup().canEditColumnForGoogle == true) {
    
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
        if (session.getUser().getGroup().canEditColumnForGoogle == true) {
            
            if (body.getPriceDTO() == null){
                return new AddPriceResponse("PriceDTO() == null", null);
            }
            PriceEntity price = new PriceEntity();
            
            price.setId(0);
            price.setName(body.getPriceDTO().getName());
            price.setCount(body.getPriceDTO().getCount());
            price.setHide(body.getPriceDTO().getHide());
            price.setGroup(body.getPriceDTO().getGroup());
    
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
        if (session.getUser().getGroup().canEditColumnForGoogle == true) {
            
            if (body.getPriceDTO() == null){
                return new EditPriceResponse("PriceDTO() == null", null);
            }
    
            PriceEntity price = priceRepository.getById(body.getPriceDTO().getId());
            if(price == null){
                return new EditPriceResponse("нет такой Id", null);
            }
    
            price.setName(body.getPriceDTO().getName());
            price.setCount(body.getPriceDTO().getCount());
            price.setGroup(body.getPriceDTO().getGroup());
            price.setHide(body.getPriceDTO().getHide());
    
            PriceEntity save = priceRepository.save(price);
    
            PriceDTO priceDTO = objectMapper.convertValue(save, PriceDTO.class);
    
            return new EditPriceResponse("изменили", priceDTO);
        }
        return new EditPriceResponse("нет сессии", null);
        
    }
}


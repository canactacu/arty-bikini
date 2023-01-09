package ru.arty_bikini.crm.servise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.arty_bikini.crm.Utils;
import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.data.dict.PriceEntity;
import ru.arty_bikini.crm.data.dict.RhinestoneTypeEntity;
import ru.arty_bikini.crm.data.file.OrderFileEntity;
import ru.arty_bikini.crm.data.orders.OrderScriptStageEntity;
import ru.arty_bikini.crm.data.orders.google.DataGoogleEntity;
import ru.arty_bikini.crm.data.orders.stone.CalcPresetRuleJson;
import ru.arty_bikini.crm.data.orders.stone.OrderRhinestoneAmountEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.data.work.WorkEntity;
import ru.arty_bikini.crm.dto.dict.PriceDTO;
import ru.arty_bikini.crm.dto.dict.RhinestoneTypeDTO;
import ru.arty_bikini.crm.dto.file.OrderFileDTO;
import ru.arty_bikini.crm.dto.orders.OrderScriptStageDTO;
import ru.arty_bikini.crm.dto.orders.stone.CalcPresetRuleDTO;
import ru.arty_bikini.crm.dto.orders.stone.OrderRhinestoneAmountDTO;
import ru.arty_bikini.crm.dto.orders.OrderDTO;
import ru.arty_bikini.crm.dto.work.WorkDTO;
import ru.arty_bikini.crm.jpa.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ColumnService columnService;
    
    @Autowired
    private WorkRepository workRepository;
    
    @Autowired
    private OrderRhinestoneAmountRepository orderRARepository;
    
    @Autowired
    private RhinestoneTypeRepository rhinestoneTypeRepository;
    
    @Autowired
    private ScriptStageRepository scriptStageRepository;
    
    @Autowired
    private OrderScriptStageRepository orderSSRepository;
    
    @Autowired
    private OrderFileRepository orderFileRepository;
    
    @Autowired
    private PriceRepository priceRepository;
    
    @Autowired
    private DataGoogleRepository dataGoogleRepository;

    @Autowired
    private DictionaryService dictionaryService;
    
    //считаем и сохраняем дату отправки
    public void savePackageTime(OrderEntity order, String user, boolean packageNow, OrderDTO orderDTO, int idDataGoogle ) {
    
        if (packageNow) {
            if (orderDTO.getPersonalData()!=null && orderDTO.getPersonalData().getPackageManager()!=null) {
                order.getPersonalData().setPackageOld(true);
                order.getPersonalData().setPackageTime(Utils.toDate(orderDTO.getPersonalData().getPackageManager()));
                order.getPersonalData().setPackageManagerOld(Utils.toDate(orderDTO.getPersonalData().getPackageManager()));
    
                //подписали, кто нажал
                LocalDate date = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                String str = date.format(formatter);
                order.getPersonalData().setUserPackage(order.getPersonalData().getUserPackage() +
                        "|" + user + "  " + str);
            }
           
        }
        else {
    
            if (order.getPersonalData().isPackageOld()) {
                order.getPersonalData().setPackageTime(order.getPersonalData().getPackageManagerOld());
            }
            else {
    
                if (order.getExpress()!=null) {
                    order.getPersonalData().setPackageTime(null);
                }
                else {
    
                    if (order.getDataGoogle()!=null) {
                        DataGoogleEntity dataGoogle = dataGoogleRepository.getById(idDataGoogle);
    
                        if (dataGoogle!=null) {
                            //считаем пошив от даты производства(зависит от мерок из гугла)
                            LocalDate manufacture= dataGoogle.getDateGoogle().toLocalDate();
                            manufacture = manufacture.plusDays(28);
                            order.getPersonalData().setPackageManufacture(manufacture);
    
                            LocalDate client;//считаем дату край от соревнований и нужности клиента
                            if (dataGoogle.getNeededDate() != null) {
                                client = dataGoogle.getNeededDate();
        
                                if (dataGoogle.getNeededDate().isAfter(dataGoogle.getCompetition())) {
                                    client = dataGoogle.getCompetition();
                                }
        
                            }
                            else {
                                client = dataGoogle.getCompetition();
                            }
                            if (order.getPersonalData().getDeliveryTime()==0) {
                                order.getPersonalData().setDeliveryTime(1);
                            }
                            client = client.minusDays(order.getPersonalData().getDeliveryTime());
                            client = client.minusDays(3);
                            order.getPersonalData().setPackageClient(client);
    
                            if (client.isAfter(manufacture)) {//крайняя дата после пошива(нормальное состояние)
                                order.getPersonalData().setPackageTime(manufacture);
                            }
                            else {//пошив не успевает
                                order.getPersonalData().setPackageTime(null);
                            }
                        }
                        else {
                            order.getPersonalData().setPackageTime(null);
                        }
                        
                    }
                    else {
                        order.getPersonalData().setPackageTime(null);
                    }
                }
            }
            
        }
        
    }
    
    //orderEntity преобразуем в OrderDTO
    public OrderDTO toOrderDTO(OrderEntity orderEntity) {
        
        OrderDTO orderDTO = objectMapper.convertValue(orderEntity, OrderDTO.class);
    
        if (orderEntity.getDataGoogle() != null) {
            try {
                Map<Integer, String> integerStringMap = objectMapper.readValue(orderEntity.getDataGoogle().getJson(), new TypeReference<Map<Integer, String>>() {
                });
                    orderDTO.getDataGoogle().setData(integerStringMap);
    
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    
        Map<Integer, String> integerStringMap = columnService.measuresToMap(orderEntity.getMeasuresJson());
        orderDTO.setMeasures(integerStringMap);
       
        //заполняем работу по заказу
        List<WorkEntity> workEntityList = workRepository.getByOrder(orderEntity);
        List<WorkDTO> workDTOList = objectMapper.convertValue(workEntityList, new TypeReference<List<WorkDTO>>() {});
        orderDTO.setWorks(workDTOList);
        
        //добавляем стразы
        List<OrderRhinestoneAmountEntity> order = orderRARepository.getByOrder(orderEntity);
        List<OrderRhinestoneAmountDTO> orderRADTOList = objectMapper.convertValue(order, new TypeReference<List<OrderRhinestoneAmountDTO>>() {});
        orderDTO.setStones(orderRADTOList);
       
        //заполняем поля presetRules и presetRulesJson
        if (orderEntity.getPresetRulesJson() == null || orderEntity.getPresetRulesJson().length() == 0){
            orderDTO.setPresetRules(null);
        }
        else {
            List<CalcPresetRuleJson> data = null;
            try {
                data = objectMapper.readValue(orderEntity.getPresetRulesJson(), new TypeReference<List<CalcPresetRuleJson>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
    
            List<CalcPresetRuleDTO> calcPresetRuleDTOList = new ArrayList<>(data.size());
            
            for (CalcPresetRuleJson datum : data) {
        
                CalcPresetRuleDTO calcPresetRuleDTO = objectMapper.convertValue(datum, CalcPresetRuleDTO.class);
        
                RhinestoneTypeEntity rhinestoneType = rhinestoneTypeRepository.getById(datum.getStoneId());
                
                if (rhinestoneType != null) {
                    RhinestoneTypeDTO rhinestoneTypeDTO = objectMapper.convertValue(rhinestoneType, RhinestoneTypeDTO.class);
                    calcPresetRuleDTO.setStone(rhinestoneTypeDTO);
                }
                calcPresetRuleDTOList.add(calcPresetRuleDTO);
                orderDTO.setPresetRules(calcPresetRuleDTOList);
            }
        }

        //заполняем поля price
        dictionaryService.fillPrice(orderEntity, orderDTO);


        //заполняем поля price
        if (orderEntity.getPriceJson() == null || orderEntity.getPriceJson().length() == 0){
            orderDTO.setPrice(null);
        }
        else {
            List<Integer> data = null;
            try {
                data = objectMapper.readValue(orderEntity.getPriceJson(), new TypeReference<List<Integer>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            List<PriceDTO> priceDTOList = new ArrayList<>(data.size());
            
            for (Integer datum : data){
                PriceEntity price = priceRepository.getById(datum.intValue());
    
                PriceDTO priceDTO = objectMapper.convertValue(price, PriceDTO.class);
                priceDTOList.add(priceDTO);
                orderDTO.setPrice(priceDTOList);
            }
        }
        
        //заполняем поле OrderScriptStageEntity скрипты у лида (просмотреть это ведь в лидах должно быть)
        List<OrderScriptStageEntity> scriptOrderList = orderSSRepository.getByOrder(orderEntity);
        List<OrderScriptStageDTO> orderScriptStageDTOS = objectMapper.convertValue(scriptOrderList, new TypeReference<List<OrderScriptStageDTO>>() {});
        orderDTO.setScript(orderScriptStageDTOS);
        
        //заполняем поле files
        List<OrderFileEntity> fileOrderList = orderFileRepository.getByOrder(orderEntity);
        List<OrderFileDTO> orderFileDTOS = objectMapper.convertValue(fileOrderList, new TypeReference<List<OrderFileDTO>>() {});
        orderDTO.setFiles(orderFileDTOS);
        
        return orderDTO;
    }
    
    //List<OrderEntity> преобразуем в List<OrderDTO>
    public List<OrderDTO> toOrderDTOList(List<OrderEntity> orderEntityList){
    
        List<OrderDTO> orderDTOS = new ArrayList<>();
    
        for (int i = 0; i < orderEntityList.size(); i++) {
            OrderDTO orderDTO = toOrderDTO(orderEntityList.get(i));
            orderDTOS.add(orderDTO);
        }
        
        return orderDTOS;
    }
}

package ru.arty_bikini.crm.servise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.arty_bikini.crm.data.dict.RhinestoneTypeEntity;
import ru.arty_bikini.crm.data.file.OrderFileEntity;
import ru.arty_bikini.crm.data.orders.OrderScriptStageEntity;
import ru.arty_bikini.crm.data.orders.google.DataGoogleEntity;
import ru.arty_bikini.crm.data.orders.stone.CalcPresetRuleJson;
import ru.arty_bikini.crm.data.orders.stone.OrderRhinestoneAmountEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.data.work.WorkEntity;
import ru.arty_bikini.crm.dto.dict.RhinestoneTypeDTO;
import ru.arty_bikini.crm.dto.file.OrderFileDTO;
import ru.arty_bikini.crm.dto.orders.OrderScriptStageDTO;
import ru.arty_bikini.crm.dto.orders.stone.CalcPresetRuleDTO;
import ru.arty_bikini.crm.dto.orders.stone.OrderRhinestoneAmountDTO;
import ru.arty_bikini.crm.dto.orders.OrderDTO;
import ru.arty_bikini.crm.dto.work.WorkDTO;
import ru.arty_bikini.crm.jpa.*;

import java.time.LocalDate;
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
    
    //считаем и сохраняем дату отправки
    public void savePackageTime(OrderEntity order){
        
        if (order.getPersonalData() != null) {
    
            int deliveryTime = order.getPersonalData().getDeliveryTime();
            if (deliveryTime < 0) {
                deliveryTime = 0;
            }
    
            if (order.getPersonalData().getNeededTime() != null) { //менеджер дата, когда нужен
                LocalDate date = order.getPersonalData().getNeededTime().minusDays(deliveryTime);
                date = date.minusDays(3);
                order.getPersonalData().setPackageTime(date);//сохраняем
            
            } else if (order.getPersonalData().getCompetitionTime() != null) {//менеджер дата соревнований
            
                LocalDate date = order.getPersonalData().getCompetitionTime().minusDays(deliveryTime);
                date = date.minusDays(3);
                order.getPersonalData().setPackageTime(date);//сохраняем
            } else if (order.getDataGoogle() != null && order.getDataGoogle().getNeededDate() != null) {//клиент дата, когда нужен
            
                LocalDate date = order.getDataGoogle().getNeededDate().minusDays(deliveryTime);
                date = date.minusDays(3);
                order.getPersonalData().setPackageTime(date);//сохраняем
            } else if (order.getDataGoogle() != null && order.getDataGoogle().getCompetition() != null) {//клиент дата соревнований
            
                LocalDate date = order.getDataGoogle().getCompetition().minusDays(deliveryTime);
                date = date.minusDays(3);
                order.getPersonalData().setPackageTime(date);//сохраняем
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

package ru.arty_bikini.crm.servise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.arty_bikini.crm.data.dict.ProductTypeEntity;
import ru.arty_bikini.crm.data.dict.RhinestoneTypeEntity;
import ru.arty_bikini.crm.data.orders.stone.CalcPresetEntity;
import ru.arty_bikini.crm.data.orders.stone.CalcPresetRuleJson;
import ru.arty_bikini.crm.dto.dict.ProductTypeDTO;
import ru.arty_bikini.crm.dto.dict.RhinestoneTypeDTO;
import ru.arty_bikini.crm.dto.orders.stone.CalcPresetDTO;
import ru.arty_bikini.crm.dto.orders.stone.CalcPresetRuleDTO;
import ru.arty_bikini.crm.jpa.RhinestoneTypeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderStoneService {
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private RhinestoneTypeRepository rhinestoneTypeRepository;
    
    //преобразуем в Entity
    public void toEntity(CalcPresetDTO calcPresetDTO, CalcPresetEntity calcPresetEntity){
        calcPresetEntity.setId(calcPresetDTO.getId());
        calcPresetEntity.setName(calcPresetEntity.getName());
        calcPresetEntity.setPriority(calcPresetEntity.getPriority());
    
        List<CalcPresetRuleDTO> calcPresetRuleDTOList = calcPresetDTO.getRules();
        List<CalcPresetRuleJson> calcPresetRuleJsonList = new ArrayList<>();
    
        for (int i = 0; i < calcPresetRuleDTOList.size(); i++) {
            calcPresetRuleJsonList.add(objectMapper.convertValue(calcPresetRuleDTOList.get(i),  CalcPresetRuleJson.class));
            calcPresetRuleJsonList.get(i).setStoneId(calcPresetRuleDTOList.get(i).getStone().getId());
        }
    
        String rulesJson = null;
        try {
            rulesJson = objectMapper.writeValueAsString(calcPresetRuleJsonList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        calcPresetEntity.setRulesJson(rulesJson);
    }
    //преобразуем в Entity
    
    
    // 1 преобразуем в DTO
    public CalcPresetDTO toDTO(CalcPresetEntity calcPresetEntity){
        CalcPresetDTO calcPresetDTO = objectMapper.convertValue(calcPresetEntity, CalcPresetDTO.class);
    
        List<CalcPresetRuleJson> data = null;
        try {
            data = objectMapper.readValue(calcPresetEntity.getRulesJson(), new TypeReference<List<CalcPresetRuleJson>>() {});
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
        }
        calcPresetDTO.setRules(calcPresetRuleDTOList);
        return calcPresetDTO;
    }
    
    //много преобразуем в DTO
    public List<CalcPresetDTO> toDTOS(List<CalcPresetEntity> calcPresetEntityList){
        List<CalcPresetDTO> calcPresetDTOList = new ArrayList<>(calcPresetEntityList.size());
        
        for (int i = 0; i < calcPresetEntityList.size(); i++) {
            calcPresetDTOList.add(toDTO(calcPresetEntityList.get(i)));
        }
        
        return calcPresetDTOList;
    }
}

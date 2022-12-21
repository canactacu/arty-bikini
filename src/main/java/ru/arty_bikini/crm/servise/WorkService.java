package ru.arty_bikini.crm.servise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.arty_bikini.crm.data.work.WorkEntity;
import ru.arty_bikini.crm.data.work.WorkTypeEntity;
import ru.arty_bikini.crm.dto.dict.ProductTypeDTO;
import ru.arty_bikini.crm.dto.work.WorkTypeDTO;
import ru.arty_bikini.crm.jpa.WorkRepository;
import ru.arty_bikini.crm.jpa.WorkTypeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WorkService {
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private WorkRepository workRepository;
    
    @Autowired
    private WorkTypeRepository workTypeRepository;
    
    @Autowired
    private DictionaryService dictionaryService;
    
    public String toString(List<WorkTypeDTO> workTypeDTOList){
        String worksJson;
    
        List<Integer> ids = new ArrayList<>(workTypeDTOList.size());
        for (int i = 0; i < workTypeDTOList.size(); i++) {
            workTypeDTOList.get(i).getId();
            ids.add(workTypeDTOList.get(i).getId());
        }
        try {
            worksJson = objectMapper.writeValueAsString(ids);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return worksJson;
    }
    
    public List<WorkTypeDTO> toListDto(String worksJson) {
        if (worksJson == null) {
            return Collections.emptyList();
        }
        List<Integer> ids;
        List<WorkTypeDTO> workTypeDTOList = null;
    
        try {
            ids = objectMapper.readValue(worksJson, new TypeReference<List<Integer>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<WorkTypeEntity> workTypeEntityList = new ArrayList<>(ids.size());
        for (int i = 0; i < ids.size(); i++) {
            workTypeEntityList.add(workTypeRepository.getById(ids.get(i).intValue()));
        }
        workTypeDTOList = toListTypeDTO(workTypeEntityList);
    
        return workTypeDTOList;
    }
    
    public List<WorkTypeDTO> toListTypeDTO(List<WorkTypeEntity> workTypeEntityList){
        List<WorkTypeDTO> workTypeDTOList = objectMapper.convertValue(workTypeEntityList, new TypeReference<List<WorkTypeDTO>>() {});
    
        for (int i = 0; i < workTypeDTOList.size(); i++) {
            List<ProductTypeDTO> productTypeDTOList = dictionaryService.productTypeDTOList(workTypeEntityList.get(i).getProductJson());
            workTypeDTOList.get(i).setProductList(productTypeDTOList);
        }
       return workTypeDTOList;
    }
}

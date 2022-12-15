package ru.arty_bikini.crm.servise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.arty_bikini.crm.data.dict.ProductTypeEntity;
import ru.arty_bikini.crm.data.orders.google.MeasureVariantsEntity;
import ru.arty_bikini.crm.data.orders.google.OrderDataTypeEntity;
import ru.arty_bikini.crm.dto.dict.ProductTypeDTO;
import ru.arty_bikini.crm.dto.orders.google.MeasureVariantsDTO;
import ru.arty_bikini.crm.dto.orders.google.OrderDataTypeDTO;
import ru.arty_bikini.crm.jpa.OrderDataTypeRepository;

import java.util.*;

@Service
public class ColumnService {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private DictionaryService dictionaryService;
    
    @Autowired
    private OrderDataTypeRepository orderDataTypeRepository;
    
    public MeasureVariantsDTO toDTO(MeasureVariantsEntity entity) {
        MeasureVariantsDTO dto = objectMapper.convertValue(entity, MeasureVariantsDTO.class);
        
        Set<ProductTypeDTO> set = dictionaryService.productToSet(entity.getProductsJson());
        dto.setProducts(set);
        
        return dto;
    }
    public OrderDataTypeDTO toDTO(OrderDataTypeEntity entity) {
        OrderDataTypeDTO dto = objectMapper.convertValue(entity, OrderDataTypeDTO.class);
        
        Set<ProductTypeDTO> set = dictionaryService.productToSet(entity.getProductJson());
        dto.setProducts(set);
        
        return dto;
    }
    
    // {"1":"aaa"} + "2" + "bb" -> {"1":"aaa","2":"bb"}
    public String measuresToJson(String oldJson, OrderDataTypeEntity column, String value) {
        Map<Integer, String> data = new HashMap<>(measuresToMap(oldJson));
    
        if (orderDataTypeRepository.getById(column.getId()) == null) {
            return null;
        }
        
        data.put(column.getId(), value);

        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Map<Integer, String> measuresToMap(String json) {
        if (json == null || json.length() == 0) {
            return Collections.emptyMap();
        }
        
        Map<Integer, String> data = null;
        try {
            data = objectMapper.readValue(json, new TypeReference<Map<Integer, String>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        
        return data;
    }
    
}

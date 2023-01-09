package ru.arty_bikini.crm.servise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.arty_bikini.crm.data.dict.PriceEntity;
import ru.arty_bikini.crm.data.dict.ProductTypeEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.dto.dict.PriceDTO;
import ru.arty_bikini.crm.dto.dict.ProductTypeDTO;
import ru.arty_bikini.crm.dto.orders.OrderDTO;
import ru.arty_bikini.crm.jpa.PriceRepository;
import ru.arty_bikini.crm.jpa.ProductTypeRepository;

import java.util.*;

@Service
public class DictionaryService {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private PriceRepository priceRepository;
    
    public String productToJson(Set<ProductTypeDTO> set) {
        if (set == null || set.size() == 0) {
            return "[]";
        }
        
        List<Integer> ids = new ArrayList<>(set.size());
    
        for (ProductTypeDTO productType : set) {
    
            if (productTypeRepository.getById(productType.getId()) == null) {
                return null;
            } else {
                ids.add(productType.getId());
            }
    
        }
    
        try {
            return objectMapper.writeValueAsString(ids);//"[1,2,3,4]"
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Set<ProductTypeDTO> productToSet(String json) {
        if (json == null || json.length() == 0) {
            return Collections.emptySet();
        }
    
        List<Integer> ids = null;
        try {
            ids = objectMapper.readValue(json, new TypeReference<List<Integer>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    
        Set<ProductTypeDTO> set = new HashSet<>(ids.size());
    
        for (int id : ids) {
            ProductTypeEntity productTypeEntity = productTypeRepository.getById(id);
            if (productTypeEntity == null) {
                
                ProductTypeDTO productTypeDTO = new ProductTypeDTO();
                productTypeDTO.setId(id);
                productTypeDTO.setName("Ошибка");
                
                set.add(productTypeDTO);
            } else {
                ProductTypeDTO productTypeDTO = objectMapper.convertValue(productTypeEntity, ProductTypeDTO.class);
                set.add(productTypeDTO);
            }
            
        }
        
        return set;
    }
    
    public String productToJson(List<ProductTypeDTO> productTypeDTOSet) {
        Set<ProductTypeDTO> productTypeDTOList = new HashSet<>(productTypeDTOSet);
        return productToJson(productTypeDTOList);
    }
    
    public List<ProductTypeDTO> productTypeDTOList(String productJson){
        Set<ProductTypeDTO> productTypeDTOS = productToSet(productJson);
        List<ProductTypeDTO> productTypeDTOList = new ArrayList<>(productTypeDTOS);
        return productTypeDTOList;
    }

    //заполняем поля price
    public void fillPrice(OrderEntity orderEntity, OrderDTO orderDTO){
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
    }
    
}

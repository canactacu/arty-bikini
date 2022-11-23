package ru.arty_bikini.crm.servise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.arty_bikini.crm.data.dict.TrainerEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.dto.orders.OrderDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ColumnService columnService;
    
    
    //orderEntity преобразуем в OrderDTO
    public OrderDTO toOrderDTO(OrderEntity orderEntity) {
    
        //  System.out.println(objectMapper);
    
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

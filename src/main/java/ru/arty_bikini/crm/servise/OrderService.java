package ru.arty_bikini.crm.servise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.arty_bikini.crm.data.orders.google.DataGoogleEntity;
import ru.arty_bikini.crm.data.orders.stone.OrderRhinestoneAmountEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.data.work.WorkEntity;
import ru.arty_bikini.crm.dto.orders.stone.OrderRhinestoneAmountDTO;
import ru.arty_bikini.crm.dto.orders.OrderDTO;
import ru.arty_bikini.crm.dto.work.WorkDTO;
import ru.arty_bikini.crm.jpa.OrderRhinestoneAmountRepository;
import ru.arty_bikini.crm.jpa.WorkRepository;

import java.time.LocalDate;
import java.util.ArrayList;
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
    
    //orderEntity преобразуем в OrderDTO OrderRhinestoneAmount
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
       
        //заполняем работу по заказу
        List<WorkEntity> workEntityList = workRepository.getByOrder(orderEntity);
        List<WorkDTO> workDTOList = objectMapper.convertValue(workEntityList, new TypeReference<List<WorkDTO>>() {});
        orderDTO.setWorks(workDTOList);
        
        //добавляем стразы
        List<OrderRhinestoneAmountEntity> order = orderRARepository.getByOrder(orderEntity);
        List<OrderRhinestoneAmountDTO> orderRADTOList = objectMapper.convertValue(order, new TypeReference<List<OrderRhinestoneAmountDTO>>() {});
        orderDTO.setStones(orderRADTOList);
       
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

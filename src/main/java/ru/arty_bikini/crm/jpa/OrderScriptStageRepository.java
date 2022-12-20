package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.dict.PriceEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.data.orders.OrderScriptStageEntity;
import ru.arty_bikini.crm.data.orders.stone.OrderRhinestoneAmountEntity;

import java.util.List;

public interface OrderScriptStageRepository extends JpaRepository<OrderScriptStageEntity, Integer> {
    public OrderScriptStageEntity getById(int id);
    
    public List<OrderScriptStageEntity> getByOrder(OrderEntity order);
}

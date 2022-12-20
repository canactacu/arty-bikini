package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.file.OrderFileEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;

import java.util.List;

public interface OrderFileRepository extends JpaRepository<OrderFileEntity, Integer> {
    public OrderFileEntity getById(int id);
    
    public List<OrderFileEntity> getByOrder(OrderEntity order);
}

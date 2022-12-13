package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.arty_bikini.crm.data.orders.stone.OrderRhinestoneAmountEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;

import java.util.List;

public interface OrderRhinestoneAmountRepository extends JpaRepository<OrderRhinestoneAmountEntity, Integer> {
    // DELETE FROM rhinestone_amount WHERE order_id = 9
    @Modifying
    @Query(value = "DELETE FROM rhinestone_amount WHERE order_id = ?", nativeQuery = true)
    public int deleteByOrder(int orderId);
  
    public List<OrderRhinestoneAmountEntity> getByOrder(OrderEntity order);
}

package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.dict.OrderRhinestoneAmountEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;

public interface OrderRhinestoneAmountRepository extends JpaRepository<OrderRhinestoneAmountEntity, Integer> {
}

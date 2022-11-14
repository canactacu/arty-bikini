package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.orders.OrderDataTypeEntity;

public interface OrderDataTypeRepository extends JpaRepository<OrderDataTypeEntity, Integer> {
    public OrderDataTypeEntity getById(int id);//ищет по id
    public OrderDataTypeEntity getByGoogleName(String googleName);
    public OrderDataTypeEntity getByGoogleColumn(int googleColumn);
}

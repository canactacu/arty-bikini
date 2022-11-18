package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.dict.ProductTypeEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;

public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity, Integer> {
    public ProductTypeEntity getById(int id);
}

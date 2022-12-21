package ru.arty_bikini.crm.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.file.FileEntity;
import ru.arty_bikini.crm.data.file.OrderFileEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;

import java.util.List;

public interface OrderFileRepository extends JpaRepository<OrderFileEntity, Integer> {
    public OrderFileEntity getById(int id);
    
    public List<OrderFileEntity> getByOrder(OrderEntity order);
    
    public List<OrderFileEntity> getByFile(FileEntity fileEntity);
}

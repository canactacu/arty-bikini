package ru.arty_bikini.crm.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.arty_bikini.crm.data.dict.TrainerEntity;
import ru.arty_bikini.crm.data.enums.ClientType;
import ru.arty_bikini.crm.data.orders.OrderEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    public List<OrderEntity> findAllByType(ClientType type);//найти всех определенного типа
    public Page<OrderEntity> findAllByType(ClientType type, Pageable pageable);//найти всех определенного типа и по страницам
    public OrderEntity getById(int id);//взять одного клиента по id клиента
    public List<OrderEntity> getByPersonalDataTrainer(TrainerEntity trainer);//найти список заказов по тренеру

}

package ru.arty_bikini.crm.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.arty_bikini.crm.data.dict.TrainerEntity;
import ru.arty_bikini.crm.dto.enums.personalData.ClientType;
import ru.arty_bikini.crm.data.orders.OrderEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer>, JpaSpecificationExecutor<OrderEntity> {
    public List<OrderEntity> findAllByType(ClientType type);//найти всех определенного типа

    public List<OrderEntity> findAllByArchiveAndType(boolean archive, ClientType type);

    public Page<OrderEntity> findAllByType(ClientType type, Pageable pageable);//найти всех определенного типа и по страницам

    public OrderEntity getById(int id);//взять одного клиента по id клиента

    public List<OrderEntity> getByPersonalDataTrainer(TrainerEntity trainer);//найти список заказов по тренеру

    public List<OrderEntity> findByArchiveAndTypeAndStatusInfoMeasureAllOrArchiveAndTypeAndStatusInfoDesignAll(boolean archive, ClientType type, boolean measure, boolean archive1, ClientType type1, boolean design);

}

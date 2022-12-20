package ru.arty_bikini.crm.data.work;

import com.fasterxml.jackson.annotation.JsonFilter;
import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.dto.enums.TypeWork;
import ru.arty_bikini.crm.data.orders.OrderEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//класс описывает работы над заказами order
@Entity(name = "works")
@JsonFilter("entityFilter")
public class WorkEntity {
    private int id;
    private OrderEntity order;//заказ
    
    private String worksJson;//части от заказа
    private UserEntity user;//работник
    private IntervalEntity interval;//интервал
    private TourEntity tour;//если ли встреча и когда

    @ManyToOne(targetEntity = TourEntity.class)//сущности откуда берем переменную из какой табл
    @JoinColumn(name = "tour_id")//даем название колонке
    public TourEntity getTour() {
        return tour;
    }

    public void setTour(TourEntity tour) {
        this.tour = tour;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = OrderEntity.class)//сущности откуда берем переменную из какой табл
    @JoinColumn(name = "order_id")
    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    @Column(name = "works_json")
    public String getWorksJson() {
        return worksJson;
    }

    public void setWorksJson(String worksJson) {
        this.worksJson = worksJson;
    }
    

    @ManyToOne(targetEntity = UserEntity.class)//сущности откуда берем переменную из какой табл
    @JoinColumn(name = "user_id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @ManyToOne(targetEntity = IntervalEntity.class)//сущности откуда берем переменную из какой табл
    @JoinColumn(name = "interval_id")
    public IntervalEntity getInterval() {
        return interval;
    }

    public void setInterval(IntervalEntity interval) {
        this.interval = interval;
    }
}


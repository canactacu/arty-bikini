package ru.arty_bikini.crm.data.work;

import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.dto.enums.TypeWork;
import ru.arty_bikini.crm.data.orders.OrderEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//класс описывает работы над заказами order
@Entity(name = "works")
public class WorkEntity {
    private int id;
    private OrderEntity order;//заказ
    private Set<TypeWork> works;//части чаказа
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

    @Transient
    public Set<TypeWork> getWorks() {
        return works;
    }

    public void setWorks(Set<TypeWork> works) {
        this.works = works;
    }

    @Column(name = "works")
    public String getWorksInternal() {
        // [STRUPS, CUP] -> "STRUPS,CUP"

        StringBuilder result = new StringBuilder();

        for (TypeWork work : this.works) {
            if (!result.isEmpty()) {
                result.append(",");
            }

            result.append(work.name());
        }

        return result.toString();
    }

    public void setWorksInternal(String value) {
        // "STRUPS,CUP" -> ["STRUPS", "CUP"] -> [STRUPS, CUP]

        Set<TypeWork> all = new HashSet<>();

        String[] parts = value.split(",");
        for (String part : parts) {
            TypeWork curr = TypeWork.valueOf(part);

            all.add(curr);
        }

        this.works = all;
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
    @JoinColumn(name = "intetval_id")
    public IntervalEntity getInterval() {
        return interval;
    }

    public void setInterval(IntervalEntity interval) {
        this.interval = interval;
    }
}


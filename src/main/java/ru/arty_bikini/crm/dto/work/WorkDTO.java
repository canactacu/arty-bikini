package ru.arty_bikini.crm.dto.work;

import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.data.enums.TypeWork;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.data.work.TourEntity;
import ru.arty_bikini.crm.dto.UserDTO;
import ru.arty_bikini.crm.dto.orders.OrderDTO;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//класс описывает работы над заказами order
public class WorkDTO {
    private int id;
    private OrderDTO order;//заказ
    private Set<TypeWork> works;//части чаказа
    private UserDTO user;//работник
    private IntervalDTO interval;//интервал
    private TourDTO tour;//доп встреча

    public TourDTO getTour() {
        return tour;
    }

    public void setTour(TourDTO tour) {
        this.tour = tour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public Set<TypeWork> getWorks() {
        return works;
    }

    public void setWorks(Set<TypeWork> works) {
        this.works = works;
    }

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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public IntervalDTO getInterval() {
        return interval;
    }

    public void setInterval(IntervalDTO interval) {
        this.interval = interval;
    }
}


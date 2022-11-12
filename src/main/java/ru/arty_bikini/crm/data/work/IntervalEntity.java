package ru.arty_bikini.crm.data.work;

import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

//интервалы
@Entity(name = "intervals")
public class IntervalEntity {
    private int id;
    private LocalDateTime dateStart;//дата начала работ
    private LocalDateTime dateFinish;//дата окончания работ на производстве
    private TourEntity tour;//доп встреча


    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "data_start")
    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    @Column(name = "data_finish")
    public LocalDateTime getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(LocalDateTime dateFinish) {
        this.dateFinish = dateFinish;
    }

    @ManyToOne(targetEntity = TourEntity.class)//сущности откуда берем переменную из какой табл
    @JoinColumn(name = "tour_id")//даем название колонке
    public TourEntity getTour() {
        return tour;
    }

    public void setTour(TourEntity tour) {
        this.tour = tour;
    }
}

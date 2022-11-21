package ru.arty_bikini.crm.data.work;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.utils.LocalDateToLongDeserializer;
import ru.arty_bikini.crm.utils.LocalDateToLongSerializer;

import javax.persistence.*;
import java.time.LocalDate;

//интервалы
@Entity(name = "intervals")
public class IntervalEntity {
    private int id;
    
    @JsonSerialize(using = LocalDateToLongSerializer.class)
    @JsonDeserialize(using = LocalDateToLongDeserializer.class)
    private LocalDate dateStart;//дата начала работ
    
    @JsonSerialize(using = LocalDateToLongSerializer.class)
    @JsonDeserialize(using = LocalDateToLongDeserializer.class)
    private LocalDate dateFinish;//дата окончания работ на производстве
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
    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    @Column(name = "data_finish")
    public LocalDate getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(LocalDate dateFinish) {
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

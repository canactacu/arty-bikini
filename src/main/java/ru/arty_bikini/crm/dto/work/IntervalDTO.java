package ru.arty_bikini.crm.dto.work;

import ru.arty_bikini.crm.data.work.TourEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class IntervalDTO {
    private int id;
    private LocalDateTime dateStart;
    private LocalDateTime dateFinish;
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

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(LocalDateTime datefinish) {
        this.dateFinish = datefinish;
    }
}

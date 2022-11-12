package ru.arty_bikini.crm.dto.work;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class TourDTO {
    private int id;
    private LocalDateTime tour;//доп встреча

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTour() {
        return tour;
    }

    public void setTour(LocalDateTime tour) {
        this.tour = tour;
    }
}

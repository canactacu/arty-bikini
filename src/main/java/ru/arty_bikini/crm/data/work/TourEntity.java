package ru.arty_bikini.crm.data.work;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class TourEntity {
    private int id;
    private LocalDateTime tour;//доп встреча

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "tour")
    public LocalDateTime getTour() {
        return tour;
    }

    public void setTour(LocalDateTime tour) {
        this.tour = tour;
    }
}

package ru.arty_bikini.crm.data.work;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class TourEntity {
    private int id;
    private LocalDate tour;//доп встреча

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "tour")
    public LocalDate getTour() {
        return tour;
    }

    public void setTour(LocalDate tour) {
        this.tour = tour;
    }
}

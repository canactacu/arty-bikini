package ru.arty_bikini.crm.data.work;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.arty_bikini.crm.utils.LocalDateToLongDeserializer;
import ru.arty_bikini.crm.utils.LocalDateToLongSerializer;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "tour")
@JsonFilter("entityFilter")
public class TourEntity {
    private int id;
    @JsonSerialize(using = LocalDateToLongSerializer.class)
    @JsonDeserialize(using = LocalDateToLongDeserializer.class)
    private LocalDate tour;//доп встреча

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tour_seq_gen")
    @SequenceGenerator(name = "tour_seq_gen", sequenceName = "tour_id_seq")
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

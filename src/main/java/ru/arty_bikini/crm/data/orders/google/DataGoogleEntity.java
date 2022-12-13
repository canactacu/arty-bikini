package ru.arty_bikini.crm.data.orders.google;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.arty_bikini.crm.utils.LocalDateTimeToLongDeserializer;
import ru.arty_bikini.crm.utils.LocalDateTimeToLongSerializer;
import ru.arty_bikini.crm.utils.LocalDateToLongDeserializer;
import ru.arty_bikini.crm.utils.LocalDateToLongSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

//описывает данные, получаемые из гугл
@Entity(name = "data_google")
@JsonFilter("entityFilter")
public class DataGoogleEntity {
    private int id;
    private boolean connected;//соединен с клиентом, обработан
    @JsonSerialize(using = LocalDateTimeToLongSerializer.class)
    @JsonDeserialize(using = LocalDateTimeToLongDeserializer.class)
    private LocalDateTime dateGoogle;//дата заполнения мерок в гугол
    private String name;//имя клиента указанного в гугол
    private String telephon;//телефон клиента указанного в гугол
    private String json;//другие данные из гугла
    
    @JsonSerialize(using = LocalDateToLongSerializer.class)
    @JsonDeserialize(using = LocalDateToLongDeserializer.class)
    private LocalDate neededDate;
    
    @JsonSerialize(using = LocalDateToLongSerializer.class)
    @JsonDeserialize(using = LocalDateToLongDeserializer.class)
    private LocalDate competition;
    
    @Column(name = "needed_date")
    public LocalDate getNeededDate() {
        return neededDate;
    }
    
    public void setNeededDate(LocalDate neededDate) {
        this.neededDate = neededDate;
    }
    
    @Column(name = "competition")
    public LocalDate getCompetition() {
        return competition;
    }
    
    public void setCompetition(LocalDate competition) {
        this.competition = competition;
    }
    
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public boolean getConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Column(name = "date_google")
    public LocalDateTime getDateGoogle() {
        return dateGoogle;
    }

    public void setDateGoogle(LocalDateTime dateGoogle) {
        this.dateGoogle = dateGoogle;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getTelephon() {
        return telephon;
    }

    public void setTelephon(String telephon) {
        this.telephon = telephon;
    }

    @Column(columnDefinition = "TEXT")
    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}

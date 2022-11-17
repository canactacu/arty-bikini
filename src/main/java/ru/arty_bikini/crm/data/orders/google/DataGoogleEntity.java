package ru.arty_bikini.crm.data.orders.google;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

//описывает данные, получаемые из гугл
@Entity(name = "data_google")
public class DataGoogleEntity {
    private int id;
    private boolean connected;//соединен с клиентом, обработан
    private LocalDateTime dateGoogle;//дата заполнения мерок в гугол
    private String name;//имя клиента указанного в гугол
    private String telephon;//телефон клиента указанного в гугол
    private String json;//другие данные из гугла

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

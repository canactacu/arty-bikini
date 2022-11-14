package ru.arty_bikini.crm.data.orders;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//описывает типы данных получаемых от клиента
@Entity(name = "orders_data_type")
public class OrderDataTypeEntity {
    private int id;
    private String name;//имя в системе
    private int googleColumn;//какая колонка
    private String googleName;//имя колонки в гугл таблице

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "google_column")
    public int getGoogleColumn() {
        return googleColumn;
    }

    public void setGoogleColumn(int googleColumn) {
        this.googleColumn = googleColumn;
    }

    @Column(name = "google_name")
    public String getGoogleName() {
        return googleName;
    }

    public void setGoogleName(String googleName) {
        this.googleName = googleName;
    }
}

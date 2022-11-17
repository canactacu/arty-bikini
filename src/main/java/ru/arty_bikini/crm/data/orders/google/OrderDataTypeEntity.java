package ru.arty_bikini.crm.data.orders.google;

import ru.arty_bikini.crm.dto.enums.ColumnImportTarget;

import javax.persistence.*;

//описывает типы данных получаемых от клиента
@Entity(name = "orders_data_type")
public class OrderDataTypeEntity {
    private int id;
    private String name;//имя в системе
    private ColumnImportTarget target;
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
    
    @Column
    @Enumerated(EnumType.STRING)
    public ColumnImportTarget getTarget() {
        return target;
    }

    public void setTarget(ColumnImportTarget target) {
        this.target = target;
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

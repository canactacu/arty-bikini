package ru.arty_bikini.crm.data.dict;

import ru.arty_bikini.crm.dto.enums.design.ManufacturerType;
import ru.arty_bikini.crm.dto.enums.design.SizeTypeRhinston;

import javax.persistence.*;

@Entity(name = "rhinestone_types")
public class RhinestoneTypeEntity {
    private int id;
    private String manufacturer;//1) Производитель
    private String sizeType;//2) Тип (Пришивная и тд)
    private int price;//3) Цена за расклейку 1 шт"
    
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column
    public String getManufacturer() {
        return manufacturer;
    }
    
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    @Column(name = "size_type")
    public String getSizeType() {
        return sizeType;
    }
    
    public void setSizeType(String sizeType) {
        this.sizeType = sizeType;
    }
    
    @Column
    public int getPrice() {
        return price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
}


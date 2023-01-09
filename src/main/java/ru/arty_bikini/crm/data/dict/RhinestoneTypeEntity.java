package ru.arty_bikini.crm.data.dict;

import com.fasterxml.jackson.annotation.JsonFilter;
import ru.arty_bikini.crm.dto.enums.design.ManufacturerType;
import ru.arty_bikini.crm.dto.enums.design.SizeTypeRhinston;

import javax.persistence.*;

@Entity(name = "rhinestone_types")
@JsonFilter("entityFilter")
public class RhinestoneTypeEntity {
    private int id;
    private String manufacturer;//1) Производитель
    private String sizeType;//2) Тип (Пришивная и тд)
    
    private int price;//3) Цена за расклейку 1 шт"
    private int payGluerCount;
    private int payGluerPercent;
    
    private int priority;
    private boolean visible;
    
    @Column(columnDefinition = "INTEGER NOT NULL DEFAULT 0")
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT true")
    public boolean isVisible() {
        return visible;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    @Column(name = "pay_gluer_percent", columnDefinition = "INTEGER NOT NULL DEFAULT 0")
    public int getPayGluerPercent() {
        return payGluerPercent;
    }
    
    public void setPayGluerPercent(int payGluerPercent) {
        this.payGluerPercent = payGluerPercent;
    }
    
    @Column(name = "pay_gluer_count", columnDefinition = "INTEGER NOT NULL DEFAULT 0")
    public int getPayGluerCount() {
        return payGluerCount;
    }
    
    public void setPayGluerCount(int payGluer) {
        this.payGluerCount = payGluer;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rhinestone_types_seq_gen")
    @SequenceGenerator(name = "rhinestone_types_seq_gen", sequenceName = "rhinestone_types_id_seq")
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


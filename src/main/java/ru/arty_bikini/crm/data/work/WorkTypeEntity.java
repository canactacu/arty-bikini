package ru.arty_bikini.crm.data.work;

import com.fasterxml.jackson.annotation.JsonFilter;
import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.data.dict.PriceEntity;

import javax.persistence.*;

@Entity(name = "work_type")
@JsonFilter("entityFilter")
public class WorkTypeEntity {
    private int id;
    private String name;
    
    private int priority;//приоритет
    private boolean visible;//видимость
    
    private boolean seamstress;//отображение для швей
    private boolean gluer;//отображение для расклейщиц
    private boolean gluerAndSeamstress;//отображение для обоих
    
    private int paySeamstress;//цена за работу
    private String productJson;//продукты
    private PriceEntity price;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "work_type_seq_gen")
    @SequenceGenerator(name = "work_type_seq_gen", sequenceName = "work_type_id_seq")
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
    
    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT true")
    public boolean isSeamstress() {
        return seamstress;
    }
    
    public void setSeamstress(boolean seamstress) {
        this.seamstress = seamstress;
    }
    
    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT true")
    public boolean isGluer() {
        return gluer;
    }
    
    public void setGluer(boolean gluer) {
        this.gluer = gluer;
    }
    
    @Column(name = "gluer_seamstress", columnDefinition = "BOOLEAN NOT NULL DEFAULT true")
    public boolean isGluerAndSeamstress() {
        return gluerAndSeamstress;
    }
    
    public void setGluerAndSeamstress(boolean gluerAndSeamstress) {
        this.gluerAndSeamstress = gluerAndSeamstress;
    }
    
    @Column(name = "pay_seamstress", columnDefinition = "INTEGER NOT NULL DEFAULT 0")
    public int getPaySeamstress() {
        return paySeamstress;
    }
    
    public void setPaySeamstress(int paySeamstress) {
        this.paySeamstress = paySeamstress;
    }
    
    @Column(name = "product_json")
    public String getProductJson() {
        return productJson;
    }
    
    public void setProductJson(String productJson) {
        this.productJson = productJson;
    }
    
    @ManyToOne(targetEntity = PriceEntity.class)
    @JoinColumn(name = "price")
    public PriceEntity getPrice() {
        return price;
    }
    
    public void setPrice(PriceEntity price) {
        this.price = price;
    }
}

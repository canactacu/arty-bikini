package ru.arty_bikini.crm.data.dict;

import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


//(тип заказа(куп,платье..))
@Entity(name = "product_types")
@JsonFilter("entityFilter")
public class ProductTypeEntity {
    private int id;
    private String name;//не понятно зачем
    
    private int paymentNonStone;//цена без расклейки
    
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
    
    @Column(name = "payment_non_stone")
    public int getPaymentNonStone() {
        return paymentNonStone;
    }
    
    public void setPaymentNonStone(int paymentNonStone) {
        this.paymentNonStone = paymentNonStone;
    }
    
   }

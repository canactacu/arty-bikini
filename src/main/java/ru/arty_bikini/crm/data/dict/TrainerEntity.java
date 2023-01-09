package ru.arty_bikini.crm.data.dict;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.hibernate.annotations.Generated;

import javax.persistence.*;

@Entity(name = "trainers")
@JsonFilter("entityFilter")
public class TrainerEntity {
    private int id;
    private String name;
    
    private int payCount;//оплата
    private int payPercent;//оплата в процентах
    
    private int discountCount;//скидка
    private int discountPercent;//скидка в процентах
    
    private int priority;
    private boolean visible;
    
    @Column(name = "pay_count", columnDefinition = "INTEGER NOT NULL DEFAULT 0")
    public int getPayCount() {
        return payCount;
    }
    
    public void setPayCount(int payCount) {
        this.payCount = payCount;
    }
    
    @Column(name = "pay_percent", columnDefinition = "INTEGER NOT NULL DEFAULT 0")
    public int getPayPercent() {
        return payPercent;
    }
    
    public void setPayPercent(int payPercent) {
        this.payPercent = payPercent;
    }
    
    @Column(name = "discount_count", columnDefinition = "INTEGER NOT NULL DEFAULT 0")
    public int getDiscountCount() {
        return discountCount;
    }
    
    public void setDiscountCount(int discountCount) {
        this.discountCount = discountCount;
    }
    
    @Column(name = "discount_percent", columnDefinition = "INTEGER NOT NULL DEFAULT 0")
    public int getDiscountPercent() {
        return discountPercent;
    }
    
    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
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
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainers_seq_gen")
    @SequenceGenerator(name = "trainers_seq_gen", sequenceName = "trainers_id_seq")
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
}

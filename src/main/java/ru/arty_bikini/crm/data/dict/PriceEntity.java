package ru.arty_bikini.crm.data.dict;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "price")
public class PriceEntity {
    private int id;
    private String name;
    private int count;
    
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
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
}

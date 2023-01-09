package ru.arty_bikini.crm.data.dict;

import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.*;

//срочность
@Entity(name = "express")
@JsonFilter("entityFilter")
public class ExpressEntity {
    
    private int id;
    private int minDays;
    private int maxDays;
    private int cost;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "express_seq_gen")
    @SequenceGenerator(name = "express_seq_gen", sequenceName = "express_id_seq")
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name = "min_days")
    public int getMinDays() {
        return minDays;
    }
    
    public void setMinDays(int minDays) {
        this.minDays = minDays;
    }
    
    @Column(name = "max_days")
    public int getMaxDays() {
        return maxDays;
    }
    
    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }
    
    @Column
    public int getCost() {
        return cost;
    }
    
    public void setCost(int cost) {
        this.cost = cost;
    }
}

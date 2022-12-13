package ru.arty_bikini.crm.data.orders.stone;

import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//пресеты для калькулятора
@Entity(name = "calc_presets")
@JsonFilter("entityFilter")
public class CalcPresetEntity {
    private int id;
    private String name;
    private int priority;
    private String rulesJson;
    
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
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    @Column(name = "rules_json")
    public String getRulesJson() {
        return rulesJson;
    }
    
    public void setRulesJson(String rulesJson) {
        this.rulesJson = rulesJson;
    }
}

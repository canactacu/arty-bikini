package ru.arty_bikini.crm.data.dict;

import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
//допы (эскиз, дизайн и тд)
@Entity(name = "price")
@JsonFilter("entityFilter")
public class PriceEntity {
    private int id;
    private String name;
    private int count;
    
    private boolean visible;//скрыть\показать
    private String group;//группа для отображения
    private int priority;//приоритет отображения
    
    private int payGluerCount;
    private int payGluerPercent;
    
    @Column(columnDefinition = "INTEGER NOT NULL DEFAULT 0")
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    @Column(columnDefinition = "INTEGER NOT NULL DEFAULT 0")
    public int getPayGluerCount() {
        return payGluerCount;
    }
    
    public void setPayGluerCount(int payGluerCount) {
        this.payGluerCount = payGluerCount;
    }
    
    @Column(columnDefinition = "INTEGER NOT NULL DEFAULT 0")
    public int getPayGluerPercent() {
        return payGluerPercent;
    }
    
    public void setPayGluerPercent(int payGluerPercent) {
        this.payGluerPercent = payGluerPercent;
    }
    
    @Column(name = "\"group\"")
    public String getGroup() {
        return group;
    }
    
    public void setGroup(String group) {
        this.group = group;
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
    
    @Column
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
}

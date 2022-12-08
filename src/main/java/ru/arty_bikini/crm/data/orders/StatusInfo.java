package ru.arty_bikini.crm.data.orders;

import ru.arty_bikini.crm.data.UserEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//класс статусов

@Embeddable
public class StatusInfo {
    
    //Мерки
    private boolean measureAll;//заполненость мерок
    private UserEntity measureBy;//кто заполнил дизайн
    private boolean measureAllTanya;//Таня проверила дизайн
    
    //Дизайн
    private boolean designAll; //заполненость дизайна
    private UserEntity designBy;//кто заполнил дизайн
    private boolean designAllTanya; //Таня проверила
    
    @Column(name = "s_measure_all", columnDefinition = "BOOLEAN NOT NULL DEFAULT false")
    public boolean isMeasureAll() {
        return measureAll;
    }
    
    public void setMeasureAll(boolean measureAll) {
        this.measureAll = measureAll;
    }
    
    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "s_measure_by_id")
    public UserEntity getMeasureBy() {
        return measureBy;
    }
    
    public void setMeasureBy(UserEntity measureBy) {
        this.measureBy = measureBy;
    }
    
    @Column(name = "s_measure_all_tanya", columnDefinition = "BOOLEAN NOT NULL DEFAULT false")
    public boolean isMeasureAllTanya() {
        return measureAllTanya;
    }
    
    public void setMeasureAllTanya(boolean measureAllTanya) {
        this.measureAllTanya = measureAllTanya;
    }
    
    @Column(name = "s_design_all", columnDefinition = "BOOLEAN NOT NULL DEFAULT false")
    public boolean isDesignAll() {
        return designAll;
    }
    
    public void setDesignAll(boolean designAll) {
        this.designAll = designAll;
    }
    
    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "s_design_by_id")
    public UserEntity getDesignBy() {
        return designBy;
    }
    
    public void setDesignBy(UserEntity designBy) {
        this.designBy = designBy;
    }
    
    @Column(name = "s_design_all_tanya", columnDefinition = "BOOLEAN NOT NULL DEFAULT false")
    public boolean isDesignAllTanya() {
        return designAllTanya;
    }
    
    public void setDesignAllTanya(boolean designAllTanya) {
        this.designAllTanya = designAllTanya;
    }
    
}

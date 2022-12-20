package ru.arty_bikini.crm.data.orders;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.arty_bikini.crm.data.dict.PriceEntity;
import ru.arty_bikini.crm.data.dict.ScriptStageEntity;
import ru.arty_bikini.crm.utils.LocalDateTimeToLongDeserializer;
import ru.arty_bikini.crm.utils.LocalDateTimeToLongSerializer;
import ru.arty_bikini.crm.utils.LocalDateToLongDeserializer;
import ru.arty_bikini.crm.utils.LocalDateToLongSerializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "orders_script_stage")
@JsonFilter("entityFilter")
public class OrderScriptStageEntity {
    private int id;
    
    private ScriptStageEntity scriptStage;
    private OrderEntity order;
    
    @JsonSerialize(using = LocalDateTimeToLongSerializer.class)
    @JsonDeserialize(using = LocalDateTimeToLongDeserializer.class)
    private LocalDateTime dateChange;
    @JsonSerialize(using = LocalDateToLongSerializer.class)
    @JsonDeserialize(using = LocalDateToLongDeserializer.class)
    private LocalDate datePostpone;
    
    private String comment;
    
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @ManyToOne(targetEntity = ScriptStageEntity.class)
    @JoinColumn(name = "script_stage_id")
    public ScriptStageEntity getScriptStage() {
        return scriptStage;
    }
    
    public void setScriptStage(ScriptStageEntity scriptStage) {
        this.scriptStage = scriptStage;
    }
    
    @ManyToOne(targetEntity = OrderEntity.class)
    @JoinColumn(name = "order_id")
    public OrderEntity getOrder() {
        return order;
    }
    
    public void setOrder(OrderEntity order) {
        this.order = order;
    }
    
    @Column(name = "date_change")
    public LocalDateTime getDateChange() {
        return dateChange;
    }
    
    public void setDateChange(LocalDateTime dateChange) {
        this.dateChange = dateChange;
    }
    
    @Column(name = "date_postpone")
    public LocalDate getDatePostpone() {
        return datePostpone;
    }
    
    public void setDatePostpone(LocalDate datePostpone) {
        this.datePostpone = datePostpone;
    }
    
    @Column
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
}
package ru.arty_bikini.crm.data.dict;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.arty_bikini.crm.utils.LocalDateToLongDeserializer;
import ru.arty_bikini.crm.utils.LocalDateToLongSerializer;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "script_stage")
@JsonFilter("entityFilter")
public class ScriptStageEntity {
    private int id;
    private String name;
    
    private int priority;
    private boolean visible;
    
    private boolean needDatePostpone;
    private boolean needComment;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "script_stage_seq_gen")
    @SequenceGenerator(name = "script_stage_seq_gen", sequenceName = "script_stage_id_seq")
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column()
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
    
    @Column(name = "need_date_postpone", columnDefinition = "BOOLEAN NOT NULL DEFAULT false")
    public boolean isNeedDatePostpone() {
        return needDatePostpone;
    }
    
    public void setNeedDatePostpone(boolean needDatePostpone) {
        this.needDatePostpone = needDatePostpone;
    }
    
    @Column(name = "need_comment", columnDefinition = "BOOLEAN NOT NULL DEFAULT false")
    public boolean isNeedComment() {
        return needComment;
    }
    
    public void setNeedComment(boolean needComment) {
        this.needComment = needComment;
    }
    
}

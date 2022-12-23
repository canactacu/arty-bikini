package ru.arty_bikini.crm.data.other;

import com.fasterxml.jackson.annotation.JsonFilter;
import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.data.dict.PriceEntity;

import javax.persistence.*;

@Entity(name = "history")
@JsonFilter("entityFilter")
public class HistoryEntity {
    private int id;
    private String entityType;
    private int entityId;
    
    private UserEntity editor;
    private String oldValue;
    private String newValue;
    private String  editType;
    
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name = "entity_type")
    public String getEntityType() {
        return entityType;
    }
    
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
    
    @Column(name = "entity_id", columnDefinition = "INTEGER NOT NULL DEFAULT -1" )
    public int getEntityId() {
        return entityId;
    }
    
    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
    
    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "editor")
    public UserEntity getEditor() {
        return editor;
    }
    
    public void setEditor(UserEntity editor) {
        this.editor = editor;
    }
    
    @Column(name = "old_value")
    public String getOldValue() {
        return oldValue;
    }
    
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }
    
    @Column(name = "new_value")
    public String getNewValue() {
        return newValue;
    }
    
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
    
    @Column(name = "edit_type")
    public String getEditType() {
        return editType;
    }
    
    public void setEditType(String editType) {
        this.editType = editType;
    }
}

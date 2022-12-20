package ru.arty_bikini.crm.data.file;

import com.fasterxml.jackson.annotation.JsonFilter;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.dto.enums.OrderFileCategory;

import javax.persistence.*;

@Entity(name = "order_file")
@JsonFilter("entityFilter")
public class OrderFileEntity {
    private int id;
    private FileEntity file;
    private OrderEntity order;
    
    private OrderFileCategory category;
    private int priority;
    private String comment;
    
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @ManyToOne(targetEntity = FileEntity.class)
    @JoinColumn(name = "file_id")
    public FileEntity getFile() {
        return file;
    }
    
    public void setFile(FileEntity file) {
        this.file = file;
    }
    
    @ManyToOne(targetEntity = OrderEntity.class)
    @JoinColumn(name = "order_id")
    public OrderEntity getOrder() {
        return order;
    }
    
    public void setOrder(OrderEntity order) {
        this.order = order;
    }
    
    @Column
    @Enumerated(EnumType.STRING)
    public OrderFileCategory getCategory() {
        return category;
    }
    
    public void setCategory(OrderFileCategory category) {
        this.category = category;
    }
    
    @Column(columnDefinition = "INT NOT NULL DEFAULT 0")
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    @Column
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
}

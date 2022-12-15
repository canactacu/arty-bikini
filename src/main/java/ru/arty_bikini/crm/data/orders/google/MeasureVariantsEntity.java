package ru.arty_bikini.crm.data.orders.google;

import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.*;

@Entity(name = "measure_variants")
@JsonFilter("entityFilter")
public class MeasureVariantsEntity {
    private int id;
    private String name;
    private int priority;
    
    private OrderDataTypeEntity orderDataType;
    private String description;
    private String googleName;
    private String productsJson;
    
    @Column(columnDefinition = "INT NOT NULL DEFAULT 0")
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
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
    
    @ManyToOne(targetEntity = OrderDataTypeEntity.class)
    @JoinColumn(name = "order_data_type_id")
    public OrderDataTypeEntity getOrderDataType() {
        return orderDataType;
    }
    
    public void setOrderDataType(OrderDataTypeEntity orderDataType) {
        this.orderDataType = orderDataType;
    }
    
    @Column
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name = "google_name")
    public String getGoogleName() {
        return googleName;
    }
    
    public void setGoogleName(String googleName) {
        this.googleName = googleName;
    }
    
    @Column(name = "product_json")
    public String getProductsJson() {
        return productsJson;
    }
    
    public void setProductsJson(String productsJson) {
        this.productsJson = productsJson;
    }
}

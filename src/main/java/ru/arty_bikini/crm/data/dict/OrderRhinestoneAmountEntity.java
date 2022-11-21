package ru.arty_bikini.crm.data.dict;

import ru.arty_bikini.crm.data.orders.OrderEntity;

import javax.persistence.*;

//таблица страз в заказе
@Entity(name = "rhinestone_amount")
public class OrderRhinestoneAmountEntity {
    private int id;
    private RhinestoneTypeEntity rhinestoneType;
    private int count;
    private OrderEntity order;
    
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @ManyToOne(targetEntity = ExpressEntity.class)//сущности откуда берем переменную из какой табл
    @JoinColumn(name = "rhinestone_type_id")
    public RhinestoneTypeEntity getRhinestoneType() {
        return rhinestoneType;
    }
    
    public void setRhinestoneType(RhinestoneTypeEntity rhinestoneType) {
        this.rhinestoneType = rhinestoneType;
    }
    
    @Column
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    @ManyToOne(targetEntity = ExpressEntity.class)//сущности откуда берем переменную из какой табл
    @JoinColumn(name = "order_id")
    public OrderEntity getOrder() {
        return order;
    }
    
    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}

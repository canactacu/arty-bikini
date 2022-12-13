package ru.arty_bikini.crm.data.dict;

import com.fasterxml.jackson.annotation.JsonFilter;
import ru.arty_bikini.crm.dto.enums.measure.CategoryMeasure;

import javax.persistence.*;


//(тип заказа(куп,платье..))
@Entity(name = "product_types")
@JsonFilter("entityFilter")
public class ProductTypeEntity {
    private int id;
    private String name;//не понятно зачем
    private int paymentNonStone;//цена без расклейки
    private CategoryMeasure categoryMeasure;
    
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
    
    @Column(name = "payment_non_stone")
    public int getPaymentNonStone() {
        return paymentNonStone;
    }
    
    public void setPaymentNonStone(int paymentNonStone) {
        this.paymentNonStone = paymentNonStone;
    }
    
    @Column(name = "category_measure")
    @Enumerated(EnumType.STRING)
    public CategoryMeasure getCategoryMeasure() {
        return categoryMeasure;
    }
    
    public void setCategoryMeasure(CategoryMeasure categoryMeasure) {
        this.categoryMeasure = categoryMeasure;
    }
}

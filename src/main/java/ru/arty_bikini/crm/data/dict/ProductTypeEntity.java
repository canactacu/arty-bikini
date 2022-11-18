package ru.arty_bikini.crm.data.dict;

import ru.arty_bikini.crm.dto.enums.measure.CategoryMeasure;

import javax.persistence.*;


//(тип заказа(куп,платье..))
@Entity(name = "product-type")
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
    
    @Column(name = "payment-non-stone")
    public int getPaymentNonStone() {
        return paymentNonStone;
    }
    
    public void setPaymentNonStone(int paymentNonStone) {
        this.paymentNonStone = paymentNonStone;
    }
    
    @Column(name = "category-measure")
    @Enumerated(EnumType.STRING)
    public CategoryMeasure getCategoryMeasure() {
        return categoryMeasure;
    }
    
    public void setCategoryMeasure(CategoryMeasure categoryMeasure) {
        this.categoryMeasure = categoryMeasure;
    }
}

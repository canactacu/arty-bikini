package ru.arty_bikini.crm.data.dict;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "measures")
public class CategoryMeasureEntity {
    private int id;
    private String name;
    //фитнес бикини
    //бодифитнес(или фитнес)
    //фитнес модель(платье)
    
    
    // "Поля:(тип заказа(куп,платье..))
    //1) Название
    //2) Цена
    //3) Енум - тип мерок (тот что уже есть)"
    
    //Таблица ProductTypeEntity
    //фитнес модель(слитный купальник)
    //плавательный купальник
    //другое

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
}

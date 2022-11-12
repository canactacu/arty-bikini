package ru.arty_bikini.crm.data.orders;

import ru.arty_bikini.crm.data.dict.CategoryMeasureEntity;
import ru.arty_bikini.crm.dto.enums.BustNaturalMeasure;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//класс данных о мерках
@Embeddable
public class Measure {

    private CategoryMeasureEntity category;//категория заказа

    @ManyToOne(targetEntity = CategoryMeasureEntity.class)//сущности откуда берем переменную из какой табл
    @JoinColumn(name = "m_category_id")
    public CategoryMeasureEntity getCategory(){return category;}

    private String weight;  //  вес
    private String weightMinus;  // вес,сколько убрать к соревнованиям
    private String growth;  //  рост

    private String bustAround;//  обхват груди
    private String bustUnder;//   обхват под грудью
    private String bustNumber;//  размер груди/импланта
    private String cupSilicone;//  лиф-комментарий по силикону
    private BustNaturalMeasure bustNatural;  //  СВ/СНВ
    private String bustBetween;//   расстояние между чашек
    //private  cupNumber; //   лиф-размер чашки
    private String cupType;//  тип чашки
    private String cupPush;//  лиф-пушап
    private String cupClose;//  тип застежки на лифе

    private String hipAround;//  обхват бедер
    private String hipPH1;//  пш1
    private String hipPH2;//  пш2
    private String hipLength;//  длинна трусов
    private String hipSide;//  ширина боковой лямки
    private String hipFront;//  трусы выкройка спереди
    private String hipFrontComment;//   трусы спереди комментарий
    private String hipBack;//  трусы выкройка сзади
    private String hipBackComment;//  трусы сзади комментарий
    private String hipFormComment;//   комментарий по форме трусов
    private String hipFormFront;//   форма трусов -спереди
    private String hipFormClose;//   форма трусов-закрытость

    private String waistAround;//   обхват талии
    private String arcGeneral;//   общая дуга
    private String formCutoutBack;//   форма выреза сзади
    private String dressLenght;//   длинна платья
    private String lenghtCutUnder;//  длинна разреза снизу
    private String depthCutBack;//   глубина выреза спинки
    private String depthCutFront;//  глубина выреза спереди

}


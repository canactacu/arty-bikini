package ru.arty_bikini.crm.data.orders;

import ru.arty_bikini.crm.data.dict.ExpressEntity;
import ru.arty_bikini.crm.data.orders.google.DataGoogleEntity;
import ru.arty_bikini.crm.dto.enums.personalData.ClientType;

import javax.persistence.*;

//класс описывает клиента-лида и соединение
@Entity(name = "orders")
public class OrderEntity {
    private int id;
    private String name;
    private ClientType type;//статус клиента

    private DataGoogleEntity dataGoogle;//гугол
    private PersonalData personalData;
    private Design design;
    private LeadInfo leadInfo;
    
    private ExpressEntity express ;//срочность
    private boolean measureAll;//заполненость мерок
    private boolean designAll; //заполненость дизайна
    private boolean tanyaOk; //таня проверила

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @ManyToOne(targetEntity = DataGoogleEntity.class)//сущности откуда берем переменную из какой табл
    @JoinColumn(name = "data_google_id")
    public DataGoogleEntity getDataGoogle() {
        return dataGoogle;
    }

    public void setDataGoogle(DataGoogleEntity dataGoogle) {
        this.dataGoogle = dataGoogle;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    @Embedded
    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    @Embedded
    public Design getDesign() {
        return design;
    }

    public void setDesign(Design design) {
        this.design = design;
    }

    @Embedded
    public LeadInfo getLeadInfo() {
        return leadInfo;
    }

    public void setLeadInfo(LeadInfo leadInfo) {
        this.leadInfo = leadInfo;
    }
    
    @ManyToOne(targetEntity = ExpressEntity.class)//сущности откуда берем переменную из какой табл
    @JoinColumn(name = "express_id")
    public ExpressEntity getExpress() {
        return express;
    }
    
    public void setExpress(ExpressEntity express) {
        this.express = express;
    }
    
    @Column(name = "measure_all", columnDefinition = "BOOLEAN NOT NULL DEFAULT false")
    public boolean isMeasureAll() {
        return measureAll;
    }
    
    public void setMeasureAll(boolean measureAll) {
        this.measureAll = measureAll;
    }
    
    @Column(name = "design_all", columnDefinition = "BOOLEAN NOT NULL DEFAULT false")
    public boolean isDesignAll() {
        return designAll;
    }
    
    public void setDesignAll(boolean designAll) {
        this.designAll = designAll;
    }
    
    @Column(name = "tanya_ok", columnDefinition = "BOOLEAN NOT NULL DEFAULT false")
    public boolean isTanyaOk() {
        return tanyaOk;
    }
    
    public void setTanyaOk(boolean tanyaOk) {
        tanyaOk = tanyaOk;
    }
}




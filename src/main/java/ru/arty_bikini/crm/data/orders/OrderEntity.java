package ru.arty_bikini.crm.data.orders;

import com.fasterxml.jackson.annotation.JsonFilter;
import ru.arty_bikini.crm.data.dict.ExpressEntity;
import ru.arty_bikini.crm.data.dict.ProductTypeEntity;
import ru.arty_bikini.crm.data.orders.google.DataGoogleEntity;
import ru.arty_bikini.crm.dto.enums.personalData.ClientType;

import javax.persistence.*;

//класс описывает клиента-лида и соединение
@Entity(name = "orders")
@JsonFilter("entityFilter")
public class OrderEntity {
    private int id;
    private String name;
    private ClientType type;//статус клиента

    private DataGoogleEntity dataGoogle;//гугол
    private PersonalData personalData;
    private Design design;
    private LeadInfo leadInfo;
    
    private ExpressEntity express;//срочность
    
    private ProductTypeEntity product;//тип заказа
    private String measuresJson;
    
    private StatusInfo statusInfo;
   //дата отправки, время доставки
    
    @Embedded
    public StatusInfo getStatusInfo() {
        return statusInfo;
    }
    
    public void setStatusInfo(StatusInfo statusInfo) {
        this.statusInfo = statusInfo;
    }
    
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name = "measures_json")
    public String getMeasuresJson() {
        return measuresJson;
    }
    
    public void setMeasuresJson(String measuresJson) {
        this.measuresJson = measuresJson;
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
    
    @ManyToOne(targetEntity = ProductTypeEntity.class)//сущности откуда берем переменную из какой табл
    @JoinColumn(name = "product_type_id")
    public ProductTypeEntity getProduct() {
        return product;
    }
    
    public void setProduct(ProductTypeEntity product) {
        this.product = product;
    }
    
    
    
}




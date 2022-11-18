package ru.arty_bikini.crm.data.orders;

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
}



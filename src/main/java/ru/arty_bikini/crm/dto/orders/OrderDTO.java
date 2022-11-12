package ru.arty_bikini.crm.dto.orders;

import ru.arty_bikini.crm.data.enums.ClientType;

import javax.persistence.*;

//класс описывает клиента-лида и соединение
public class OrderDTO {
    private int id;
    private String name;
    private ClientType type;

    private PersonalDataDTO personalData;
    private DesignDTO design;
    private LeadInfoDTO leadInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public PersonalDataDTO getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalDataDTO personalData) {
        this.personalData = personalData;
    }

    public DesignDTO getDesign() {
        return design;
    }

    public void setDesign(DesignDTO design) {
        this.design = design;
    }

    public LeadInfoDTO getLeadInfo() {
        return leadInfo;
    }

    public void setLeadInfo(LeadInfoDTO leadInfo) {
        this.leadInfo = leadInfo;
    }
}




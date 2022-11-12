package ru.arty_bikini.crm.dto.orders;

import ru.arty_bikini.crm.data.enums.ColdHotLeadInfo;
import ru.arty_bikini.crm.data.enums.HasManyLeadInfo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class LeadInfoDTO {
    private ColdHotLeadInfo coldHot;//холодный,теплый горячий
    private HasManyLeadInfo hasMany;//А,В,С

    public ColdHotLeadInfo getColdHot() {
        return coldHot;
    }

    public HasManyLeadInfo getHasMany() {
        return hasMany;
    }

    public void setColdHot(ColdHotLeadInfo coldHot) {
        this.coldHot = coldHot;
    }

    public void setHasMany(HasManyLeadInfo hasMany) {
        this.hasMany = hasMany;
    }
}


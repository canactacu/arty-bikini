package ru.arty_bikini.crm.data.orders;

import ru.arty_bikini.crm.dto.enums.lead.ColdHotLeadInfo;
import ru.arty_bikini.crm.dto.enums.lead.HasManyLeadInfo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class LeadInfo {
    private ColdHotLeadInfo coldHot;//холодный, теплый, горячий
    private HasManyLeadInfo hasMany;//А,В,С
    private String commentArchive;//причина попадания в архив
    
    @Column(name = "l_comment_archive")
    public String getCommentArchive() {
        return commentArchive;
    }
    
    public void setCommentArchive(String commentArchive) {
        this.commentArchive = commentArchive;
    }
    
    @Column(name = "l_cold_hot")
    @Enumerated(EnumType.STRING)
    public ColdHotLeadInfo getColdHot() {
        return coldHot;
    }

    @Column(name = "l_has_many")
    @Enumerated(EnumType.STRING)
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


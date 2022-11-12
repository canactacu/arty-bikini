package ru.arty_bikini.crm.dto.orders

import ru.arty_bikini.crm.dto.enums.ColdHotLeadInfo
import ru.arty_bikini.crm.dto.enums.HasManyLeadInfo

class LeadInfoDTO {
    var coldHot //холодный,теплый горячий
            : ColdHotLeadInfo? = null
    var hasMany //А,В,С
            : HasManyLeadInfo? = null
}
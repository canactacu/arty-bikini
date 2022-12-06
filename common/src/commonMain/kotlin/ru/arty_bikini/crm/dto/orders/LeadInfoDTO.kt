package ru.arty_bikini.crm.dto.orders

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.enums.lead.ColdHotLeadInfo
import ru.arty_bikini.crm.dto.enums.lead.HasManyLeadInfo

@Serializable
class LeadInfoDTO {
    var coldHot: ColdHotLeadInfo? = null //холодный,теплый, горячий
    var hasMany: HasManyLeadInfo? = null //А,В,С
    var commentArchive : String? = null//причина попадания в архив
}
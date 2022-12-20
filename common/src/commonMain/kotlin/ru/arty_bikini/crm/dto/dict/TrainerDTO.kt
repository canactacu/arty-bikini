package ru.arty_bikini.crm.dto.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.EntityDTO

@Serializable
class TrainerDTO : EntityDTO() {
    override var id = 0
    var name: String? = null
    
    var payCount = 0
    var payPercent = 0
    
    var discountCount = 0
    var discountPercent = 0
    
    var priority = 0
    var visible = true
}
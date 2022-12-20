package ru.arty_bikini.crm.dto.work

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.EntityDTO
import ru.arty_bikini.crm.dto.dict.PriceDTO
import ru.arty_bikini.crm.dto.dict.ProductTypeDTO
import ru.arty_bikini.crm.dto.orders.stone.CalcPresetRuleDTO

@Serializable
class WorkTypeDTO : EntityDTO() {
    override var id = 0
    var name: String? = null
    
    var priority  = 0
    var visible = true
    
    var seamstress = true
    var gluer = true
    var gluerAndSeamstress = true
    
    var paySeamstress  = 0
    var productList : List<ProductTypeDTO>? = null
    var price : PriceDTO? = null
    
    
}
package ru.arty_bikini.crm.dto.orders

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.EntityDTO
import ru.arty_bikini.crm.dto.dict.ScriptStageDTO

@Serializable
class OrderScriptStageDTO : EntityDTO()  {
    override var id = 0
    
    var scriptStage : ScriptStageDTO? = null
    var order : OrderDTO? = null
    
    var dateChange: Long? = null// время, когда произошли изменения
    var datePostpone: Long? = null//на какое время отложить
    
    var comment : String?=null
}
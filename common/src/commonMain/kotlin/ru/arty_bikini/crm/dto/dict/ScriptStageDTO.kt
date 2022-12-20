package ru.arty_bikini.crm.dto.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.EntityDTO

@Serializable
class ScriptStageDTO : EntityDTO() {
    override var id = 0
    var name : String? = null
    
    var  priority = 0
    var visible = true
    
    var needDatePostpone = false
    var needComment = false
}
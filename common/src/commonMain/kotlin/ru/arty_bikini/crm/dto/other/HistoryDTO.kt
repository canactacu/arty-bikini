package ru.arty_bikini.crm.dto.other

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.EntityDTO
import ru.arty_bikini.crm.dto.UserDTO

@Serializable
class HistoryDTO : EntityDTO(){
    override var id = 0
    var entityType : String? = null
    var entityId = -1
    
    var editor: UserDTO? = null
    var oldValue: String? = null
    var newValue: String? = null
    var editType: String? = null
    
}
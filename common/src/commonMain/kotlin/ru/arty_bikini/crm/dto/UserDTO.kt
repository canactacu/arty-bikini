package ru.arty_bikini.crm.dto

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.enums.TypeWork
import ru.arty_bikini.crm.dto.enums.UserGroup

//для передали данных в интернете
@Serializable
class UserDTO : EntityDTO() {
    override var id = 0
    var login: String? = null
    var group: UserGroup? = null
    var specialisation: String? = null
    var productivityComment : String? = null
    var baseProductivity : Int? = 0;//основная продуктивность(для расклейщиц)
    var productivity : Map<TypeWork, Int>? = null
    
}
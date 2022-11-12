package ru.arty_bikini.crm.dto

import ru.arty_bikini.crm.dto.enums.UserGroup

//для передали данных в интернете
class UserDTO : IEntity {
    override var id = 0
    var login: String? = null
    var group: UserGroup? = null
}
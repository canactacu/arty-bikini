package ru.arty_bikini.crm.dto.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.IEntity

//лямки
@Serializable
class StrapsDTO : IEntity {
    override var id = 0
    var name: String? = null
    var count = 0
}
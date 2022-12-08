package ru.arty_bikini.crm.dto.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.IEntity

@Serializable
class ExpressDTO : IEntity {
    override var id = 0
    var minDays = 0
    var maxDays = 0
    var cost = 0
}
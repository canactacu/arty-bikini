package ru.arty_bikini.crm.dto.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.IEntity

@Serializable
class TrainerDTO : IEntity {
    override var id = 0
    var name: String? = null
}
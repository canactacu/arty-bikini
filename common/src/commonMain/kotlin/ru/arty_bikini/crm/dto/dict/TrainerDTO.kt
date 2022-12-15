package ru.arty_bikini.crm.dto.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.EntityDTO

@Serializable
class TrainerDTO : EntityDTO() {
    override var id = 0
    var name: String? = null
}
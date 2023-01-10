package ru.arty_bikini.crm.dto.filter

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.TrainerDTO
import ru.arty_bikini.crm.dto.enums.personalData.ClientType

@Serializable
class OrderFilter : EntityFilter() {
    var type: ClientType? = null
    var trainer: TrainerDTO? = null

    var createdTimeFrom: Long? = null
    var createdTimeTo: Long? = null
}
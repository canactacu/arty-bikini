package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable

//тело для:изменить тренара.
@Serializable
class EditTrainersRequest (
    var idTrainers: Int = 0,
    var name : String? = null
)
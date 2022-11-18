package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable

//тело для:добавить тренара.
@Serializable
class AddTrainersRequest (
    var name : String? = null //имя тренера
)
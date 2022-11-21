package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable

//тело для:удалить встречу(только, если нет работ)
@Serializable
class DelIntervalRequest (
    var idInterval : Int   //id интервала, в котором будем удалять встречу

)
package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable

//тело для: добавить интервал
@Serializable
class AddIntervalRequest (
    var dateFinish  : Long? = null  //дата встречи
)
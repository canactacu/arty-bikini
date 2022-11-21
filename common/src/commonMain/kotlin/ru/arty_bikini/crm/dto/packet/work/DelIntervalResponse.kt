package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable

//ответ для:удалить встречу(только, если нет работ)
@Serializable
class DelIntervalResponse( //статус
    val statusCode: String
)
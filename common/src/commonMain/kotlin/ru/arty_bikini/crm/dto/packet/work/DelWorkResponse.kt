package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable

//ответ для: удалить работу
@Serializable
class DelWorkResponse( //статус
    val statusCode: String?
)
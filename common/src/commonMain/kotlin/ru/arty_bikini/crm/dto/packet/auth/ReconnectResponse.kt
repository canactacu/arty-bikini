package ru.arty_bikini.crm.dto.packet.auth

import kotlinx.serialization.Serializable

//ответ для:вход в систему без пороля, по сохраненному в браузере коду
@Serializable
class ReconnectResponse( //код доступа
    val success: Boolean
)
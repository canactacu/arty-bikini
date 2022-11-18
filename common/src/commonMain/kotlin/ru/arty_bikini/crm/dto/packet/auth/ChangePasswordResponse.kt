package ru.arty_bikini.crm.dto.packet.auth

import kotlinx.serialization.Serializable

//ответ для:смена пароля
@Serializable
class ChangePasswordResponse(//статус
    val statusCode: String, //пароль
    val password: String
)
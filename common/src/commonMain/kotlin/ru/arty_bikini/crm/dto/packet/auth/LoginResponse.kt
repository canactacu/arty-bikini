package ru.arty_bikini.crm.dto.packet.auth

import kotlinx.serialization.Serializable

//ответ для:точка входа по логину и паролю(
@Serializable
class LoginResponse(
    val errorCode : String,
    val accessCode: String? = null
)
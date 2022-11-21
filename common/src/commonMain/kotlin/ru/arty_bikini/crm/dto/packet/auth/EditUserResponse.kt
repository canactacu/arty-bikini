package ru.arty_bikini.crm.dto.packet.auth

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.UserDTO

//ответ для: редактирование user пользователя
@Serializable
class EditUserResponse(
    val editCode: String,
    val users: List<UserDTO>?  = null
)
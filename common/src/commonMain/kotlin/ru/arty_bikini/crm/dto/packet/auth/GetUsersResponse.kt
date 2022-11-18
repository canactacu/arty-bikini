package ru.arty_bikini.crm.dto.packet.auth

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.UserDTO

//ответ для:возвращает всех пользователей
@Serializable
class GetUsersResponse(
    val users: List<UserDTO>?,  //пользователи без паролей
    val statusCode: String?
)
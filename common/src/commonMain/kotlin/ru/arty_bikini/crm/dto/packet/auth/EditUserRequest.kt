package ru.arty_bikini.crm.dto.packet.auth

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.UserDTO

//тело для: редактирование user пользователя
@Serializable
class EditUserRequest(
    var user: UserDTO
)
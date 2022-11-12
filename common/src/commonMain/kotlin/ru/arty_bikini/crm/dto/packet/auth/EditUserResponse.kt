package ru.arty_bikini.crm.dto.packet.auth

import ru.arty_bikini.crm.dto.UserDTO

//ответ для: редактирование user пользователя
class EditUserResponse(//что хотим отправить
    val editCode: String?,
    val users: List<UserDTO>?
)
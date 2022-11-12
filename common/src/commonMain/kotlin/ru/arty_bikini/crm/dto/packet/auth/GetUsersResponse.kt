package ru.arty_bikini.crm.dto.packet.auth

import ru.arty_bikini.crm.dto.UserDTO

//ответ для:возвращает всех пользователей
class GetUsersResponse(//что должно возвращать
    val users: List<UserDTO>?,  //пользователи без паролей
    val statusCode: String?
)
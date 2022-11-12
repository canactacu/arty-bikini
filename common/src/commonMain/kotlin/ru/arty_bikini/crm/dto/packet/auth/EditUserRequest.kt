package ru.arty_bikini.crm.dto.packet.auth

import ru.arty_bikini.crm.dto.UserDTO

//тело для: редактирование user пользователя
class EditUserRequest {
    var user: UserDTO? = null
}
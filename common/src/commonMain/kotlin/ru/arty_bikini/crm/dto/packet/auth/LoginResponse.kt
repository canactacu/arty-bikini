package ru.arty_bikini.crm.dto.packet.auth

//ответ для:точка входа по логину и паролю(
class LoginResponse    //конструктор
    (//то,что мы передаем клиенту
    val errorCode //ошибка
    : String?, //код доступа
    val accessCode: String?
)
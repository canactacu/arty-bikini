package ru.arty_bikini.crm.dto.packet.auth

//ответ для:смена пароля
class ChangePasswordResponse(//статус
    val statusCode: String?, //пароль
    val password: String?
)
package ru.arty_bikini.crm.dto.packet.auth

//ответ для:вход в систему без пороля, по сохраненному в браузере коду
class ReconnectResponse    //конструктор
    ( //код доступа
    val success: Boolean
)
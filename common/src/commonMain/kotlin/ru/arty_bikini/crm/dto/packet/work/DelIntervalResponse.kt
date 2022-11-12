package ru.arty_bikini.crm.dto.packet.work

//ответ для:удалить встречу(только, если нет работ)
class DelIntervalResponse( //статус
    val statusCode: String?
)
package ru.arty_bikini.crm.dto.packet.work

import ru.arty_bikini.crm.dto.work.WorkDTO

//ответ для:удалить работу со встречи
class DelWorkFromTourResponse(//статус
    val statusCode: String?,
    val workDTO: WorkDTO?
)
package ru.arty_bikini.crm.dto.packet.work

import ru.arty_bikini.crm.dto.work.WorkDTO

//ответ для: добавить работу
class AddWorkResponse(//статус
    val statusCode: String?, //работа
    val workDTO: WorkDTO?
)
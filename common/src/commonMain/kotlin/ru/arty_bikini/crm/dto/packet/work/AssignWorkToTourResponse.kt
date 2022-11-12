package ru.arty_bikini.crm.dto.packet.work

import ru.arty_bikini.crm.dto.work.WorkDTO

//ответ для:добавить работу на отдельную встречу
class AssignWorkToTourResponse(//статус
    val statusCode: String?,
    val workDTO: WorkDTO?
)
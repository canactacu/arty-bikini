package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.work.WorkDTO

//ответ для:добавить работу на отдельную встречу
@Serializable
class AssignWorkToTourResponse(//статус
    val statusCode: String?,
    val workDTO: WorkDTO?
)
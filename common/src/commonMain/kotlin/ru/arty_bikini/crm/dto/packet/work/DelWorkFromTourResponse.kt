package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.work.WorkDTO

//ответ для:удалить работу со встречи
@Serializable
class DelWorkFromTourResponse(//статус
    val statusCode: String,
    val workDTO: WorkDTO? = null
)
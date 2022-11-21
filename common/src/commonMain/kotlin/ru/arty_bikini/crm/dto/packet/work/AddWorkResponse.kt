package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.work.WorkDTO

//ответ для: добавить работу
@Serializable
class AddWorkResponse(//статус
    val statusCode: String, //работа
    val workDTO: WorkDTO? =null
)
package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.work.IntervalDTO

//ответ для: добавить интервал
@Serializable
class AddIntervalResponse(//статус
    val statusCode: String, //интервал
    val intervalDTO: IntervalDTO?= null
)
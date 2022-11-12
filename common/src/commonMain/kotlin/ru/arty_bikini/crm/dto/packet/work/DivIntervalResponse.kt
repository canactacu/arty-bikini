package ru.arty_bikini.crm.dto.packet.work

import ru.arty_bikini.crm.dto.work.IntervalDTO

//ответ для: разделить интервал(добавить встречу)
class DivIntervalResponse(//статус
    val statusCode: String?,
    val intervalDTO: IntervalDTO?
)
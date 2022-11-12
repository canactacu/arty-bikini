package ru.arty_bikini.crm.dto.packet.work

import ru.arty_bikini.crm.dto.work.IntervalDTO

//ответ для: добавить интервал
class AddIntervalResponse(//статус
    val statusCode: String?, //интервал
    val intervalDTO: IntervalDTO?
)
package ru.arty_bikini.crm.dto.packet.work

import ru.arty_bikini.crm.dto.work.IntervalDTO

//ответ для: изменить интервал
class EditIntervalResponse(//статус
    val statusCode: String?, //интервал
    val intervalDTO: IntervalDTO?, //следующий интервал
    val intervalDTONext: IntervalDTO?
)
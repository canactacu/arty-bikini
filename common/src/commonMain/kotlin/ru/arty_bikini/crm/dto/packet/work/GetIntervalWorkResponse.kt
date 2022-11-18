package ru.arty_bikini.crm.dto.packet.work

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.work.IntervalDTO
import ru.arty_bikini.crm.dto.work.WorkDTO

//ответ для:получить список работ...
@Serializable
class GetIntervalWorkResponse(
    val statusCode: String?, //список работ одним списком
    val workDTOList: List<WorkDTO>?, //список интервалов
    val intervalDTOList: List<IntervalDTO>?
)
package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.TrainerDTO

//ответ для:получить список работ...
@Serializable
class GetTrainersResponse(//статус
    val statusCode: String?, //список тренеров
    val trainerList: List<TrainerDTO>?
)
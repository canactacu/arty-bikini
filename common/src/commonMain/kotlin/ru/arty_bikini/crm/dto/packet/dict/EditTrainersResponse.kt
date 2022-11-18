package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.TrainerDTO

//ответ для:изменить тренара
@Serializable
class EditTrainersResponse(//статус
    val statusCode: String?, //измененный тренер
    val trainerDTO: TrainerDTO?
)
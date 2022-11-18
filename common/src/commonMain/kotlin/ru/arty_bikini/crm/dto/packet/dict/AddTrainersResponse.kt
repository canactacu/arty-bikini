package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.TrainerDTO

//ответ для:добавить тренара
@Serializable
class AddTrainersResponse(
    val statusCode: String?, //добавленный тренер
    val trainerDTO: TrainerDTO?
)
package ru.arty_bikini.crm.dto.packet.dict

import ru.arty_bikini.crm.dto.dict.TrainerDTO

//ответ для:добавить тренара
class AddTrainersResponse(//статус
    val statusCode: String?, //добавленный тренер
    val trainerDTO: TrainerDTO?
)
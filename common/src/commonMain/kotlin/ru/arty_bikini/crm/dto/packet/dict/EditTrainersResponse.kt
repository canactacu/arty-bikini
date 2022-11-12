package ru.arty_bikini.crm.dto.packet.dict

import ru.arty_bikini.crm.dto.dict.TrainerDTO

//ответ для:изменить тренара
class EditTrainersResponse(//статус
    val statusCode: String?, //измененный тренер
    val trainerDTO: TrainerDTO?
)
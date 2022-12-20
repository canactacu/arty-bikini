package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.TrainerDTO

//тело для:изменить тренера.
@Serializable
class EditTrainersRequest (
    var trainerDTO : TrainerDTO
)
package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.work.WorkTypeDTO

@Serializable
class AddWorkTypeRequest (
    val WorkType: WorkTypeDTO
)
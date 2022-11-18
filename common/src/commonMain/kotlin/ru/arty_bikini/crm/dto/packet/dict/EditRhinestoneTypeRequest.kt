package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.RhinestoneTypeDTO

@Serializable
class EditRhinestoneTypeRequest (
    var rhinestoneTypeDTO: RhinestoneTypeDTO
)
package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.RhinestoneTypeDTO

@Serializable
class EditRhinestoneTypeResponse(
    val statusCode: String,
    val rhinestoneTypeDTO: RhinestoneTypeDTO?
    )
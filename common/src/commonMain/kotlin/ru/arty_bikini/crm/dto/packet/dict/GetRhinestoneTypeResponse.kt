package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.RhinestoneTypeDTO

@Serializable
class GetRhinestoneTypeResponse(
    val statusCode: String,
    val rhinestoneTypeDTOList: List<RhinestoneTypeDTO>? = null
    )
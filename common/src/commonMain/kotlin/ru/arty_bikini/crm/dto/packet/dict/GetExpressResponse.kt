package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.ExpressDTO

@Serializable
class GetExpressResponse(
    val errorCode: String,
    val expressDTOList: List<ExpressDTO>?= null
    )
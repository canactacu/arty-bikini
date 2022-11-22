package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.ExpressDTO

@Serializable
class AddExpressResponse(
    val errorCode: String,
    val expressDTO: ExpressDTO?= null
    )
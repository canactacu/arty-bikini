package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.StrapsDTO

@Serializable
class AddStrapsResponse(
    val errorCode: String,
    val strapsDTO: StrapsDTO?= null
    )
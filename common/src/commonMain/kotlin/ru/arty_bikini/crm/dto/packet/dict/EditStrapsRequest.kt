package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.StrapsDTO

@Serializable
class EditStrapsRequest (
    var strapsDTO: StrapsDTO
    )
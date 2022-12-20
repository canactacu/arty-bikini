package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.ScriptStageDTO

@Serializable
class AddScriptStageResponse (
    val errorCode: String,
    val scriptStageDTO : ScriptStageDTO? = null
    )
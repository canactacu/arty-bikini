package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.ScriptStageDTO

@Serializable
class AddScriptStageRequest (
    var scriptStageDTO : ScriptStageDTO
        )
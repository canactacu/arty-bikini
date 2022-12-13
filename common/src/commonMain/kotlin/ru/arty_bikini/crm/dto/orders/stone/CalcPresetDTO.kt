package ru.arty_bikini.crm.dto.orders.stone

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.IEntity
import ru.arty_bikini.crm.dto.dict.RhinestoneTypeDTO
import ru.arty_bikini.crm.dto.enums.CalcPresetRuleType

//пресеты для калькулятора
@Serializable
class CalcPresetDTO : IEntity  {
    override var id = 0
    var name: String? = null
    var priority = 0
    var rules: List<CalcPresetRuleDTO>? = null
}

@Serializable
data class CalcPresetRuleDTO(
    var stone: RhinestoneTypeDTO,
    var auto: Boolean,
    var type: CalcPresetRuleType,
    var value: Int
)

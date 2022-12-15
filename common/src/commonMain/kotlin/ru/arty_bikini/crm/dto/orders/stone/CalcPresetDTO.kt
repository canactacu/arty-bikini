package ru.arty_bikini.crm.dto.orders.stone

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.EntityDTO
import ru.arty_bikini.crm.dto.dict.RhinestoneTypeDTO
import ru.arty_bikini.crm.dto.enums.CalcPresetRuleType

//пресеты для калькулятора
@Serializable
class CalcPresetDTO : EntityDTO()  {
    override var id = 0
    var name: String? = null
    var priority = 0
    var rules: List<CalcPresetRuleDTO>? = null
}

@Serializable
class CalcPresetRuleDTO() {
    var stone: RhinestoneTypeDTO? = null
    var auto: Boolean = false
    var type: CalcPresetRuleType = CalcPresetRuleType.COST_PERCENT
    var value: Int = 0
}


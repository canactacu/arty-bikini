package ru.arty_bikini.crm.dto.enums.measure

import ru.arty_bikini.crm.dto.enums.HasDisplayValue

enum class DataDisplayCategory (override val displayName: String) : HasDisplayValue {
    SPECIAL("специальные мерки"),
    MEASURES_UP("Мерки лифа"),
    MEASURES_DOWN("Мерки трусов"),
    MEASURES_OTHER("Мерки (прочие)"),
    
}
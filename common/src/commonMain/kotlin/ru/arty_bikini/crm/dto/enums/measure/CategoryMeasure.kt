package ru.arty_bikini.crm.dto.enums.measure

import ru.arty_bikini.crm.dto.enums.HasDisplayValue

enum class CategoryMeasure (override val displayName: String) : HasDisplayValue {
    WELLNESS("Фитнес Бикини или Велнес"),
    BODYFITNESS("Бодифитнес (или Физик)"),
    SWIMSUIT("Плавательный купальник "),
    DRESS("Фит. модель (платье) "),
    CONECTSWIMSUIT("Фит. модель(слитный куп.) ")
    
}
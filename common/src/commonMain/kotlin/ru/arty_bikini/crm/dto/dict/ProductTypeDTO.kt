package ru.arty_bikini.crm.dto.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.enums.measure.CategoryMeasure

//(тип заказа(куп,платье..))
@Serializable
class ProductTypeDTO {
    var id = 0
    var name: String? = null //не понятно зачем
    var paymentNonStone = 0 //цена без расклейки
    var categoryMeasure: CategoryMeasure? = null
}
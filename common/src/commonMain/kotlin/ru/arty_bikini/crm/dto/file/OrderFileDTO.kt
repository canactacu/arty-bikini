package ru.arty_bikini.crm.dto.file

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.EntityDTO
import ru.arty_bikini.crm.dto.enums.OrderFileCategory
import ru.arty_bikini.crm.dto.orders.OrderDTO

@Serializable
class OrderFileDTO : EntityDTO() {
    override var id = 0
    var file: FileDTO? = null
    var order : OrderDTO? = null
    
    var category : OrderFileCategory? = null
    var priority = 0
    var comment : String? = null
}
package ru.arty_bikini.crm.dto.file

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.EntityDTO
import ru.arty_bikini.crm.dto.UserDTO
import ru.arty_bikini.crm.dto.orders.OrderDTO

@Serializable
class FileDTO : EntityDTO()  {
    override var id = 0
    var uploadAt: Long? = null
    var uploadBy : UserDTO? = null
    
    var sha256: String? = null
    var location: String? = null
    var size: Long = 0
    var orders: List<OrderFileDTO>? = null
}
package ru.arty_bikini.crm.dto.orders.google

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.EntityDTO

@Serializable
class DataGoogleDTO  : EntityDTO() {
    override var id = 0
    var connected: Boolean = false
    var dateGoogle: Long? = null
    var name: String? = null

    var exclude : Boolean = false
    
    var telephon: String? = null
    var data: Map<Int, String>? = null
}
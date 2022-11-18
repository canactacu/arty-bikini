package ru.arty_bikini.crm.dto.orders.google

import kotlinx.serialization.Serializable

@Serializable
class DataGoogleDTO {
    var id = 0
    
    var connected: Boolean = false
    
    var dateGoogle: Long? = null
    
    var name: String? = null
    
    var telephon: String? = null
    
    var data: Map<Int, String>? = null
    
    
}
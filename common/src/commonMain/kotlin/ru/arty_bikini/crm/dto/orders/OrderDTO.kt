package ru.arty_bikini.crm.dto.orders

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.IEntity
import ru.arty_bikini.crm.dto.dict.ExpressDTO
import ru.arty_bikini.crm.dto.dict.ProductTypeDTO
import ru.arty_bikini.crm.dto.enums.personalData.ClientType
import ru.arty_bikini.crm.dto.orders.google.DataGoogleDTO

//класс описывает клиента-лида и соединение
@Serializable
class OrderDTO : IEntity {
    override var id = 0
    var name: String? = null
    var type: ClientType? = null
    var personalData: PersonalDataDTO? = null
    var design: DesignDTO? = null
    var leadInfo: LeadInfoDTO? = null

    val dataGoogle: DataGoogleDTO? = null
    var tanyaOk : Boolean = false
    var designAll : Boolean = false
    var measureAll : Boolean = false
    var express : ExpressDTO? = null
    
    var product : ProductTypeDTO? = null
    var measures : Map<Int, String>? = null
}
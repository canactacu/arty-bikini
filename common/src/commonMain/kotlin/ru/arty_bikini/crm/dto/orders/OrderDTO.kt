package ru.arty_bikini.crm.dto.orders

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.IEntity
import ru.arty_bikini.crm.dto.dict.ExpressDTO
import ru.arty_bikini.crm.dto.orders.stone.OrderRhinestoneAmountDTO
import ru.arty_bikini.crm.dto.dict.ProductTypeDTO
import ru.arty_bikini.crm.dto.enums.personalData.ClientType
import ru.arty_bikini.crm.dto.orders.google.DataGoogleDTO
import ru.arty_bikini.crm.dto.work.WorkDTO

//класс описывает клиента-лида и соединение
@Serializable
class OrderDTO : IEntity {
    override var id = 0
    var name: String? = null
    var type: ClientType = ClientType.LEAD
    
    val dataGoogle: DataGoogleDTO? = null
    var personalData: PersonalDataDTO? = null
    var design: DesignDTO? = null
    var leadInfo: LeadInfoDTO? = null
   
    
    var express : ExpressDTO? = null
    
    var product : ProductTypeDTO? = null
    var measures : Map<Int, String>? = null
    
    var statusInfo : StatusInfoDTO? = null
    
    var works : List<WorkDTO>? = null
    var stones : List<OrderRhinestoneAmountDTO>? = null
}
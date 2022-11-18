package ru.arty_bikini.crm.dto.packet.ordet_data

import kotlinx.serialization.Serializable

//тело для: привязать заказ к результату импорта
@Serializable
class LinkOrderToImportRequest (
    var idOrder : Int = 0,//от клиента
    var idDataGoogle : Int = 0 //от гугла
)
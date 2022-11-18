package ru.arty_bikini.crm.dto.packet.ordet_data

import kotlinx.serialization.Serializable

//тело
@Serializable
class UnlinkOrderFromImportRequest (
    var idOrder : Int = 0   //от клиента
)
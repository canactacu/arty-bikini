package ru.arty_bikini.crm.dto.packet.ordet_data

import kotlinx.serialization.Serializable

@Serializable
class UnlinkOrderFromImportRequest (
    var idOrder : Int  //от клиента
)
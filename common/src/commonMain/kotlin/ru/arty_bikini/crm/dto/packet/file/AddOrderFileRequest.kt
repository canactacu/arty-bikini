package ru.arty_bikini.crm.dto.packet.file

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.file.OrderFileDTO

@Serializable
class AddOrderFileRequest (
    var orderFile : OrderFileDTO
    )
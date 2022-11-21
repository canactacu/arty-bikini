package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.ProductTypeDTO

@Serializable
class GetProductTypesResponse(
    val statusCode: String,
    val productTypeDTOList: List<ProductTypeDTO>? = null
    )
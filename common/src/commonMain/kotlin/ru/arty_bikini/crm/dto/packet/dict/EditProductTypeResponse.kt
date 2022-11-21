package ru.arty_bikini.crm.dto.packet.dict

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.dict.ProductTypeDTO

@Serializable
class EditProductTypeResponse(
    val statusCode: String,
    val productTypeDTO: ProductTypeDTO? = null
)
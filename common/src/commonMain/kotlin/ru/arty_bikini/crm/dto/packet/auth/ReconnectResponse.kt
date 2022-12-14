package ru.arty_bikini.crm.dto.packet.auth

import kotlinx.serialization.Serializable
import ru.arty_bikini.crm.dto.UserDTO
import ru.arty_bikini.crm.dto.packet.BaseResponse

//ответ для:вход в систему без пароля, по сохраненному в браузере коду
@Serializable
class ReconnectResponse( //код доступа
    override val statusCode : String,
    override val ok : Boolean,
    override val displayMessage : String?,
    
    val user : UserDTO? = null
) : BaseResponse()
package ru.arty_bikini.crm.dto.packet

abstract class BaseResponse {
    abstract val ok: Boolean
    abstract val statusCode: String
    abstract val displayMessage: String?
}
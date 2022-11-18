package ru.arty_bikini.crm.dto

import kotlinx.serialization.Serializable

@Serializable
class PageDTO<T> (
    val data : List<T>,
    val page : Int,
    val pageSize : Int,
    val totalItems : Long,
    val totalPages : Int
)


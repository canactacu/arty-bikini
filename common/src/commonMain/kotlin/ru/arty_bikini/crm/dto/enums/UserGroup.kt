package ru.arty_bikini.crm.dto.enums

import kotlin.jvm.JvmField

//права доступа
enum class UserGroup
    (//видит таблицу пользователей
    @JvmField val canEditUsers: Boolean, //видит таблицу лидов,может получить всех тренеров,изменить тренара, добавить тренера
    @JvmField val canViewLeads: Boolean, //видит таблицу клиентов
    @JvmField val canViewClients: Boolean, //видит таблицу архив
    @JvmField val canViewArchive: Boolean, //может добавлять клиента-лида
    @JvmField val canAddClientsLeads: Boolean, //может изменять поля у клиента-лида
    @JvmField val canEditClients: Boolean, //может добавлять работу на производство и интервал,
    @JvmField val canAddWork: Boolean,
    @JvmField val canViewColumnForGoogle: Boolean, //
    @JvmField val canEditColumnForGoogle: Boolean, //изменить столбик гугол(
    @JvmField val canViewProductTypes: Boolean, //список типов мерок(бодифитнес,фитнес бини...),Добавить, изменить,Список типов страз

) {
    // изменить интервал,удалить работу,разделить интервал(добавить встречу),добавить работы на отдельную встречу,
// удалить работу со встречи,получить список работ в интервалах, интервалов по дате
    ADMIN(
        canEditUsers = true,
        canViewLeads = true,
        canViewClients = true,
        canViewArchive = true,
        canAddClientsLeads = true,
        canEditClients = true,
        canAddWork = true,
        canViewColumnForGoogle = true,
        canEditColumnForGoogle = true,
        canViewProductTypes = true
    ),
    TANYA(
        canEditUsers = true,
        canViewLeads = true,
        canViewClients = true,
        canViewArchive = true,
        canAddClientsLeads = true,
        canEditClients = true,
        canAddWork = true,
        canViewColumnForGoogle = true,
        canEditColumnForGoogle = true,
        canViewProductTypes = true,
    
        ),
    MANAGER(
        canEditUsers = false,
        canViewLeads = true,
        canViewClients = true,
        canViewArchive = false,
        canAddClientsLeads = true,
        canEditClients = true,
        canAddWork = true,
        canViewColumnForGoogle = true,
        canEditColumnForGoogle = false,
        canViewProductTypes = true,
    ),
    SEAMSTRESS(
        canEditUsers = false,
        canViewLeads = false,
        canViewClients = true,
        canViewArchive = false,
        canAddClientsLeads = false,
        canEditClients = false,
        canAddWork = false,
        canViewColumnForGoogle = true,
        canEditColumnForGoogle = false,
        canViewProductTypes = true,
    ),
    GLUER(
        canEditUsers = false,
        canViewLeads = false,
        canViewClients = true,
        canViewArchive = false,
        canAddClientsLeads = false,
        canEditClients = false,
        canAddWork = false,
        canViewColumnForGoogle = true,
        canEditColumnForGoogle = false,
        canViewProductTypes = true,
    ),
    ;
}
package ru.arty_bikini.crm.dto.enums

import kotlin.jvm.JvmField

//права доступа
enum class UserGroup // изменить интервал,удалить работу,разделить интервал(добавить встречу),добавить работы на отдельную встречу,
// удалить работу со встречи,получить список работ в интервалах, интервалов по дате
    (//видит таблицу пользователей
    @JvmField val canEditUsers: Boolean, //видит таблицу лидов,может получить всех тренеров,изменить тренара, добавить тренера
    @JvmField val canViewLeads: Boolean, //видит таблицу клиентов
    @JvmField val canViewClients: Boolean, //видит таблицу архив
    @JvmField val canViewArchive: Boolean, //может добавлять клиента-лида
    @JvmField val canAddClientsLeads: Boolean, //может изменять поля у клиента-лида
    @JvmField val canEditClients: Boolean, //может добавлять работу на производство и интервал,
    @JvmField val canAddWork: Boolean
) {
    ADMIN(true, true, true, true, true, true, true), TANYA(true, true, true, true, true, true, true), MANAGER(
        false,
        true,
        true,
        false,
        true,
        true,
        true
    ),
    WORKER(false, false, true, false, false, false, false);
}
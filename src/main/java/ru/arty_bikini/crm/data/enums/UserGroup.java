package ru.arty_bikini.crm.data.enums;

//права доступа
public enum UserGroup {
    ADMIN(true, true, true, true, true, true, true),
    TANYA(true, true, true, true, true, true, true),
    MANAGER(false, true, true, false, true, true, true),
    WORKER(false, false, true, false, false, false, false),
    ;

    public final boolean canEditUsers;//видит таблицу пользователей
    public final boolean canViewLeads;//видит таблицу лидов,может получить всех тренеров,изменить тренара, добавить тренера
    public final boolean canViewClients;//видит таблицу клиентов
    public final boolean canViewArchive;//видит таблицу архив
    public final boolean canAddClientsLeads;//может добавлять клиента-лида
    public final boolean canEditClients;//может изменять поля у клиента-лида
    public final boolean canAddWork;//может добавлять работу на производство и интервал,
    // изменить интервал,удалить работу,разделить интервал(добавить встречу),добавить работы на отдельную встречу,
    // удалить работу со встречи,получить список работ в интервалах, интервалов по дате

    UserGroup(boolean canEditUsers, boolean canViewLeads, boolean canViewClients, boolean canViewArchive, boolean canAddClientsLeads, boolean canEditClients, boolean canAddWork) {
        this.canEditUsers = canEditUsers;
        this.canViewLeads = canViewLeads;
        this.canViewClients = canViewClients;
        this.canViewArchive = canViewArchive;
        this.canAddClientsLeads = canAddClientsLeads;
        this.canEditClients = canEditClients;
        this.canAddWork = canAddWork;
    }
}

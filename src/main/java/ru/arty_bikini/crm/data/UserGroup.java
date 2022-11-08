package ru.arty_bikini.crm.data;

//права доступа
public enum UserGroup {
    ADMIN(true),
    TANYA(true),
    MANAGER(false),
    WORKER(false),
    ;

    public final boolean canEditUsers;

    UserGroup(boolean canEditUsers) {
        this.canEditUsers = canEditUsers;
    }
}

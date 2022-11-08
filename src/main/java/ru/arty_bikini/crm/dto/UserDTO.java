package ru.arty_bikini.crm.dto;

import ru.arty_bikini.crm.data.UserGroup;

//для передали данных в интернете
public class UserDTO {
    private int id;
    private String login;
    private UserGroup group;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserGroup getGroup() {
        return group;
    }

    public void setGroup(UserGroup group) {
        this.group = group;
    }
}

package ru.arty_bikini.crm.data;

import javax.persistence.*;

@Entity(name = "users")//создаем пустую табл имя user
public class UserEntity {
    private int id;
    private String login;
    private String password;
    private UserGroup group;

    @Id //первичный кюч
    @GeneratedValue//заполнять автоматически   создадут столбцы в табл user
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column//создаст столбцы
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "\"group\"")
    @Enumerated(EnumType.STRING)
    public UserGroup getGroup() {
        return group;//группа пользователя
    }

    public void setGroup(UserGroup group) {
        this.group = group;
    }
}

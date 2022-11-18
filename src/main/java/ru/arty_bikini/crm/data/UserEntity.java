package ru.arty_bikini.crm.data;

import ru.arty_bikini.crm.dto.enums.UserGroup;

import javax.persistence.*;

@Entity(name = "users")//создаем пустую табл имя user
public class UserEntity {
    private int id;
    private String login;
    private String password;
    private UserGroup group;
    private String specialisation;//специализация
    private String productivityComment;//комментарий по продуктивности
    private Integer baseProductivity;//основная продуктивность(для расклейщиц)
    private String productivityJson;
    
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
    
    @Column
    public String getSpecialisation() {
        return specialisation;
    }
    
    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }
    
    @Column(name = "productivity_comment")
    public String getProductivityComment() {
        return productivityComment;
    }
    
    public void setProductivityComment(String productivityComment) {
        this.productivityComment = productivityComment;
    }
    
    @Column(name = "base_productivity")
    public Integer getBaseProductivity() {
        return baseProductivity;
    }
    
    public void setBaseProductivity(Integer baseProductivity) {
        this.baseProductivity = baseProductivity;
    }
    
    @Column(name = "productivity_json")
    public String getProductivityJson() {
        return productivityJson;
    }
    
    public void setProductivityJson(String productivityJson) {
        this.productivityJson = productivityJson;
    }
}

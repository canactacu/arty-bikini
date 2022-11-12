package ru.arty_bikini.crm.dto.orders;

import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.data.enums.DesignStraps;
import ru.arty_bikini.crm.data.enums.GlueDesign;
import ru.arty_bikini.crm.data.enums.TextileOrder;
import ru.arty_bikini.crm.dto.UserDTO;

import javax.persistence.*;

//класс данных о дизайне
public class DesignDTO {
    private String color;//цвет купальника
    private TextileOrder textile;//ткань купальника

    private String amount;//количество страз
    private GlueDesign glue;//клей
    private DesignStraps straps;//верхние лямки дизайн
    private String commentDesignUP;//комментарий по дизайну лифа
    private String commentDesignDoun;//комментарий по дизайну низ

    private UserDTO users;//кто заполнил дизайн
    private boolean userTanya;//таня проверила дизайн

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public TextileOrder getTextile() {
        return textile;
    }

    public void setTextile(TextileOrder textile) {
        this.textile = textile;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public GlueDesign getGlue() {
        return glue;
    }

    public void setGlue(GlueDesign glue) {
        this.glue = glue;
    }

    public DesignStraps getStraps() {
        return straps;
    }

    public void setStraps(DesignStraps straps) {
        this.straps = straps;
    }

    public String getCommentDesignUP() {
        return commentDesignUP;
    }

    public void setCommentDesignUP(String commentDesignUP) {
        this.commentDesignUP = commentDesignUP;
    }

    public String getCommentDesignDoun() {
        return commentDesignDoun;
    }

    public void setCommentDesignDoun(String commentDesignDoun) {
        this.commentDesignDoun = commentDesignDoun;
    }

    public UserDTO getUsers() {
        return users;
    }

    public void setUsers(UserDTO users) {
        this.users = users;
    }

    public boolean isUserTanya() {
        return userTanya;
    }

    public void setUserTanya(boolean userTanya) {
        this.userTanya = userTanya;
    }
}


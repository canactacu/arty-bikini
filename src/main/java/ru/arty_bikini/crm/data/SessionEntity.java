package ru.arty_bikini.crm.data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Locale;

@Entity(name = "sessions")//создаем пустую табл имя
public class SessionEntity {
    private int id;
    private UserEntity user;
    private String key;
    private LocalDate start;

    @Id //первичный кюч
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = UserEntity.class)//сущности откуда берем переменную из какой табл
    @JoinColumn(name = "user_id")//даем название колонке
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Column//создаст столбцы
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column//создаст столбцы
    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }
}
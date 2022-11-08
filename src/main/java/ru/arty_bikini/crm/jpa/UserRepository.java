package ru.arty_bikini.crm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.arty_bikini.crm.data.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public UserEntity getByLogin(String login);
    // SELECT * FROM user WHERE login = ?;
    public UserEntity getById(int id);

//    @Query("SELECT * FROM users;")
//    public List<UserEntity> getAll(); можно заменить на findAll()

}

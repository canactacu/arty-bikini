package ru.arty_bikini.crm.servise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.arty_bikini.crm.Utils;
import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.data.enums.UserGroup;
import ru.arty_bikini.crm.dto.UserDTO;
import ru.arty_bikini.crm.jpa.SessionRepository;
import ru.arty_bikini.crm.jpa.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    //если талбица пустая, то создаем 1 пользователя
    @PostConstruct//вызвать функцию при запуске программы(после заполнения всех зависимостей)
    public void createTestUser(){
        List<UserEntity> users = userRepository.findAll();//получили всех пользователей
        if(users.size() == 0){
            //создаем пользователя
            UserEntity user = new UserEntity();//создали строку
            user.setId(0);
            user.setLogin("admin");
            user.setPassword(Utils.SHA256("0000"));//записываем пароль ввиде хеша

            user.setGroup(UserGroup.ADMIN);

            userRepository.save(user);//сохранили в бд
        }
    }

    public List<UserDTO> getUsers() {//получение списка пользователей
        List<UserEntity> all = userRepository.findAll();
        List<UserDTO> result = new ArrayList<>();

        for (int i = 0; i < all.size(); i++) {

            UserDTO dto = new UserDTO();//создали обьект

            dto.setId(all.get(i).getId());//заполнили
            dto.setLogin(all.get(i).getLogin());
            dto.setGroup(all.get(i).getGroup());

            result.add(dto);//добавили в конец списка

        }

        return result;
    }

}

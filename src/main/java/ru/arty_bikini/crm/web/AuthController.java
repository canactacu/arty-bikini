package ru.arty_bikini.crm.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.arty_bikini.crm.Utils;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.data.UserEntity;
import ru.arty_bikini.crm.dto.UserDTO;
import ru.arty_bikini.crm.dto.packet.auth.*;
import ru.arty_bikini.crm.jpa.SessionRepository;
import ru.arty_bikini.crm.jpa.UserRepository;
import ru.arty_bikini.crm.servise.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

//user 174  ("/edit-user")

// /api/auth/login - 4 точка входа по логину и паролю(если нет сохраненного пароля а браузере)
// /api/auth/reconnect - 12(вход в систему без пороля, по сохраненному в браузере коду)
// /api/auth/change-password - смена пароля пользователя
// /api/auth/get-users - возвращает всех пользователей
// /api/auth/edit-user - редактировать пользователя(добавлять, скрывать, менять групу, логин(только админ и таня))


@RestController//контролерр
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserService userService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/login")//точка входа по логину и паролю(
    @ResponseBody
    public LoginResponse login(@RequestParam String login, @RequestParam String password){

        UserEntity user = userRepository.getByLogin(login);
        if (user == null){

            return new LoginResponse("invalidPassword", null);
        }
        String pass1 = user.getPassword();//пароль от бд
        String pass2 = Utils.SHA256(password);//пароль от клиента
        if (pass1.equals(pass2)){

            SessionEntity session = new SessionEntity();

            session.setId(0);//
            session.setUser(user);
            session.setKey(UUID.randomUUID().toString());
            session.setStart(LocalDate.now());

            sessionRepository.save(session);//сохранить пароль

            // если (ПК) id == 0
            // INSERT INTO session (id, user_id, key, start) VALUES (NEXT_ID(), ?, ?, ?);

            // если (ПК) id != 0
            // UPDATE session SET (user_id = ?, key = ?, start = ?) WHERE id = ?;


            return new LoginResponse(null, session.getKey());
        }

        return new LoginResponse("invalidPassword", null);//создаем обьект для ответа клиенту
    }

    @PostMapping("/reconnect")//вход в систему без пороля, по сохраненному в браузере коду
    @ResponseBody//аннотация для ответа
    public ReconnectResponse reconnect(@RequestParam String key){

        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new ReconnectResponse(false);
        }
        //сравнить ключ из бд и ключ из клиента
        if (session.getKey().equals(key)){
            return new ReconnectResponse(true);
        }
        return new ReconnectResponse(false);//ключи не совпали
    }

    @PostMapping("/change-password")//смена пароля
    @ResponseBody//аннотация для ответа
    public ChangePasswordResponse changePassword(@RequestParam String key, @RequestParam int id){
        //проверяем тот ли key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new ChangePasswordResponse("нет сессий", null);
        }
        if (session.getKey().equals(key)){// ключи совпали
            if (session.getUser().getGroup().canEditUsers == true){//должны сравнить права user

                //есть ли такое id
                UserEntity user = userRepository.getById(id); //получить user из таблички логинов и праолей
                if (user == null){
                    return new ChangePasswordResponse("нет такого id", null);
                }

                String password = Utils.generatePassword();//генерируем пароли из класса Utils/
                String passwordSHA = Utils.SHA256(password);//и хешируем
                user.setPassword(passwordSHA);
                //изменить пароль в бд
                userRepository.save(user);
                return new ChangePasswordResponse("password изменен", password);
            }
            return new ChangePasswordResponse("нет прав", null);
        }
        return new ChangePasswordResponse("нет сессий", null);
    }

    @PostMapping("/get-users")//возвращает всех пользователей
    @ResponseBody//аннотация для ответа
    public GetUsersResponse getUsers(@RequestParam String key){
        //проверяем тот ли key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new GetUsersResponse(null, "нет сессий");
        }
        //проверяем совпали ли ключи
        if (session.getKey().equals(key)){// ключи совпали
            if (session.getUser().getGroup().canEditUsers == true){//должны сравнить права user

                List<UserDTO> result = userService.getUsers();//получить всех пользователей,которых можно через инет кидать

                return new GetUsersResponse(result,"пользователи выведены");
            }
            return new GetUsersResponse(null, "нет прав");
        }
        return new GetUsersResponse(null, "нет сессий");

    }

    @PostMapping("/edit-user")//редактирование user пользователя
    @ResponseBody
    public EditUserResponse editUser(@RequestParam String key, @RequestBody EditUserRequest body){
        //проверяем тот ли key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null){
            return new EditUserResponse("нет сессии", null);
        }
        if (session.getKey().equals(key)){// ключи совпали
            if (session.getUser().getGroup().canEditUsers == true){//должны сравнить права user
               
                String loginName = body.getUser().getLogin();//название логина,кот нам передали
                //проверить тело на адекватность
                if (loginName.matches("[a-zA-Zа-яА-Я0-9][a-zA-Zа-яА-Я0-9 _]{2,40}")){
                
                }
                else{
                    return new EditUserResponse("недопустимые имена", null);
                }
                
                //добавить
                //проверить тело на совпаддения имен
                UserEntity sameUser = userRepository.getByLogin(loginName);
                if (sameUser != null) {
                    if (sameUser.getId() != body.getUser().getId()) {
                        return new EditUserResponse("одинаковые имена", null);
                    }
                }
                
    
                if(body.getUser().getId() == 0){//добавить; id = 0  у перданного нам
                   
                    UserEntity user = new UserEntity();

                    user.setId(0);
                    user.setLogin(body.getUser().getLogin());
                    user.setGroup(body.getUser().getGroup());
                    user.setBaseProductivity(body.getUser().getBaseProductivity());
                    user.setSpecialisation(body.getUser().getSpecialisation());
                    user.setProductivityComment(body.getUser().getProductivityComment());
                    
                    try {
                        String productivityJson = objectMapper.writeValueAsString(body.getUser().getProductivity());
    
                        user.setProductivityJson(productivityJson);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //добавить в бд
                    userRepository.save(user);

                    List<UserDTO> result = userService.getUsers();//получить всех пользователей,которых можно через инет кидать

                    return new EditUserResponse("добавлено", result);
                }

                //изменить
                //проверить, есть ли такой id
                int id1 = body.getUser().getId();
                UserEntity user = userRepository.getById(id1);
                if (user == null){
                    return new EditUserResponse("нет пользователя", null);
                }

                user.setLogin(body.getUser().getLogin());
                user.setGroup(body.getUser().getGroup());
                user.setBaseProductivity(body.getUser().getBaseProductivity());
                user.setSpecialisation(body.getUser().getSpecialisation());
                user.setProductivityComment(body.getUser().getProductivityComment());
    
                try {
                    String productivityJson = objectMapper.writeValueAsString(body.getUser().getProductivity());
        
                    user.setProductivityJson(productivityJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //добавить в бд
                userRepository.save(user);

                List<UserDTO> result = userService.getUsers();//получить всех пользователей,которых можно через инет кидать

                return new EditUserResponse("изменено", result);

            }
            return new EditUserResponse("нет сессии", null);
        }
        return new EditUserResponse("нет сессии", null);
    }
}


package ru.arty_bikini.crm.web;

import org.springframework.web.bind.annotation.*;

@RestController//контролерр
@RequestMapping("/api")
public class TestController {

    @PostMapping("/test")//
    @ResponseBody//аннотация для ответа
    public TestControllerResponse test(
            @RequestParam String name, //параметр 2 который из ссылки
            @RequestBody TestControllerRequest body//эта анотация делает сборку тела в body
    ){
        String message = "функция test вызвана, name=" + name + ", info=" + body.getInfo();
        System.out.println(message);

        return new TestControllerResponse(message);//создаем обьект для ответа клиенту
    }
}

class TestControllerRequest {//класс,который описывает обьект тела

    private String info;//параметр тела, который параметр 3

    public String getInfo() {//функ для параметра
        return info;
    }

    public void setInfo(String info) {//функ для параметра
        this.info = info;
    }
}

class TestControllerResponse {//описывает обьект ответа клиенту

    private final String message;

    TestControllerResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

package ru.sadykov.testsecurity.ru.sadykov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sadykov.testsecurity.ru.sadykov.entity.User;
import ru.sadykov.testsecurity.ru.sadykov.service.UserService;

import java.security.Principal;

@RestController
public class AuthenticationController {

    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/authenticated")
    public String pageForAuthenticatedUsers(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return "secured part of web service: " + user.getUserName() + user.getEmail();
    }

    @GetMapping("/read_profile")
    public String pageForReadProfile() {
        return "read profile page";
    }

    @GetMapping("/only_for_admins")
    public String pageForAdminsPage() {
        return "only for admin page";
    }

    /**Principal - позволяет вытащить логин
     * SecurityContextHolder - хранилище данных о пользователе спринга
     * SecurityContext - оборачивается SecurityContextHolder хранит данные в ThredLocal переменной. Данные ThredLocal
     * доступны только в нем, извне к ним добраться нельзя. Для безопасности.
     * Authenticated - оборачивается SecurityContext
     * Authenticated состоит из: Principal - информация о польлзователе; Credentials - хранит пароль пользователя,
     * затирается для безопасности; Authorities - хранит права доступа*/


}
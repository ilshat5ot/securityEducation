package ru.sadykov.testsecurity.ru.sadykov.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthenticationController {

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/authenticated")
    public String pageForAuthenticatedUsers(Principal principal) {
        return "secured part of web service: " + principal.getName();
    }
    /**Principal - позволяет вытащить логин
     * SecurityContextHolder - хранилище данных о пользователе спринга
     * SecurityContext - оборачивается SecurityContextHolder хранит данные в ThredLocal переменной. Данные ThredLocal
     * доступны только в нем, извне к ним добраться нельзя. Для безопасности.
     * Authenticated - оборачивается SecurityContext
     * Authenticated состоит из: Principal - информация о польлзователе; Credentials - хранит пароль пользователя,
     * затирается для безопасности; Authorities - хранит права доступа*/


}
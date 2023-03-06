package ru.sadykov.testsecurity.ru.sadykov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
/**Вместо extends WebSecurityConfigurerAdapter используем новый подход @EnableWebSecurity*/
public class SecurityConfig {

    /***/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/").permitAll()
                .requestMatchers("/authenticated/**").authenticated()
        ).formLogin()
                .and()
                .logout().logoutSuccessUrl("/");
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$10$DZgsBGRJRTbfnqxaVIUW3.ZWOcGk8uxRAN5bmuKotfHtdmiZ1D95q")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$DZgsBGRJRTbfnqxaVIUW3.ZWOcGk8uxRAN5bmuKotfHtdmiZ1D95q")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    /**По умолчанию spring наш запрос обрабатывает по адресу security/login мы можем поменять это применив
     * loginProcessingUrl("/hellologin")
     * После авторизации мы попадаем на запрашиваемую страницу если у нас есть права страницу
     * logout().logoutSuccessUrl("/") - как разлагинились нас перебросит на корневой адрес сайта*/

    /**UserDetails - минимальная информация о пользователях
     * InMemoryUserDetailsManager - имплементирует UserDetailsService, хранит данные о пользователях в памяти*/


}
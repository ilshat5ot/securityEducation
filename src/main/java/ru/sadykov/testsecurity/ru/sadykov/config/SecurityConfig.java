package ru.sadykov.testsecurity.ru.sadykov.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.sadykov.testsecurity.ru.sadykov.service.UserService;

@Configuration
@EnableWebSecurity(debug = true)
/**Вместо extends WebSecurityConfigurerAdapter используем новый подход @EnableWebSecurity*/
public class SecurityConfig {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**Когда мы говорим что у пользователя должна быть роль (hasRole) ADMIN то спринг преобразует это в
     * ROLE_ADMIN и ищет соответствие в БД; А когда проверяются Authority данные с БД сравниваются
     * один к одному*/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/").permitAll()
                .requestMatchers("/authenticated/**").authenticated()
                        .requestMatchers("/only_for_admins/**").hasRole("ADMIN")
                        .requestMatchers("/read_profile/**").hasAuthority("READ_PROFILE")
        ).formLogin()
                .and()
                .logout().logoutSuccessUrl("/");
        return http.build();
    }

   /**daoAuth
    * В этом варианте мы создаем свои собственные сущности которыми хотим управлять с помощью security
    * Обязанности DaoAuthenticationProvider сказать существет пользователь или нет, если да, то положи в
    * SpringSecurityContext.
    * Для того что бы узнать существует пользователь или нет нужно засетить в DaoAuthenticationProvider
    * бин UserDetailsService*/
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }
    /**Преобразователь паролей - из текста будет получать хеш
     * Данный бин нужно засетить в DaoAuthenticationProvider*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**По умолчанию spring наш запрос обрабатывает по адресу security/login мы можем поменять это применив
     * loginProcessingUrl("/hellologin")
     * После авторизации мы попадаем на запрашиваемую страницу если у нас есть права страницу
     * logout().logoutSuccessUrl("/") - как разлагинились нас перебросит на корневой адрес сайта*/

    /**UserDetails - минимальная информация о пользователях
     * InMemoryUserDetailsManager - имплементирует UserDetailsService, хранит данные о пользователях в памяти*/

}
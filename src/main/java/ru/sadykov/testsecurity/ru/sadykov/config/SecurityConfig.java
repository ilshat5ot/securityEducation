package ru.sadykov.testsecurity.ru.sadykov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
/**Вместо extends WebSecurityConfigurerAdapter используем новый подход @EnableWebSecurity*/
public class SecurityConfig {



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

    /**jdbc auth
     * Этот вид аутентификации будет работать при соблюдении простой структуры БД описанной в dbData
     * коменты при первом запуске программы нужно убрать*/
    @Bean
    public JdbcUserDetailsManager users(DataSource dataSource) {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$10$DZgsBGRJRTbfnqxaVIUW3.ZWOcGk8uxRAN5bmuKotfHtdmiZ1D95q")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$10$DZgsBGRJRTbfnqxaVIUW3.ZWOcGk8uxRAN5bmuKotfHtdmiZ1D95q")
//                .roles("USER", "ADMIN")
//                .build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//        /**Если такие данные существуют, то они удаляются из БД*/
//        if (jdbcUserDetailsManager.userExists(user.getUsername())){
//            jdbcUserDetailsManager.deleteUser(user.getUsername());
//        }
//        if (jdbcUserDetailsManager.userExists(admin.getUsername())){
//            jdbcUserDetailsManager.deleteUser(admin.getUsername());
//        }
//        /**Создвем пользователей в БД*/
//        jdbcUserDetailsManager.createUser(user);
//        jdbcUserDetailsManager.createUser(admin);
        return jdbcUserDetailsManager;
    }

//    @Bean
//    public DataSource dataSource() {
//        return new DriverManagerDataSource();
//    }


    /**По умолчанию spring наш запрос обрабатывает по адресу security/login мы можем поменять это применив
     * loginProcessingUrl("/hellologin")
     * После авторизации мы попадаем на запрашиваемую страницу если у нас есть права страницу
     * logout().logoutSuccessUrl("/") - как разлагинились нас перебросит на корневой адрес сайта*/

    /**UserDetails - минимальная информация о пользователях
     * InMemoryUserDetailsManager - имплементирует UserDetailsService, хранит данные о пользователях в памяти*/

}
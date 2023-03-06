package ru.sadykov.testsecurity.ru.sadykov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sadykov.testsecurity.ru.sadykov.entity.Role;
import ru.sadykov.testsecurity.ru.sadykov.entity.User;
import ru.sadykov.testsecurity.ru.sadykov.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

/**Задача сервиса по имени пользователя педоставить самого юзера*/
@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    /**Задача метода по имени пользователя вернуть юзера из БД
     * User - это наша сущность, в нем мы можем нагородить все что угодно
     * А вот все что нужно спрингу это: имя, пароль, права доступа всем этим заведует UserDetails
     * Метод возвращает спрингового юзера. Поэтому мы потрошим данные из своего юзера и заносим в спринговый*/
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    /**Метод берет пользовательскую коллекцию ролей и преобразовывает ее  в колекцию GrantedAuthority*/
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}

package ru.sadykov.testsecurity.ru.sadykov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sadykov.testsecurity.ru.sadykov.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}

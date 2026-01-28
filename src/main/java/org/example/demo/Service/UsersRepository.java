package org.example.demo.Service;

import org.example.demo.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long> {
    boolean existsUsersByEmail(String eMail);
    Users getUsersByEmail(String eMail);
    Users findByEmail(String email);
}

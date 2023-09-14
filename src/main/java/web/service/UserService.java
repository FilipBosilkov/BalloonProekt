package web.service;


import web.model.User;
import web.model.enumeration.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;

public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword, String name, String surname, LocalDate dateOfBirth, Role role);
}

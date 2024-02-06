package web.service.impl;


import web.exceptions.InvalidArgumentsException;
import web.exceptions.PasswordsDoNotMatchException;
import web.exceptions.UsernameAlreadyExistsException;
import web.model.User;
import web.model.enumeration.Role;
import web.repsitory.UserRepository;
import web.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, LocalDate dateOfBirth,Role role) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty())
            throw new InvalidArgumentsException();

        if(!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();

        if(this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);

        User user = new User(username,passwordEncoder.encode(password),name,surname,dateOfBirth,role);
        return userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}

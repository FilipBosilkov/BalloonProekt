package web.service;

import web.model.User;

public interface AuthenticationService {
    User login(String username, String password);
}

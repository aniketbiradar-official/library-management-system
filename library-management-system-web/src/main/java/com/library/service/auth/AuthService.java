package com.library.service.auth;

import com.library.dao.user.UserDAO;
import com.library.model.user.User;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {
        return userDAO.authenticate(username, password);
    }
}

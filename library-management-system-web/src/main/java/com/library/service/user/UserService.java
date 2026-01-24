package com.library.service.user;

import com.library.dao.user.UserDAO;
import com.library.model.user.User;
import com.library.util.PasswordUtil;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public void registerMember(User user) {

        // Hash password using same util as login
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        // Force role = MEMBER (security rule)
        user.setRole("MEMBER");

        userDAO.registerMember(user);
    }
}

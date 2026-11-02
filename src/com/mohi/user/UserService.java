package com.mohi.user;

import java.util.UUID;

public class UserService {

    private final UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public User[] getUsers() {
        return userDao.getUsers();
    }

    public User getUserById(UUID userId) {
        return userDao.getUserById(userId);
    }
}
package com.mohi.user;

import java.util.UUID;

public class UserDao {
    private static final User[] users;

    static {
        users = new User[]{
                new User(UUID.fromString("e90f6d92-54fa-4e58-a680-5967778d6b3b"), "Tony Stark"),
                new User(UUID.fromString("a1804d5e-0d36-4a5c-8f7d-9ea101bdd6ce"), "Peter Parker"),
                new User(UUID.fromString("246b2047-1ecc-40ca-9a99-e1f329adb333"), "Steve Rogers"),
                new User(UUID.fromString("75147bab-8a5e-4ce7-8adb-2b930e2afc09"), "Natasha Romanoff"),
        };
    }

    public User[] getUsers() {
        return users;
    }

    public User getUserById(UUID userId) {
        for (User user : users) {
            if (userId.equals(user.getId())) {
                return user;
            }
        }
        return null;
    }
}
package com.mohi.user;

import com.mohi.exception.CarBookingException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserFileDataAccessService implements UserDao {
    private final String filePath;

    public UserFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] values = line.split(",");

                UUID userId = UUID.fromString(values[0].trim());
                String name = values[1].trim();

                users.add(new User(userId, name));
            }
        } catch (IOException e) {
            throw new CarBookingException("error while reading file: " + e.getMessage());
        }

        return users;
    }

    @Override
    public User findUserById(UUID userId) {
        for (User user : getUsers()) {
            if (userId.equals(user.getId())) {
                return user;
            }
        }
        return null;
    }
}
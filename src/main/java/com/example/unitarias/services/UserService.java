package com.example.unitarias.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.unitarias.models.User;
import com.example.unitarias.repos.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public Optional<User> updateUser(User user, int id) {
        Optional<User> userToUpdate = userRepo.findById(id);
        return userToUpdate.map(u -> {
            user.setId(id);
            return userRepo.save(user);
        });
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(int id) {
        return userRepo.findById(id);
    }

    public boolean deleteUserById(int id) {
        try {
            userRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

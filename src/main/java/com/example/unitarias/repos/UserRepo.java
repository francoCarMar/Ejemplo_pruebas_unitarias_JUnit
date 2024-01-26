package com.example.unitarias.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.unitarias.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

}

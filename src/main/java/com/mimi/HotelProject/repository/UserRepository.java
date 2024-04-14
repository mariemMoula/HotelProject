package com.mimi.HotelProject.repository;

import com.mimi.HotelProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User>findUserByUserId(Long userId);

}

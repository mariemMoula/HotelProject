package com.mimi.HotelProject.service;

import com.mimi.HotelProject.dtos.UserDTO;
import com.mimi.HotelProject.entity.User;


import java.util.List;


public interface UserService {
    User findByUsername(String username);

    User findUserById(Long userId);

    List<User> findAll();

    User updateUser(UserDTO user);

    User saveUser(UserDTO userDTO);

    void deleteUser(Long userId);

    // only for admin
    User assignRoleToUser(String username, String roleName);

    public User revokeAdminRole(String username);


}


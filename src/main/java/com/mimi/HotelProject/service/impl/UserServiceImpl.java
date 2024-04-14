package com.mimi.HotelProject.service.impl;

import com.mimi.HotelProject.dtos.UserDTO;
import com.mimi.HotelProject.entity.User;
import com.mimi.HotelProject.mapper.UserMapper;
import com.mimi.HotelProject.repository.UserRepository;
import com.mimi.HotelProject.service.RoleService;
import com.mimi.HotelProject.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UserDTO userDTO) {
        User savedUser = findUserById(userDTO.getUserId());
        if (userDTO.getPassword() == null || savedUser.getPassword().equals(userDTO.getPassword()))
            userDTO.setPassword(savedUser.getPassword());
        else
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User theUser = userMapper.toUser(userDTO);
        theUser.setRole(roleService.findByRoleName(userDTO.getRole().getRoleName()));
        userRepository.save(theUser);
        return theUser;
    }

    @Override
    public User saveUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User theUser = userMapper.toUser(userDTO);
        theUser.setRole(roleService.findByRoleName(userDTO.getRole().getRoleName()));
        theUser = userRepository.save(theUser);
        return theUser;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // only to admin
    @Override
    public User assignRoleToUser(String username, String roleName) {
        User theUser = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        theUser.setRole(roleService.findByRoleName(roleName));
        return userRepository.save(theUser);

    }

    @Override
    public User revokeAdminRole(String username) {
        User theUser = findByUsername(username);
        theUser.setRole(roleService.findByRoleName("GUEST"));
        return userRepository.save(theUser);
    }
}


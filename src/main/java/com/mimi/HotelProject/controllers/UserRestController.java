package com.mimi.HotelProject.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimi.HotelProject.dtos.UserDTO;
import com.mimi.HotelProject.entity.User;
import com.mimi.HotelProject.helper.JWTHelper;
import com.mimi.HotelProject.mapper.UserMapper;
import com.mimi.HotelProject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.mimi.HotelProject.constant.JWTUtil.*;

@RestController
@RequestMapping("/users")
public class UserRestController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final JWTHelper jwtHelper;

    public UserRestController(UserService userService, UserMapper userMapper, JWTHelper jwtHelper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.jwtHelper = jwtHelper;
    }

    @GetMapping("/byUsername/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(userMapper.toUserDTO(user));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        return ResponseEntity.ok(userMapper.toUserDTO(user));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users.stream().map(userMapper::toUserDTO).toList());
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        userDTO.setUserId(userId); // Set the ID from the path variable
        User updatedUser = userService.updateUser(userDTO);
        return ResponseEntity.ok(userMapper.toUserDTO(updatedUser));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        User savedUser = userService.saveUser(userDTO);
        return ResponseEntity.created(null).body(userMapper.toUserDTO(savedUser));
    }

    @DeleteMapping("/byUserId/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/assignRole/{username}/role/{roleName}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> assignRoleToUser(@PathVariable String username, @PathVariable String roleName) {

        return ResponseEntity.ok(userMapper.toUserDTO(userService.assignRoleToUser(username, roleName)));
    }

    @DeleteMapping("/byUserName/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> revokeAdminRole(@PathVariable String username) {
        return ResponseEntity.ok(userMapper.toUserDTO(userService.revokeAdminRole(username)));
    }

    @GetMapping("/refresh-token")
    public void generateNewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jwtRefreshToken = jwtHelper.extractTokenFromHeaderIfExists(request.getHeader(AUTH_HEADER));
        if (jwtRefreshToken != null) {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwtRefreshToken);
            String username = decodedJWT.getSubject();
            User user = userService.findByUsername(username);
            String jwtAccessToken = jwtHelper.generateAccessToken(username, user.getRole().getRoleName());
            String newJwtRefreshToken = jwtHelper.generateRefreshToken(user.getUsername());
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), jwtHelper.getTokensMap(jwtAccessToken, newJwtRefreshToken));
        } else
            throw new RuntimeException("Refresh token required");
    }

}

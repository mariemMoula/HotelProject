package com.mimi.HotelProject.dtos;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private Long userId;
    private String username;
    private String password;
    private RoleDTO role;
}




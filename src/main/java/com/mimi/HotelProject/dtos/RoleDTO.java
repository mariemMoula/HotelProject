package com.mimi.HotelProject.dtos;


import lombok.*;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoleDTO {
    private Long roleId;
    private String roleName;

}


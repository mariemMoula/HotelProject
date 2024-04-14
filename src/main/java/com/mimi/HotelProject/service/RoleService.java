package com.mimi.HotelProject.service;

import com.mimi.HotelProject.dtos.RoleDTO;
import com.mimi.HotelProject.entity.Role;
import org.springframework.data.domain.Page;


import java.util.List;

public interface RoleService {
    Role findByRoleName(String roleName);
    Role findByRoleId(Long roleId);
    RoleDTO updateRole(RoleDTO role);
    void deleteRole(Long roleId);
    RoleDTO saveRole(String roleName);
    Page<RoleDTO> findAllRoles(int size, int page);
}

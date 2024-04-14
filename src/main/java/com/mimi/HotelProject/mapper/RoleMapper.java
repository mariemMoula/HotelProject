package com.mimi.HotelProject.mapper;

import com.mimi.HotelProject.dtos.RoleDTO;
import com.mimi.HotelProject.entity.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class RoleMapper {
    public Role toRole(RoleDTO roleDTO){
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        return role;
    }
    public RoleDTO toRoleDTO(Role role){
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(role, roleDTO);
        return roleDTO;
    }
}

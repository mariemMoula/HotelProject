package com.mimi.HotelProject.service.impl;

import com.mimi.HotelProject.dtos.RoleDTO;
import com.mimi.HotelProject.entity.Role;
import com.mimi.HotelProject.mapper.RoleMapper;
import com.mimi.HotelProject.repository.RoleRepository;
import com.mimi.HotelProject.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;


    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(()->new RuntimeException("NOT FOUND"));
    }

    @Override
    public Role findByRoleId(Long roleId) {
        return roleRepository.findByRoleId(roleId).orElseThrow( ()-> new RuntimeException(" ROLE NOT FOUND"));
    }

    @Override
    public RoleDTO updateRole(RoleDTO role) {
        Role roleEntity = roleRepository.findByRoleName(role.getRoleName()).orElseThrow(()->new RuntimeException("ROLE NAME NOT FOUND"));
        roleEntity.setRoleName(role.getRoleName());
        roleEntity=roleRepository.save(roleEntity);
        return role;
    }

    @Override
    public void deleteRole(Long roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    public RoleDTO saveRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        role =roleRepository.save(role);
        return  roleMapper.toRoleDTO(role);
    }

    @Override
    public Page<RoleDTO> findAllRoles(int size, int page) {
        return roleRepository.findAll(PageRequest.of(page, size)).map(roleMapper::toRoleDTO);
    }
}


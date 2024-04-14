package com.mimi.HotelProject.controllers;

import com.mimi.HotelProject.dtos.RoleDTO;
import com.mimi.HotelProject.mapper.RoleMapper;
import com.mimi.HotelProject.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleRestController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public RoleRestController(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @GetMapping("/{roleName}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RoleDTO> findByRoleName(@PathVariable String roleName) {
        RoleDTO roleDTO = roleMapper.toRoleDTO(roleService.findByRoleName(roleName));
        if (roleDTO != null) {
            return ResponseEntity.ok(roleDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/id/{roleId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RoleDTO> findByRoleId(@PathVariable Long roleId) {
        RoleDTO roleDTO = roleMapper.toRoleDTO(roleService.findByRoleId(roleId));
        if (roleDTO != null) {
            return ResponseEntity.ok(roleDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{roleId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Long roleId, @RequestBody RoleDTO roleDTO) {
        roleDTO.setRoleId(roleId);
        RoleDTO updatedRole = roleService.updateRole(roleDTO);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RoleDTO> saveRole(@RequestParam String roleName) {
        RoleDTO savedRole = roleService.saveRole(roleName);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<RoleDTO>> findAllRoles(@RequestParam int size, @RequestParam int page) {
        Page<RoleDTO> roles = roleService.findAllRoles(size, page);
        return ResponseEntity.ok(roles);
    }
}

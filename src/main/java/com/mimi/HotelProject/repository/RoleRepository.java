package com.mimi.HotelProject.repository;

import com.mimi.HotelProject.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String name);
    Optional<Role> findByRoleId(long id);
    Page<Role> findAll(Pageable pageable);
}


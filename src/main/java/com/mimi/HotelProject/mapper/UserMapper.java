package com.mimi.HotelProject.mapper;


import com.mimi.HotelProject.dtos.UserDTO;
import com.mimi.HotelProject.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    private final RoleMapper roleMapper;

    public UserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public User toUser(UserDTO userdto) {
        User user = new User();
        // to convert the userdto coming from the front end to user
        BeanUtils.copyProperties(userdto, user);
        return user;
    }
    public UserDTO toUserDTO(User user) {
        UserDTO userdto = new UserDTO();
        BeanUtils.copyProperties(user, userdto);
        userdto.setRole(roleMapper.toRoleDTO(user.getRole()));
        return userdto;
    }
    // I Don t need to show the guest because it does not necessarily have one
}

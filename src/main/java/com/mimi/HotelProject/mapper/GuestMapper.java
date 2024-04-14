package com.mimi.HotelProject.mapper;

import com.mimi.HotelProject.dtos.GuestDTO;
import com.mimi.HotelProject.entity.Guest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class GuestMapper {
    private UserMapper userMapper;
    public GuestMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    public GuestDTO toGuestDTO(Guest guest) {
        GuestDTO guestDTO = new GuestDTO();
        BeanUtils.copyProperties(guest, guestDTO);
        guestDTO.setUser(userMapper.toUserDTO(guest.getUser()));
        return guestDTO;
    }

    public Guest toGuest(GuestDTO guestDTO) {
        Guest guest = new Guest();
        BeanUtils.copyProperties(guestDTO, guest);
        return guest;
    }

}

package com.mimi.HotelProject.service.impl;

import com.mimi.HotelProject.dtos.GuestDTO;
import com.mimi.HotelProject.dtos.RoleDTO;
import com.mimi.HotelProject.dtos.UserDTO;
import com.mimi.HotelProject.entity.Guest;
import com.mimi.HotelProject.entity.User;
import com.mimi.HotelProject.mapper.GuestMapper;
import com.mimi.HotelProject.mapper.UserMapper;
import com.mimi.HotelProject.repository.GuestRepository;
import com.mimi.HotelProject.service.GuestService;
import com.mimi.HotelProject.service.RoleService;
import com.mimi.HotelProject.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;
    private final UserMapper userMapper;
    private final UserService userService;
    private final RoleService roleService;

    public GuestServiceImpl(GuestRepository guestRepository, GuestMapper guestMapper, UserMapper userMapper, UserService userService, RoleService roleService) {
        this.guestRepository = guestRepository;
        this.guestMapper = guestMapper;
        this.userMapper = userMapper;
        this.userService = userService;
        this.roleService = roleService;
    }


    @Override
    public Page<GuestDTO> getAllGuests(int size, int page) {
        return guestRepository.findAll(PageRequest.of(page, size)).map(guestMapper::toGuestDTO);
    }

    @Override
    public GuestDTO getGuestsByUsername(String username) {
        return guestMapper.toGuestDTO(guestRepository.findByUsername(username));

    }

    @Override
    public GuestDTO getGuestByPhoneNumber(String phoneNumber) {
        return guestMapper.toGuestDTO(guestRepository.findGuestByPhoneNumber(phoneNumber));

    }

    @Override
    public Page<GuestDTO> findByNameContaining(String guestName, int size, int page) {
        return guestRepository.findByNameContaining(guestName, PageRequest.of(page, size)).map(guestMapper::toGuestDTO);
    }


    @Override
    public Guest getGuestById(Long id) {
        return guestRepository.findByGuestId(id);
    }


    @Override
    public GuestDTO saveGuest(GuestDTO guest) {
        guest.getUser().setRole(RoleDTO.builder().roleName("GUEST").build());
        // to ignore the fields coming from the dto
        User theGuestUser = userService.saveUser(guest.getUser());
        Guest theGuest = guestMapper.toGuest(guest);
        theGuest.setUser(theGuestUser);
        theGuest = guestRepository.save(theGuest);
        return guestMapper.toGuestDTO(theGuest);
    }

    @Override
    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }

    @Override
    public GuestDTO updateGuest(GuestDTO guestdto) {
        Guest theGuest = guestMapper.toGuest(guestdto);
        Guest foundGuest = guestRepository.findByGuestId(guestdto.getGuestId());
        guestdto.getUser().setRole(RoleDTO.builder().roleName("GUEST").build());
        guestdto.getUser().setUserId(foundGuest.getUser().getUserId());
        User user = userService.updateUser(guestdto.getUser());
        theGuest.setUser(user);
        guestRepository.save(theGuest);
        return guestMapper.toGuestDTO(theGuest);
    }
}

package com.mimi.HotelProject.service;

import com.mimi.HotelProject.dtos.GuestDTO;
import com.mimi.HotelProject.entity.Guest;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Optional;

public interface GuestService {
    Page<GuestDTO> getAllGuests(int size, int page);

    GuestDTO getGuestsByUsername(String username);
    GuestDTO getGuestByPhoneNumber(String phoneNumber);
    Page<GuestDTO> findByNameContaining(String guestName, int size, int page);




    Guest getGuestById(Long id);


    GuestDTO saveGuest(GuestDTO guest);
    void deleteGuest(Long id);
    GuestDTO updateGuest(GuestDTO guest);
}


package com.mimi.HotelProject.controllers;

import com.mimi.HotelProject.dtos.GuestDTO;
import com.mimi.HotelProject.mapper.GuestMapper;
import com.mimi.HotelProject.service.GuestService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guests")
public class GuestRestController {
    private final GuestService guestService;
    private final GuestMapper guestMapper;

    public GuestRestController(GuestService guestService, GuestMapper guestMapper) {
        this.guestService = guestService;
        this.guestMapper = guestMapper;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<GuestDTO>> getAllGuests(@RequestParam(defaultValue = "10") int size,
                                                       @RequestParam(defaultValue = "0") int page) {
        Page<GuestDTO> guestsPage = guestService.getAllGuests(size, page);
        return ResponseEntity.ok(guestsPage);
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<GuestDTO> getGuestByUsername(@PathVariable String username) {
        GuestDTO guest = guestService.getGuestsByUsername(username);
        return ResponseEntity.ok(guest);
    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<GuestDTO> getGuestByPhoneNumber(@PathVariable String phoneNumber) {
        GuestDTO guest = guestService.getGuestByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(guest);
    }

    @GetMapping("/byGuestName/{guestName}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<GuestDTO>> findGuestsByNameContaining(@PathVariable String guestName,
                                                                     @RequestParam(defaultValue = "10") int size,
                                                                     @RequestParam(defaultValue = "0") int page) {
        Page<GuestDTO> guestsPage = guestService.findByNameContaining(guestName, size, page);
        return ResponseEntity.ok(guestsPage);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<GuestDTO> getGuestById(@PathVariable Long id) {
        GuestDTO guest = guestMapper.toGuestDTO(guestService.getGuestById(id));
        return ResponseEntity.ok(guest);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<GuestDTO> saveGuest(@RequestBody GuestDTO guest) {
        GuestDTO savedGuest = guestService.saveGuest(guest);
        return new ResponseEntity<>(savedGuest, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteGuest(@PathVariable Long id) {
        guestService.deleteGuest(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<GuestDTO> updateGuest(@PathVariable Long id, @RequestBody GuestDTO guest) {
        guest.setGuestId(id); // Ensure the ID is set
        GuestDTO updatedGuest = guestService.updateGuest(guest);
        return ResponseEntity.ok(updatedGuest);
    }

}

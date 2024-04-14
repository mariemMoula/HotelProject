package com.mimi.HotelProject.controllers;

import com.mimi.HotelProject.dtos.RoomDTO;
import com.mimi.HotelProject.mapper.RoomMapper;
import com.mimi.HotelProject.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomRestController {
    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','GUEST')")
    public ResponseEntity<Page<RoomDTO>> getAllRooms(@RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "0") int page) {
        Page<RoomDTO> rooms = roomService.getAllRooms(size, page);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','GUEST')")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {

        RoomDTO room = roomMapper.toRoomDTO(roomService.getRoomById(id));
        return ResponseEntity.ok(room);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO) {
        RoomDTO createdRoom = roomService.saveRoom(roomDTO);
        return ResponseEntity.created(null).body(createdRoom);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        roomDTO.setRoomId(id); // Ensure DTO has the correct ID
        RoomDTO updatedRoom = roomService.updateRoom(roomDTO);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }


    public RoomRestController(RoomService roomService, RoomMapper roomMapper) {
        this.roomService = roomService;
        this.roomMapper = roomMapper;
    }

    @GetMapping("/price-range")
    @PreAuthorize("hasAnyAuthority('ADMIN','GUEST')")
    public ResponseEntity<Page<RoomDTO>> getRoomByPriceRange(@RequestParam("min") Double priceMin,
                                                             @RequestParam("max") Double priceMax,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "0") int page) {
        Page<RoomDTO> rooms = roomService.getRoomByPriceRange(priceMin, priceMax, size, page);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyAuthority('ADMIN','GUEST')")
    public ResponseEntity<Page<RoomDTO>> getRoomByAvailable(@RequestParam("available") Boolean available,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(defaultValue = "0") int page) {
        Page<RoomDTO> rooms = roomService.getRoomByAvailable(available, size, page);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/capacity")
    @PreAuthorize("hasAnyAuthority('ADMIN','GUEST')")
    public ResponseEntity<Page<RoomDTO>> getRoomByCapacity(@RequestParam("capacity") Integer capacity,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "0") int page) {
        Page<RoomDTO> rooms = roomService.getRoomByCapacity(capacity, size, page);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/type")
    @PreAuthorize("hasAnyAuthority('ADMIN','GUEST')")
    public ResponseEntity<Page<RoomDTO>> getRoomByType(@RequestParam("type") String type,
                                                       @RequestParam(defaultValue = "10") int size,
                                                       @RequestParam(defaultValue = "0") int page) {
        Page<RoomDTO> rooms = roomService.getRoomByType(type, size, page);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/rating")
    @PreAuthorize("hasAnyAuthority('ADMIN','GUEST')")
    public ResponseEntity<Page<RoomDTO>> findRoomsByRating(@RequestParam("rating") int rating,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "0") int page) {
        Page<RoomDTO> rooms = roomService.findRoomsByRating(rating, size, page);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/filtered")
    @PreAuthorize("hasAnyAuthority('ADMIN','GUEST')")
    public ResponseEntity<Page<RoomDTO>> filterRooms(@RequestParam(value = "roomNumber", required = false) String roomNumber,
                                                     @RequestParam(value = "type", required = false) String type,
                                                     @RequestParam(value = "price", required = false) Double price,
                                                     @RequestParam(value = "available", required = false) Boolean available,
                                                     @RequestParam(value = "capacity", required = false) Integer capacity,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "0") int page) {

        Page<RoomDTO> rooms = roomService.filteredRooms(roomNumber, type, price, available, capacity, size, page);
        return ResponseEntity.ok(rooms);
    }
}

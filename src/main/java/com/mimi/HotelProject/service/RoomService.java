package com.mimi.HotelProject.service;

import com.mimi.HotelProject.dtos.RoomDTO;
import com.mimi.HotelProject.entity.Room;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    Page<RoomDTO> getRoomByPriceRange(Double priceMin, Double priceMax, int size, int page);

    Page<RoomDTO> getRoomByAvailable(Boolean available, int size, int page);

    Page<RoomDTO> getRoomByCapacity(Integer capacity, int size, int page);

    Page<RoomDTO> getRoomByType(String type, int size, int page);

    Room getRoomById(Long id);

    RoomDTO getRoomByRoomNumber(String roomNumber);

    RoomDTO updateRoom(RoomDTO roomDTO);

    Page<RoomDTO> getAllRooms(int size, int page);

    RoomDTO saveRoom(RoomDTO roomDTO);

    void deleteRoom(Long id);

    Page<RoomDTO> findRoomsByRating(int rating, int size, int page);


    Page<RoomDTO> filteredRooms(String roomNumber, String type, Double price, Boolean available, Integer capacity,int size, int page);

}

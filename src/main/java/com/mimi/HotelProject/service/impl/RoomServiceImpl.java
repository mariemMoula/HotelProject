package com.mimi.HotelProject.service.impl;

import com.mimi.HotelProject.dtos.RoomDTO;
import com.mimi.HotelProject.entity.Room;
import com.mimi.HotelProject.mapper.RoomMapper;
import com.mimi.HotelProject.repository.RoomRepository;
import com.mimi.HotelProject.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomServiceImpl(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    @Override
    public Page<RoomDTO> getRoomByPriceRange(Double priceMin, Double priceMax, int size, int page) {
        return roomRepository.findAllByPriceBetween(priceMin, priceMax, PageRequest.of(page, size)).map(roomMapper::toRoomDTO);
    }

    @Override
    public Page<RoomDTO> getRoomByAvailable(Boolean available, int size, int page) {
        return roomRepository.findAllByAvailable(available, PageRequest.of(page, size)).map(roomMapper::toRoomDTO);
    }

    @Override
    public Page<RoomDTO> getRoomByCapacity(Integer capacity, int size, int page) {
        return roomRepository.findAllByCapacity(capacity, PageRequest.of(page, size)).map(roomMapper::toRoomDTO);
    }

    @Override
    public Page<RoomDTO> getRoomByType(String type, int size, int page) {
        return roomRepository.findAllByType(type, PageRequest.of(page, size)).map(roomMapper::toRoomDTO);
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public RoomDTO getRoomByRoomNumber(String roomNumber) {
        return roomMapper.toRoomDTO(roomRepository.findByRoomNumber(roomNumber));
    }

    @Override
    public RoomDTO updateRoom(RoomDTO roomDTO) {
        Room theRoom = roomMapper.toRoom(roomDTO);
        theRoom.setReservations(getRoomById(roomDTO.getRoomId()).getReservations());
        theRoom=roomRepository.save(theRoom);
        return roomMapper.toRoomDTO(theRoom);
    }

    @Override
    public Page<RoomDTO> getAllRooms(int size, int page) {
        return roomRepository.findAll(PageRequest.of(page, size)).map(roomMapper::toRoomDTO);
    }

    @Override
    public RoomDTO saveRoom(RoomDTO roomDTO) {
        Room roomSaved = roomMapper.toRoom(roomDTO);
        roomSaved = roomRepository.save(roomSaved);
        return roomMapper.toRoomDTO(roomSaved);
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Page<RoomDTO> findRoomsByRating(int rating, int size, int page) {
        return roomRepository.findRoomsByRating(rating, PageRequest.of(page, size)).map(roomMapper::toRoomDTO);
    }

    @Override
    public Page<RoomDTO> filteredRooms(String roomNumber, String type, Double price, Boolean available, Integer capacity, int size, int page) {
        return roomRepository.filterRooms(roomNumber, type, price, available, capacity, PageRequest.of(page, size)).map(roomMapper::toRoomDTO);
    }
}

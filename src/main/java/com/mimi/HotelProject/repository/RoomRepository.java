package com.mimi.HotelProject.repository;

import com.mimi.HotelProject.dtos.RoomDTO;
import com.mimi.HotelProject.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByRoomId(Long id);
    Room findByRoomNumber(String roomName);
    Page<Room> findAllByPriceBetween(Double priceMin, Double priceMax, Pageable pageable);

    Page<Room> findAllByAvailable(Boolean available, Pageable pageable);
    Page<Room> findAllByCapacity(Integer capacity, Pageable pageable);
    Page<Room> findAllByType(String type, Pageable pageable);

    @Query("SELECT DISTINCT r FROM Room r " +
            "JOIN r.reservations res " +
            "JOIN res.review rev " +
            "WHERE rev.rating >= :rating")
    Page<Room> findRoomsByRating(@Param("rating") int rating, Pageable pageable);

    @Query("SELECT r FROM Room r " +
            "WHERE (:roomNumber is null or r.roomNumber = :roomNumber) " +
            "AND (:type is null or r.type = :type) " +
            "AND (:price is null or r.price = :price) " +
            "AND (:available is null or r.available = :available) " +
            "AND (:capacity is null or r.capacity = :capacity)")
    Page<Room> filterRooms(@Param("roomNumber") String roomNumber,
                           @Param("type") String type,
                           @Param("price") Double price,
                           @Param("available") Boolean available,
                           @Param("capacity") Integer capacity,
                           Pageable pageable);
}


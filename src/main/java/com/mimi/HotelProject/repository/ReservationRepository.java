package com.mimi.HotelProject.repository;


import com.mimi.HotelProject.entity.Reservation;
import com.mimi.HotelProject.entity.Room;
import com.mimi.HotelProject.entity.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    List<Reservation> findByGuest(Guest guest);
    List<Reservation> findByRoom(Room room);

    Page<Reservation> findByPaymentMethod(String paymentMethod, Pageable pageable);
    Reservation findByReservationId(Long id);
    List<Reservation> findByGuestAndRoom(Guest guest, Room room);


    @Query("SELECT r FROM Reservation r WHERE r.room.roomId = :roomId")
    Page<Reservation> findRoomByRoomId(Long roomId, Pageable pageable);

    @Query("SELECT r FROM Reservation r WHERE r.guest.guestId = :guestId")
    Page<Reservation> findByGuestId(Long guestId, Pageable pageable);

    @Query("SELECT r FROM Reservation r WHERE r.guest.guestId = :guestId AND r.room.roomId = :roomId")
    Page<Reservation> findByGuestIdAndRoomId(Long guestId, Long roomId, Pageable pageable);

    @Query("SELECT r FROM Reservation r WHERE r.checkInDate = :checkInDate")
    Page<Reservation> findByCheckInDate(Date checkInDate, Pageable pageable);

    @Query("SELECT r FROM Reservation r WHERE r.checkOutDate = :checkOutDate")
    Page<Reservation> findByCheckOutDate(Date checkOutDate, Pageable pageable);

    @Query("SELECT r FROM Reservation r WHERE r.room.roomId = :roomId AND r.checkInDate <= :checkOutDate AND r.checkOutDate >= :checkInDate")
    Page<Reservation> findByRoomIdAndDates(Long roomId, Date checkInDate, Date checkOutDate, Pageable pageable);

    @Query("SELECT r FROM Reservation r " +
            "WHERE (:excludedReservationId IS NULL OR r.reservationId != :excludedReservationId) " +
            "AND r.room.roomId = :roomId " +
            "AND r.checkInDate < :checkOutDate " +
            "AND r.checkOutDate > :checkInDate")
    Page<Reservation> findAvailableReservationsByRoomIdAndDates(Long excludedReservationId, Long roomId, Date checkInDate, Date checkOutDate, Pageable pageable);

    @Query("SELECT r FROM Reservation r JOIN r.review rv WHERE rv.rating > 7")
    Page<Reservation> findReservationsWithGoodRating(Pageable pageable);

    @Query("SELECT r FROM Reservation r JOIN r.review rv WHERE rv.rating <= 4")
    Page<Reservation> findReservationsWithBadRating(Pageable pageable);


}


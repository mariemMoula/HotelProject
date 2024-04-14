package com.mimi.HotelProject.service;

import com.mimi.HotelProject.dtos.ReservationDTO;
import com.mimi.HotelProject.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

    @Scheduled(cron = "0 0 0 * * ?")
    void updateRoomsAvailability();

    Page<ReservationDTO> getAllReservations(int size, int page);

    Reservation getReservationById(Long id);

    void deleteReservation(Long id);

    ReservationDTO updateReservation(ReservationDTO updatedReservation);

    ReservationDTO saveReservation(ReservationDTO reservation);

    Page<ReservationDTO> getReservationsByGuestAndRoom(Long guestId, Long roomId, int page, int size);

    Page<ReservationDTO> getReservationsByCheckInDate(Date checkInDate, int size, int page);

    Page<ReservationDTO> getReservationsByCheckOutDate(Date checkOutDate, int size, int page);

    Page<ReservationDTO> getReservationsByRoomIdAndDates(Long roomId, Date checkInDate, Date checkOutDate, int size, int page);

    Page<ReservationDTO> getAvailableReservationsByRoomIdAndDates(Long roomId, Date checkInDate, Date checkOutDate, int size, int page);

    Page<ReservationDTO> findByRoomId(Long roomId, int size, int page);

    Page<ReservationDTO> findByGuestId(Long guestId, int size, int page);

    Page<ReservationDTO> findByPaymentMethod(String paymentMethod, int size, int page);

    Page<ReservationDTO> getReservationsWithBadRating(int size, int page);

    Page<ReservationDTO> getReservationsWithGoodRating(int size, int page);


}


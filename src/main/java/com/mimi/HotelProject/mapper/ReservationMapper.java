package com.mimi.HotelProject.mapper;

import com.mimi.HotelProject.dtos.ReservationDTO;
import com.mimi.HotelProject.entity.Reservation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ReservationMapper {
    private final RoomMapper roomMapper;

    private final GuestMapper guestMapper;

    private final ReviewMapper reviewMapper;

    public ReservationMapper(RoomMapper roomMapper, GuestMapper guestMapper, ReviewMapper reviewMapper) {
        this.roomMapper = roomMapper;
        this.guestMapper = guestMapper;
        this.reviewMapper = reviewMapper;
    }

    public Reservation toReservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(reservationDTO, reservation);
        return reservation;
    }

    public ReservationDTO toReservationDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        BeanUtils.copyProperties(reservation, reservationDTO);
        reservationDTO.setGuest(guestMapper.toGuestDTO(reservation.getGuest()));
        reservationDTO.setRoom(roomMapper.toRoomDTO(reservation.getRoom()));
        if (reservation.getReview() != null) {
            reservationDTO.setReview(reviewMapper.toReviewDTO(reservation.getReview()));
        } else {
            reservationDTO.setReview(null);
        }
        return reservationDTO;
    }
}

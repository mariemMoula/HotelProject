package com.mimi.HotelProject.service.impl;

import com.mimi.HotelProject.dtos.ReservationDTO;
import com.mimi.HotelProject.entity.Reservation;
import com.mimi.HotelProject.mapper.ReservationMapper;
import com.mimi.HotelProject.repository.ReservationRepository;
import com.mimi.HotelProject.repository.RoomRepository;
import com.mimi.HotelProject.service.GuestService;
import com.mimi.HotelProject.service.ReservationService;
import com.mimi.HotelProject.service.ReviewService;
import com.mimi.HotelProject.service.RoomService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;


@Transactional
@Service
@EnableScheduling
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final RoomService roomService;
    private final ReviewService reviewService;
    private final GuestService guestService;
    private final RoomRepository roomRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper, RoomService roomService, ReviewService reviewService, GuestServiceImpl guestService, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.roomService = roomService;
        this.reviewService = reviewService;
        this.guestService = guestService;
        this.roomRepository = roomRepository;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateRoomsAvailability() {
        roomRepository.findAll().forEach(room -> {
            if(getAvailableReservationsByRoomIdAndDates(room.getRoomId(), new Date(), DateUtils.addDays(new Date(),1),5,0).isEmpty())
                room.setAvailable(true);
            else
                room.setAvailable(false);
        });
    }

    @Override
    public Page<ReservationDTO> getAllReservations(int size, int page) {
        //this method returns a list of Reservation but we expect a Page of ReservationDTO
        // the map method already iterates the pageable and applies a lambda function , and that lambda function is used from ReservationMapper
        return reservationRepository.findAll(PageRequest.of(page, size)).map(res -> {
            return reservationMapper.toReservationDTO(res);
        });
    }

    @Override
    public Reservation getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NO EXISTANT RESERVATION BY THIS ID"));

        return reservation;
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);


    }

    @Override
    public ReservationDTO updateReservation(ReservationDTO newReservation) {
        Reservation updatedReservation = reservationMapper.toReservation(newReservation);
        // we have to add manually the fields that weren't added in the mapper:the dtos , but we have to verify
        // verifying if the room is still available:
        Long updatedRoomId = newReservation.getRoom().getRoomId();
        if (reservationRepository.findAvailableReservationsByRoomIdAndDates(newReservation.getReservationId(), updatedRoomId, updatedReservation.getCheckInDate(), updatedReservation.getCheckOutDate(), PageRequest.of(0, 100)).isEmpty()) {
            updatedReservation.setRoom(roomService.getRoomById(updatedRoomId));
        } else {
            throw new RuntimeException("ROOM ALREADY BOOKED");
        }
        // we add the rest of fields
        updatedReservation.setGuest(guestService.getGuestById(newReservation.getGuest().getGuestId()));
        // we add the reviews if not null
        if (newReservation.getReview() != null) {
            updatedReservation.setReview(reviewService.getReviewById(newReservation.getReview().getReviewId()));
        }
        updatedReservation = reservationRepository.save(updatedReservation);
        return reservationMapper.toReservationDTO(updatedReservation);
    }

    @Override
    public ReservationDTO saveReservation(ReservationDTO reservationDto) {
        // converting the dto to an entity
        Reservation theReservation = reservationMapper.toReservation(reservationDto);
        // we have to verify if the reservation is even possible before saving it
        // verifying if the room is still available:
        Long theRoomId = reservationDto.getRoom().getRoomId();
        if (reservationRepository.findAvailableReservationsByRoomIdAndDates(null, theRoomId, theReservation.getCheckInDate(), theReservation.getCheckOutDate(), PageRequest.of(0, 100)).isEmpty()) {
            theReservation.setRoom(roomService.getRoomById(theRoomId));
        } else {
            throw new RuntimeException("ROOM ALREADY BOOKED");
        }
        // we add the rest of fields
        theReservation.setGuest(guestService.getGuestById(reservationDto.getGuest().getGuestId()));
        // we add the reviews if not null
        if (reservationDto.getReview() != null) {
            theReservation.setReview(reviewService.getReviewById(reservationDto.getReview().getReviewId()));
        }
        theReservation = reservationRepository.save(theReservation);
        return reservationMapper.toReservationDTO(theReservation);

    }

    @Override
    public Page<ReservationDTO> getReservationsByGuestAndRoom(Long guestId, Long roomId, int page, int size) {
        return reservationRepository.findByGuestIdAndRoomId(guestId, roomId, PageRequest.of(page, size)).map(reservationMapper::toReservationDTO);
    }

    @Override
    public Page<ReservationDTO> getReservationsByCheckInDate(Date checkInDate, int size, int page) {
        return reservationRepository.findByCheckInDate(checkInDate, PageRequest.of(page, size)).map(reservationMapper::toReservationDTO);
    }

    @Override
    public Page<ReservationDTO> getReservationsByCheckOutDate(Date checkOutDate, int size, int page) {
        return reservationRepository.findByCheckInDate(checkOutDate, PageRequest.of(page, size)).map(reservationMapper::toReservationDTO);
    }

    @Override
    public Page<ReservationDTO> getReservationsByRoomIdAndDates(Long roomId, Date checkInDate, Date checkOutDate, int size, int page) {
        return reservationRepository.findByRoomIdAndDates(roomId, checkInDate, checkOutDate, PageRequest.of(page, size)).map(reservationMapper::toReservationDTO);
    }

    @Override
    public Page<ReservationDTO> getAvailableReservationsByRoomIdAndDates(Long roomId, Date checkInDate, Date checkOutDate, int size, int page) {
        return reservationRepository.findAvailableReservationsByRoomIdAndDates(null, roomId, checkInDate, checkOutDate, PageRequest.of(page, size)).map(reservationMapper::toReservationDTO);
    }

    @Override
    public Page<ReservationDTO> findByRoomId(Long roomId, int size, int page) {
        return reservationRepository.findRoomByRoomId(roomId, PageRequest.of(page, size)).map(reservationMapper::toReservationDTO);
    }

    @Override
    public Page<ReservationDTO> findByGuestId(Long guestId, int size, int page) {
        return reservationRepository.findByGuestId(guestId, PageRequest.of(page, size)).map(reservationMapper::toReservationDTO);
    }

    @Override
    public Page<ReservationDTO> findByPaymentMethod(String paymentMethod, int size, int page) {
        return reservationRepository.findByPaymentMethod(paymentMethod, PageRequest.of(page, size)).map(reservationMapper::toReservationDTO);
    }

    @Override
    public Page<ReservationDTO> getReservationsWithBadRating(int size, int page) {
        return reservationRepository.findReservationsWithBadRating(PageRequest.of(page, size)).map(reservationMapper::toReservationDTO);
    }

    @Override
    public Page<ReservationDTO> getReservationsWithGoodRating(int size, int page) {
        return reservationRepository.findReservationsWithBadRating(PageRequest.of(page, size)).map(reservationMapper::toReservationDTO);
    }
}

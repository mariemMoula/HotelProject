package com.mimi.HotelProject.controllers;

import com.mimi.HotelProject.dtos.ReservationDTO;
import com.mimi.HotelProject.mapper.ReservationMapper;
import com.mimi.HotelProject.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
@RestController
@RequestMapping("/reservations")
public class ReservationRestController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;
    public ReservationRestController(ReservationService reservationService, ReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;

    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<ReservationDTO>> getAllReservations(@RequestParam(defaultValue = "10") int size,
                                                                   @RequestParam(defaultValue = "0") int page) {
        Page<ReservationDTO> reservations = reservationService.getAllReservations(size, page);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @securityHelper.hasAuthorityToReservation(#id)")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        ReservationDTO reservation = reservationMapper.toReservationDTO(reservationService.getReservationById(id));
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @securityHelper.hasAuthorityToReservation(#id)")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @securityHelper.hasAuthorityToReservation(#id)")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable Long id, @RequestBody ReservationDTO updatedReservation) {
        updatedReservation.setReservationId(id);
        ReservationDTO reservation = reservationService.updateReservation(updatedReservation);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN') or @securityHelper.hasAuthorityToMakeReservation(#reservation.guest.guestId)")
    public ResponseEntity<ReservationDTO> saveReservation(@RequestBody ReservationDTO reservation) {
        ReservationDTO savedReservation = reservationService.saveReservation(reservation);
        return ResponseEntity.created(null).body(savedReservation);
    }

    @GetMapping("/guest/{guestId}/room/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<ReservationDTO>> getReservationsByGuestAndRoom(@PathVariable Long guestId,
                                                                              @PathVariable Long roomId,
                                                                              @RequestParam(defaultValue = "10") int size,
                                                                              @RequestParam(defaultValue = "0") int page) {
        Page<ReservationDTO> reservations = reservationService.getReservationsByGuestAndRoom(guestId, roomId, size, page);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/check-in-date")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<ReservationDTO>> getReservationsByCheckInDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkInDate,
                                                                             @RequestParam(defaultValue = "10") int size,
                                                                             @RequestParam(defaultValue = "0") int page) {
        Page<ReservationDTO> reservations = reservationService.getReservationsByCheckInDate(checkInDate, size, page);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/check-out-date")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<ReservationDTO>> getReservationsByCheckOutDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkOutDate,
                                                                              @RequestParam(defaultValue = "10") int size,
                                                                              @RequestParam(defaultValue = "0") int page) {
        Page<ReservationDTO> reservations = reservationService.getReservationsByCheckOutDate(checkOutDate, size, page);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/room-dates")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<ReservationDTO>> getReservationsByRoomIdAndDates(@RequestParam Long roomId,
                                                                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkInDate,
                                                                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkOutDate,
                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                @RequestParam(defaultValue = "0") int page) {
        Page<ReservationDTO> reservations = reservationService.getReservationsByRoomIdAndDates(roomId, checkInDate, checkOutDate, size, page);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/available-room-dates")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<ReservationDTO>> getAvailableReservationsByRoomIdAndDates(@RequestParam Long roomId,
                                                                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkInDate,
                                                                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkOutDate,
                                                                                         @RequestParam(defaultValue = "10") int size,
                                                                                         @RequestParam(defaultValue = "0") int page) {
        Page<ReservationDTO> reservations = reservationService.getAvailableReservationsByRoomIdAndDates(roomId, checkInDate, checkOutDate, size, page);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/room/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<ReservationDTO>> findByRoomId(@PathVariable Long roomId,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "0") int page) {
        Page<ReservationDTO> reservations = reservationService.findByRoomId(roomId, size, page);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/guest/{guestId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<ReservationDTO>> findByGuestId(@PathVariable Long guestId,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(defaultValue = "0") int page) {
        Page<ReservationDTO> reservations = reservationService.findByGuestId(guestId, size, page);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/payment-method/{paymentMethod}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<ReservationDTO>> findByPaymentMethod(@PathVariable String paymentMethod,
                                                                    @RequestParam(defaultValue = "10") int size,
                                                                    @RequestParam(defaultValue = "0") int page) {
        Page<ReservationDTO> reservations = reservationService.findByPaymentMethod(paymentMethod, size, page);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/ratings/bad")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<ReservationDTO>> getReservationsWithBadRating(@RequestParam(defaultValue = "10") int size,
                                                                             @RequestParam(defaultValue = "0") int page) {
        Page<ReservationDTO> reservations = reservationService.getReservationsWithBadRating(size, page);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/ratings/good")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<ReservationDTO>> getReservationsWithGoodRating(@RequestParam(defaultValue = "10") int size,
                                                                              @RequestParam(defaultValue = "0") int page) {
        Page<ReservationDTO> reservations = reservationService.getReservationsWithGoodRating(size, page);
        return ResponseEntity.ok(reservations);
    }
}

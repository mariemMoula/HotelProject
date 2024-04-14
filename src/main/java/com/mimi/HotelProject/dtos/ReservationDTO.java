package com.mimi.HotelProject.dtos;


import com.mimi.HotelProject.entity.Guest;
import com.mimi.HotelProject.entity.Review;
import com.mimi.HotelProject.entity.Room;
import lombok.*;

import java.util.Date;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReservationDTO {
    private Long reservationId;
    private Date checkInDate;
    private Date checkOutDate;
    private String paymentMethod;
    private RoomDTO room;
    private GuestDTO guest;
    private ReviewDTO review;

}

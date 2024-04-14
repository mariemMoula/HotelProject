package com.mimi.HotelProject.dtos;


import com.mimi.HotelProject.entity.Reservation;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GuestDTO {
    private Long guestId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserDTO user;



}


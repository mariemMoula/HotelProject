package com.mimi.HotelProject.dtos;

import lombok.*;

import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoomDTO {
    private Long roomId;

    private String roomNumber;
    private String type;
    private double price;
    private boolean available;
    private int capacity;
    private String description;
    private List<String> photos;
    private List<ReservationDTO> reservations;


}

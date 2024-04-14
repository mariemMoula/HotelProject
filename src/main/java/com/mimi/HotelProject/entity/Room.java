package com.mimi.HotelProject.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    private String roomNumber;
    private String type;
    private double price;
    private boolean available;
    private int capacity;
    private String description;
    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> photos = new ArrayList<>();
    // this is the slave entity , it s referenced in the entity reservation with the name room
    @Builder.Default // will set this state as a default value when using lombok
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<Reservation> reservations = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return Double.compare(price, room.price) == 0 && available == room.available && capacity == room.capacity && Objects.equals(roomId, room.roomId) && Objects.equals(roomNumber, room.roomNumber) && Objects.equals(type, room.type) && Objects.equals(description, room.description) && Objects.equals(photos, room.photos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, roomNumber, type, price, available, capacity, description, photos);
    }
}
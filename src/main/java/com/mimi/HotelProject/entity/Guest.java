package com.mimi.HotelProject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "guest")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id")
    private Long guestId;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    @ToString.Exclude//to avoid infinite loops
    // this is the master entity , it has the id of the user
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false,name = "user_id",referencedColumnName = "user_id")
    private User user;

    @ToString.Exclude//to avoid infinite loops
    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reservation> reservations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guest guest)) return false;
        return Objects.equals(guestId, guest.guestId) && Objects.equals(firstName, guest.firstName) && Objects.equals(lastName, guest.lastName) && Objects.equals(phoneNumber, guest.phoneNumber) && Objects.equals(user, guest.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guestId, firstName, lastName, phoneNumber, user);
    }
}

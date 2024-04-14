package com.mimi.HotelProject.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @Temporal(TemporalType.DATE)
    @Column(name = "check_in_date")
    private Date checkInDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "check_out_date")
    private Date checkOutDate;
    // the master entity , it has the key of the table room with the name room_id
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false)
    private Room room;

    @ToString.Exclude //to avoid infinite loops
    @ManyToOne // master entitty because it has the id of the Guest , in the table the column will have the value of the guest primary key
    @JoinColumn(name = "guest_id", referencedColumnName = "guest_id", nullable = false)
    private Guest guest;

    @Column(name = "payment_method")
    private String paymentMethod;
    // this is the slave entity since it has the key of the other entity
    @ToString.Exclude  //to avoid infinite loops
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Review review;

    public void setReview(Review review) {
        this.review = review;
        review.setReservation(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return Objects.equals(reservationId, that.reservationId) && Objects.equals(checkInDate, that.checkInDate) && Objects.equals(checkOutDate, that.checkOutDate) && Objects.equals(room, that.room) && Objects.equals(paymentMethod, that.paymentMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, checkInDate, checkOutDate, room, paymentMethod);
    }
}

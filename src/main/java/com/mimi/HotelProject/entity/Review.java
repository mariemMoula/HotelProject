package com.mimi.HotelProject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    // this is the master entity
    @OneToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id")//the name of the column in the table of reservation , and it represents the key of review with the name review_id in the review table
    private Reservation reservation;


    @Column(columnDefinition = "TEXT")
    private String comment;

    private int rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review review)) return false;
        return rating == review.rating && Objects.equals(reviewId, review.reviewId) && Objects.equals(comment, review.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, comment, rating);
    }
}

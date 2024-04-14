package com.mimi.HotelProject.repository;

import com.mimi.HotelProject.dtos.ReviewDTO;
import com.mimi.HotelProject.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByReviewId(Long reviewId);

    @Query("SELECT r FROM Review r WHERE r.reservation.room.roomId = :roomId")
    Page<Review> getReviewsByRoomId(Long roomId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.reservation.guest.guestId = :guestId")
    Page<Review> getReviewByGuestId(Long guestId, Pageable pageable);
    @Query("SELECT r FROM Review r WHERE r.reservation.guest.guestId = :guestId")
    List<Review> getListReviewByGuestId(Long guestId);
}


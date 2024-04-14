package com.mimi.HotelProject.service;

import com.mimi.HotelProject.dtos.ReviewDTO;
import com.mimi.HotelProject.entity.Review;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ReviewService {
    Review getReviewById(Long id);
    Page<ReviewDTO> getAllReviews(  int page, int size);
    ReviewDTO saveReview(ReviewDTO review);
    void deleteReview(Long id);
    ReviewDTO updateReview(ReviewDTO review);
    Page<ReviewDTO> getReviewsByRoomId(Long roomId,  int page, int size);
    Page<ReviewDTO> getListReviewByGuestId(Long guestId, int page, int size);
    List<Review> getListReviewByGuestId(Long guestId);


}


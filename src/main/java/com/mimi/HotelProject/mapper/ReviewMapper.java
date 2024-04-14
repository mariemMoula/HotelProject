package com.mimi.HotelProject.mapper;

import com.mimi.HotelProject.dtos.ReviewDTO;
import com.mimi.HotelProject.entity.Review;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class ReviewMapper {
    public ReviewDTO toReviewDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        BeanUtils.copyProperties(review, reviewDTO);
        return reviewDTO;
    }

    public Review toReview(ReviewDTO reviewDTO) {
        Review review = new Review();
        BeanUtils.copyProperties(reviewDTO, review);
        return review;
    }
}

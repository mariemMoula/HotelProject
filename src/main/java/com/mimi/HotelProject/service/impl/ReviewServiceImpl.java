package com.mimi.HotelProject.service.impl;

import com.mimi.HotelProject.dtos.ReviewDTO;
import com.mimi.HotelProject.entity.Review;
import com.mimi.HotelProject.mapper.ReviewMapper;
import com.mimi.HotelProject.repository.ReviewRepository;
import com.mimi.HotelProject.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public Review getReviewById(Long id) {
        return reviewRepository.findByReviewId(id);
    }

    @Override
    public Page<ReviewDTO> getAllReviews(int page, int size) {
        return reviewRepository.findAll(PageRequest.of(page, size)).map(reviewMapper::toReviewDTO);
    }

    @Override
    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        return reviewMapper.toReviewDTO(reviewRepository.save(reviewMapper.toReview(reviewDTO)));
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public ReviewDTO updateReview(ReviewDTO review) {
        return reviewMapper.toReviewDTO(reviewRepository.save(reviewMapper.toReview(review)));

    }

    @Override
    public Page<ReviewDTO> getReviewsByRoomId(Long roomId, int page, int size) {
        return reviewRepository.getReviewsByRoomId(roomId ,PageRequest.of(page, size)).map(reviewMapper::toReviewDTO);
    }

    @Override
    public Page<ReviewDTO> getListReviewByGuestId(Long guestId, int page, int size) {
        return reviewRepository.getReviewByGuestId(guestId ,PageRequest.of(page, size)).map(reviewMapper::toReviewDTO);
    }

    @Override
    public List<Review> getListReviewByGuestId(Long guestId) {
        return reviewRepository.getListReviewByGuestId(guestId);
    }


}


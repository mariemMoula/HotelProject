package com.mimi.HotelProject.controllers;

import com.mimi.HotelProject.dtos.ReviewDTO;
import com.mimi.HotelProject.mapper.ReviewMapper;
import com.mimi.HotelProject.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewRestController {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;
    public ReviewRestController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        ReviewDTO reviewDTO = reviewMapper.toReviewDTO(reviewService.getReviewById(id));
        if (reviewDTO != null) {
            return ResponseEntity.ok(reviewDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<ReviewDTO>> getAllReviews(@RequestParam int page, @RequestParam int size) {
        Page<ReviewDTO> reviews = reviewService.getAllReviews(page, size);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','GUEST')")
    public ResponseEntity<ReviewDTO> saveReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO savedReview = reviewService.saveReview(reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @securityHelper.hasAuthorityToReview(#id)")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @securityHelper.hasAuthorityToReview(#id)")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        reviewDTO.setReviewId(id);
        ReviewDTO updatedReview = reviewService.updateReview(reviewDTO);
        return ResponseEntity.ok(updatedReview);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<Page<ReviewDTO>> getReviewsByRoomId(@PathVariable Long roomId, @RequestParam int page, @RequestParam int size) {
        Page<ReviewDTO> reviews = reviewService.getReviewsByRoomId(roomId, page, size);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/guest/{guestId}")
    @PreAuthorize("hasAuthority('ADMIN') or @securityHelper.hasAuthorityToReview(#id)")
    public ResponseEntity<Page<ReviewDTO>> getReviewByGuestId(@PathVariable Long guestId, @RequestParam int page, @RequestParam int size) {
        Page<ReviewDTO> reviews = reviewService.getListReviewByGuestId(guestId, page, size);
        return ResponseEntity.ok(reviews);
    }
}

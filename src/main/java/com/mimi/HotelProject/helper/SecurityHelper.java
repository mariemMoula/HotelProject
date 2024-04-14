package com.mimi.HotelProject.helper;

import com.mimi.HotelProject.dtos.GuestDTO;
import com.mimi.HotelProject.entity.Guest;
import com.mimi.HotelProject.entity.Review;
import com.mimi.HotelProject.service.GuestService;
import com.mimi.HotelProject.service.ReviewService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


// this class is to verify if, the guest who trying to manipulate a reservation ,that reservation belong to them
@Service
public class SecurityHelper {
    private GuestService guestService;
    private ReviewService reviewService;

    public SecurityHelper(GuestService guestService, ReviewService reviewService) {
        this.guestService = guestService;
        this.reviewService = reviewService;
    }

    public boolean hasAuthorityToReservation(Long guestId) {
        // To get who s currently authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // finding the guest by the id
        Guest guest = guestService.getGuestById(guestId);
        // now we verify if the authenticated guest is trying to manipulate his own reservations or not ,  and verifying if there s even someone is even authenticated
        return authentication != null && authentication.getName().equalsIgnoreCase(guest.getUser().getUsername());
    }

    public boolean hasAuthorityToReview(Long reviewId) {
        // To get who s currently authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null)
            return false;

        // finding the guest by the id
        List<Review> reviews = reviewService.getListReviewByGuestId(guestService.getGuestsByUsername(authentication.getName()).getGuestId());

        // now we verify if the authenticated guest is trying to manipulate his own reviews or not ,  and verifying if there s even someone is even authenticated
        return reviews.contains(reviewService.getReviewById(reviewId));
    }

    public boolean hasAuthorityToMakeReservation(Long reservationGuestId) {
        // To get who s currently authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            return false;
        // finding the guest by the id
        GuestDTO guest = guestService.getGuestsByUsername(authentication.getName());
        // now we verify if the authenticated guest is trying to manipulate his own reservations or not ,  and verifying if there s even someone is even authenticated
        return guest.getGuestId().equals(reservationGuestId);

    }
}

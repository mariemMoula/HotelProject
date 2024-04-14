package com.mimi.HotelProject.dtos;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReviewDTO {
    private Long reviewId;
    private String comment;
    private int rating;
}


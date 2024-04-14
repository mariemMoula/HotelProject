package com.mimi.HotelProject.repository;

import com.mimi.HotelProject.entity.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    @Query("select g from Guest as g " +
            "where g.user.username=:username ")
    Guest findByUsername(@Param("username") String username);

    Guest findByGuestId(long id);

    @Query("SELECT g FROM Guest g WHERE g.firstName LIKE %:name% OR g.lastName LIKE %:name%")
    Page<Guest> findByNameContaining(@Param("name") String guestName, Pageable pageable);

    Guest findGuestByPhoneNumber(String phoneNumber);
}


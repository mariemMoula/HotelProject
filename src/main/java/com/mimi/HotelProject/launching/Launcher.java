package com.mimi.HotelProject.launching;

import com.mimi.HotelProject.dtos.*;
import com.mimi.HotelProject.entity.*;
import com.mimi.HotelProject.mapper.*;
import com.mimi.HotelProject.service.*;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class Launcher implements CommandLineRunner {

    private final RoomService roomService;
    private final UserService userService;
    private final GuestService guestService;
    private final RoleService roleService;
    private final ReservationService reservationService;
    private final ReviewService reviewService;
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final ReservationMapper reservationMapper;
    private final ReviewMapper reviewMapper;
    private final RoomMapper roomMapper ;
    private  final GuestMapper guestMapper;

    @Autowired
    public Launcher(RoomService roomService, UserService userService, GuestService guestService,
                    RoleService roleService, ReservationService reservationService, ReviewService reviewService, RoleMapper roleMapper, UserMapper userMapper, ReservationMapper reservationMapper, ReviewMapper reviewMapper, RoomMapper roomMapper, GuestMapper guestMapper) {
        this.roomService = roomService;
        this.userService = userService;
        this.guestService = guestService;
        this.roleService = roleService;
        this.reservationService = reservationService;
        this.reviewService = reviewService;
        this.roleMapper = roleMapper;
        this.userMapper = userMapper;
        this.reservationMapper = reservationMapper;
        this.reviewMapper = reviewMapper;
        this.roomMapper = roomMapper;
        this.guestMapper = guestMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        createRoles();
        createRooms();
        createAdmin();
        createGuests();
        createReservations();
        createReviews();

        readRooms();
        readUsers();
        readGuests();
        readRoles();
        readReservations();
        readReviews();

        // Creating an updated RoomDTO instance
        RoomDTO updatedRoom = new RoomDTO();
        updatedRoom.setRoomId(1L);
        updatedRoom.setRoomNumber("101");
        updatedRoom.setType("Suite");
        updatedRoom.setPrice(150.0);
        updatedRoom.setAvailable(true);
        updatedRoom.setCapacity(2);
        updatedRoom.setDescription("Luxurious suite with ocean view");
// Assuming photos are stored as a List<String>
        updatedRoom.setPhotos(Arrays.asList("photo1.jpg", "photo2.jpg"));

// Creating an updated UserDTO instance
        UserDTO updatedUser = new UserDTO();
        updatedUser.setUserId(1L);
        updatedUser.setUsername("newUsername");
        updatedUser.setPassword("newPassword");

// Creating an updated GuestDTO instance
        GuestDTO updatedGuest = new GuestDTO();
        updatedGuest.setGuestId(1L);
        updatedGuest.setFirstName("John");
        updatedGuest.setLastName("Doe");
        updatedGuest.setPhoneNumber("123456789");

// Creating an updated RoleDTO instance
        RoleDTO updatedRole = new RoleDTO();
        updatedRole.setRoleId(1L);
        updatedRole.setRoleName("Admin");

// Creating an updated ReservationDTO instance
        ReservationDTO updatedReservation = new ReservationDTO();
        updatedReservation.setReservationId(1L);
        updatedReservation.setCheckInDate(new Date()); // Assuming current date for check-in;
        updatedReservation.setCheckOutDate(new Date(System.currentTimeMillis() + 86400000)); // Assuming check-out date is 1 day ahead
        updatedReservation.setPaymentMethod("Credit Card");

// Creating an updated ReviewDTO instance
        ReviewDTO updatedReview = new ReviewDTO();
        updatedReview.setReviewId(1L);
        updatedReview.setComment("Great experience!");
        updatedReview.setRating(5);


//        // Example delete methods
//        Long roomIdToDelete = 1L;
//        deleteRoom(roomIdToDelete);
//
//        Long userIdToDelete = 1L;
//        deleteUser(userIdToDelete);
//
//        Long guestIdToDelete = 1L;
//        deleteGuest(guestIdToDelete);
//
//        Long roleIdToDelete = 2L;
//        deleteRole(roleIdToDelete);
//
//        Long reservationIdToDelete = 1L;
//        deleteReservation(reservationIdToDelete);
//
//        Long reviewIdToDelete = 1L;
//        deleteReview(reviewIdToDelete);
    }

    private void createRooms() {
        // Example 1: Create a single room
        RoomDTO roomDTO1 = new RoomDTO();
        roomDTO1.setRoomNumber("101");
        roomDTO1.setType("Standard");
        roomDTO1.setPrice(100.0);
        roomDTO1.setAvailable(true);
        roomDTO1.setCapacity(2);
        roomDTO1.setDescription("A cozy standard room");
        RoomDTO createdRoom1 = roomService.saveRoom(roomDTO1);
        System.out.println("Created Room 1: " + createdRoom1);

        // Example 2: Create multiple rooms
        RoomDTO roomDTO2 = new RoomDTO();
        roomDTO2.setRoomNumber("201");
        roomDTO2.setType("Deluxe");
        roomDTO2.setPrice(150.0);
        roomDTO2.setAvailable(true);
        roomDTO2.setCapacity(3);
        roomDTO2.setDescription("A spacious deluxe room");
        RoomDTO createdRoom2 = roomService.saveRoom(roomDTO2);
        System.out.println("Created Room 2: " + createdRoom2);

        RoomDTO roomDTO3 = new RoomDTO();
        roomDTO3.setRoomNumber("301");
        roomDTO3.setType("Suite");
        roomDTO3.setPrice(200.0);
        roomDTO3.setAvailable(false);
        roomDTO3.setCapacity(4);
        roomDTO3.setDescription("A luxurious suite");
        RoomDTO createdRoom3 = roomService.saveRoom(roomDTO3);
        System.out.println("Created Room 3: " + createdRoom3);
    }

    private void createAdmin() {
        // Create a user for admin
        UserDTO adminUserDTO = new UserDTO();
        adminUserDTO.setUsername("admin");
        adminUserDTO.setPassword("admin");
        adminUserDTO.setRole(roleMapper.toRoleDTO(roleService.findByRoleName("ADMIN")));
        adminUserDTO = userMapper.toUserDTO(userService.saveUser(adminUserDTO));
        System.out.println("Created admin user: " + adminUserDTO);
    }

    private void createGuests() {
        // Example 1: Create a single guest
        GuestDTO guestDTO1 = new GuestDTO();
        guestDTO1.setFirstName("Aguest");
        guestDTO1.setLastName("Aguest");
        guestDTO1.setPhoneNumber("123456789");
        UserDTO userDTO1 = UserDTO.builder()
                .username("Aguest")
                .password("guest")
                .build();
        guestDTO1.setUser(userDTO1);
        GuestDTO createdGuest1 = guestService.saveGuest(guestDTO1);
        System.out.println("Created Guest 1: " + createdGuest1);

        // Example 2:
        GuestDTO guestDTO2 = new GuestDTO();
        guestDTO2.setFirstName("anotherGuest");
        guestDTO2.setLastName("anotherGuest");
        guestDTO2.setPhoneNumber("987654321");
        UserDTO userDTO2 = UserDTO.builder()
                .username("anotherGuest")
                .password("guest")
                .build();
        guestDTO2.setUser(userDTO2);
        GuestDTO createdGuest2 = guestService.saveGuest(guestDTO2);
        System.out.println("Created Guest 2: " + createdGuest2);

        GuestDTO guestDTO3 = new GuestDTO();
        guestDTO3.setFirstName("guest");
        guestDTO3.setLastName("guest");
        guestDTO3.setPhoneNumber("456789123");
        UserDTO userDTO3 = UserDTO.builder()
                .username("guest")
                .password("guest")
                .build();
        guestDTO3.setUser(userDTO3);
        GuestDTO createdGuest3 = guestService.saveGuest(guestDTO3);
        System.out.println("Created Guest 3: " + createdGuest3);
    }
    private void createRoles() {
        // Create GUEST role
        RoleDTO guestRoleDTO = roleService.saveRole("GUEST");
        System.out.println("Created GUEST role: " + guestRoleDTO);

        // Create WRONG role
        RoleDTO wrongRoleDTO = roleService.saveRole("WRONG");
        System.out.println("Created GUEST role: " + guestRoleDTO);

        // Create ADMIN role
        RoleDTO adminRoleDTO = roleService.saveRole("ADMIN");
        System.out.println("Created ADMIN role: " + adminRoleDTO);
    }

    private void createReservations() {
        // Get a sample room for reservation
        RoomDTO roomDTO = roomService.getRoomByRoomNumber("101");
        RoomDTO roomDTO2 = roomService.getRoomByRoomNumber("201");

        // Get a sample guest for reservation
        GuestDTO guestDTO = guestService.getGuestsByUsername("guest");
        GuestDTO guestDTO2 = guestService.getGuestsByUsername("anotherGuest");

        // Create reservations with different dates
        Date now = new Date();
        Date checkInDate1 = DateUtils.addDays(now, 3); // Check-in date after 3 days from now
        Date checkOutDate1 = DateUtils.addDays(checkInDate1, 5); // Check-out date after 5 days from check-in date

        Date checkInDate2 = DateUtils.addDays(now, 8); // Check-in date after 7 days from now
        Date checkOutDate2 = DateUtils.addDays(checkInDate2, 5); // Check-out date after 5 days from check-in date

        // Create reservation 1
        ReservationDTO reservationDTO1 = new ReservationDTO();
        reservationDTO1.setRoom(roomDTO);
        reservationDTO1.setGuest(guestDTO);
        reservationDTO1.setCheckInDate(checkInDate1);
        reservationDTO1.setCheckOutDate(checkOutDate1);
        reservationDTO1.setPaymentMethod("CREDIT_CARD");
        reservationDTO1 = reservationService.saveReservation(reservationDTO1);
        System.out.println("Created reservation: " + reservationDTO1);

        // Create reservation 2
        ReservationDTO reservationDTO2 = new ReservationDTO();
        reservationDTO2.setRoom(roomDTO2);
        reservationDTO2.setGuest(guestDTO2);
        reservationDTO2.setCheckInDate(checkInDate2);
        reservationDTO2.setCheckOutDate(checkOutDate2);
        reservationDTO2.setPaymentMethod("CASH");
        reservationDTO2 = reservationService.saveReservation(reservationDTO2);
        System.out.println("Created reservation: " + reservationDTO2);
    }



    private void createReviews() {
        // Get a sample reservation for review
        ReservationDTO reservationDTO1 =reservationMapper.toReservationDTO( reservationService.getReservationById(1L));
        ReservationDTO reservationDTO2 =reservationMapper.toReservationDTO( reservationService.getReservationById(2L));

        // Create reviews for the reservation
        ReviewDTO reviewDTO1 = new ReviewDTO();
        reviewDTO1.setComment("Great experience! Clean room and friendly staff.");
        reviewDTO1.setRating(9); // Rating out of 10
        reviewDTO1 = reviewService.saveReview(reviewDTO1);
        reservationDTO1.setReview(reviewDTO1);
        reservationService.updateReservation(reservationDTO1);
        System.out.println("Created review: " + reviewDTO1);

        ReviewDTO reviewDTO2 = new ReviewDTO();
        reviewDTO2.setComment("Disappointing. Room was not clean and service was poor.");
        reviewDTO2.setRating(3); // Rating out of 10
        reviewDTO2 = reviewService.saveReview(reviewDTO2);
        reservationDTO2.setReview(reviewDTO2);
        reservationService.updateReservation(reservationDTO2);
        System.out.println("Created review: " + reviewDTO2);
    }


    private void readRooms() {
        int page = 0; // Page number
        int size = 10; // Number of rooms per page

        // Retrieve rooms from the service
        Page<RoomDTO> roomPage = roomService.getAllRooms(size, page);
        List<RoomDTO> rooms = roomPage.getContent();

        // Print the retrieved rooms
        System.out.println("Rooms:");
        for (RoomDTO room : rooms) {
            System.out.println(room);
        }
    }

    private void readUsers() {
        // Retrieve all users from the service
        List<User> users = userService.findAll();

        // Print the retrieved users
        System.out.println("Users:");
        for (User user : users) {
            System.out.println(user);
        }
    }

    private void readGuests() {
        int page = 0; // Page number
        int size = 10; // Number of guests per page

        // Retrieve guests from the service
        Page<GuestDTO> guestPage = guestService.getAllGuests(size, page);
        List<GuestDTO> guests = guestPage.getContent();

        // Print the retrieved guests
        System.out.println("Guests:");
        for (GuestDTO guest : guests) {
            System.out.println(guest);
        }
    }

    private void readRoles() {
        int page = 0; // Page number
        int size = 10; // Number of roles per page

        // Retrieve roles from the service
        Page<RoleDTO> rolePage = roleService.findAllRoles(size, page);
        List<RoleDTO> roles = rolePage.getContent();

        // Print the retrieved roles
        System.out.println("Roles:");
        for (RoleDTO role : roles) {
            System.out.println(role);
        }
    }


    private void readReservations() {
        int page = 0; // Page number
        int size = 10; // Number of reservations per page

        // Retrieve reservations from the service
        Page<ReservationDTO> reservationPage = reservationService.getAllReservations(size, page);
        List<ReservationDTO> reservations = reservationPage.getContent();

        // Print the retrieved reservations
        System.out.println("Reservations:");
        for (ReservationDTO reservation : reservations) {
            System.out.println(reservation);
        }
    }

    private void readReviews() {
        int page = 0; // Page number
        int size = 10; // Number of reviews per page

        // Retrieve reviews from the service
        Page<ReviewDTO> reviewPage = reviewService.getAllReviews(page,size);
        List<ReviewDTO> reviews = reviewPage.getContent();

        // Print the retrieved reviews
        System.out.println("Reviews:");
        for (ReviewDTO review : reviews) {
            System.out.println(review);
        }
    }

    private void updateRoom(Long roomId, RoomDTO updatedRoom) {
        // Retrieve the room by ID
        RoomDTO roomToUpdate = roomMapper.toRoomDTO(roomService.getRoomById(roomId));

        // Update the room fields
        roomToUpdate.setRoomNumber(updatedRoom.getRoomNumber());
        roomToUpdate.setType(updatedRoom.getType());
        roomToUpdate.setPrice(updatedRoom.getPrice());
        roomToUpdate.setAvailable(updatedRoom.isAvailable());
        roomToUpdate.setCapacity(updatedRoom.getCapacity());
        roomToUpdate.setDescription(updatedRoom.getDescription());
        roomToUpdate.setPhotos(updatedRoom.getPhotos());

        // Save the updated room
        roomService.updateRoom(roomToUpdate);
        System.out.println("Room updated successfully.");
    }

    private void updateUser(Long userId, UserDTO updatedUser) {
        // Retrieve the user by ID
        User userToUpdate = userService.findUserById(userId);

        // Update the user fields
        userToUpdate.setUsername(updatedUser.getUsername());
        userToUpdate.setPassword(updatedUser.getPassword());

        // Save the updated user
        userService.updateUser(userMapper.toUserDTO(userToUpdate));
        System.out.println("User updated successfully.");
    }

    private void updateGuest(Long guestId, GuestDTO updatedGuest) {
        // Retrieve the guest by ID
        GuestDTO guestToUpdate = guestMapper.toGuestDTO(guestService.getGuestById(guestId));

        // Update the guest fields
        guestToUpdate.setFirstName(updatedGuest.getFirstName());
        guestToUpdate.setLastName(updatedGuest.getLastName());
        guestToUpdate.setPhoneNumber(updatedGuest.getPhoneNumber());

        // Save the updated guest
        guestService.updateGuest(guestToUpdate);
        System.out.println("Guest updated successfully.");
    }

    private void updateRole(Long roleId, RoleDTO updatedRole) {
        // Retrieve the role by ID
        RoleDTO roleToUpdate =roleMapper.toRoleDTO( roleService.findByRoleId(roleId));

        // Update the role fields
        roleToUpdate.setRoleName(updatedRole.getRoleName());

        // Save the updated role
        roleService.updateRole(roleToUpdate);
        System.out.println("Role updated successfully.");
    }

    private void updateReservation(Long reservationId, ReservationDTO updatedReservation) {
        // Retrieve the reservation by ID
        ReservationDTO reservationToUpdate =reservationMapper.toReservationDTO(reservationService.getReservationById(reservationId));

        // Update the reservation fields
        reservationToUpdate.setCheckInDate(updatedReservation.getCheckInDate());
        reservationToUpdate.setCheckOutDate(updatedReservation.getCheckOutDate());
        reservationToUpdate.getRoom().setRoomId(updatedReservation.getRoom().getRoomId());
        reservationToUpdate.getGuest().setGuestId(updatedReservation.getGuest().getGuestId());
        reservationToUpdate.setPaymentMethod(updatedReservation.getPaymentMethod());

        // Save the updated reservation
        reservationService.updateReservation(reservationToUpdate);
        System.out.println("Reservation updated successfully.");
    }

    private void updateReview(Long reviewId, ReviewDTO updatedReview) {
        // Retrieve the review by ID
        ReviewDTO reviewToUpdate = reviewMapper.toReviewDTO(reviewService.getReviewById(reviewId));

        // Update the review fields
        reviewToUpdate.setComment(updatedReview.getComment());
        reviewToUpdate.setRating(updatedReview.getRating());

        // Save the updated review
        reviewService.updateReview(reviewToUpdate);
        System.out.println("Review updated successfully.");
    }


    private void deleteRoom(Long roomId) {
        // Delete the room by ID
        roomService.deleteRoom(roomId);
        System.out.println("Room deleted successfully.");
    }

    private void deleteUser(Long userId) {
        // Delete the user by ID
        userService.deleteUser(userId);
        System.out.println("User deleted successfully.");
    }

    private void deleteGuest(Long guestId) {
        // Delete the guest by ID
        guestService.deleteGuest(guestId);
        System.out.println("Guest deleted successfully.");
    }

    private void deleteRole(Long roleId) {
        // Delete the role by ID
        roleService.deleteRole(roleId);
        System.out.println("Role deleted successfully.");
    }

    private void deleteReservation(Long reservationId) {
        // Delete the reservation by ID
        reservationService.deleteReservation(reservationId);
        System.out.println("Reservation deleted successfully.");
    }

    private void deleteReview(Long reviewId) {
        // Delete the review by ID
        reviewService.deleteReview(reviewId);
        System.out.println("Review deleted successfully.");
    }

}

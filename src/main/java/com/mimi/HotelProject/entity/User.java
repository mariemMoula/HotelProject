package com.mimi.HotelProject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String username;
    private String password;

    @ToString.Exclude
    @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE)
    private Guest guest;

    // this is the master entity , it has the key of the role table
    @ManyToOne // this represents the column name in the table of user , and it has refrence of the id of table role
    @JoinColumn(name = "role_id",referencedColumnName = "role_id")
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(userId, user.userId) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password);
    }
}

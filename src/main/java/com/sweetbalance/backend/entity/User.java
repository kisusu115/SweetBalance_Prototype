package com.sweetbalance.backend.entity;

import com.sweetbalance.backend.enums.user.Gender;
import com.sweetbalance.backend.enums.user.LoginType;
import com.sweetbalance.backend.enums.user.Role;
import com.sweetbalance.backend.enums.common.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter @Setter
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(length = 255)
    private String password;

    @Column(nullable = false, length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status;
}

package org.javaproteam27.socialnetwork.model.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "person")
@Data
@RequiredArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private LocalDateTime regDate;
    private LocalDateTime birthDate;
    private String email;
    private String phone;
    private String password;
    private String photo;
    private String about;
    private String town;
    private Integer confirmationCode;
    private Boolean isApproved;
    private String messagesPermission;
    private LocalDateTime lastOnlineTime;
    private Boolean isBlocked;
    private String token;


    public Person(int id, String email) {
        this.id = id;
        this.email = email;
    }
}

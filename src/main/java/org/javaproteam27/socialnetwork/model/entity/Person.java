package org.javaproteam27.socialnetwork.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.javaproteam27.socialnetwork.model.enums.MessagesPermission;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Pattern(regexp = "[A-Z][a-z]{2,15}|[А-ЯЁ][а-яё]{2,15}", message = "Неверно введено имя")
    private String firstName;
    @Pattern(regexp = "[A-Z][a-z]{2,15}|[А-ЯЁ][а-яё]{2,15}", message = "Неверно введена Фамилия")
    private String lastName;
    private LocalDateTime regDate;
    private LocalDateTime birthDate;
    @Email
    private String email;
    private String phone;
    private String password;
    private String photo;
    private String about;
    private Integer cityId;
    private City city;
    private Country country;
    private Integer confirmationCode;
    private Boolean isApproved;
    private MessagesPermission messagesPermission;
    private LocalDateTime lastOnlineTime;
    private Boolean isBlocked;
    private String token;

    public Person(String email) {
        this.email = email;
    }
}

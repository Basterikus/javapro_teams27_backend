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
    private Integer cityId;
    private Integer confirmationCode;
    private Boolean isApproved;
    private MessagesPermission messagesPermission;
    private LocalDateTime lastOnlineTime;
    private Boolean isBlocked;
    private String token;
}

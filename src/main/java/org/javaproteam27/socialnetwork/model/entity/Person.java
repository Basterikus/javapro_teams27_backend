package org.javaproteam27.socialnetwork.model.entity;

import lombok.Data;
import org.javaproteam27.socialnetwork.model.enums.MessagesPermission;

import java.time.LocalDateTime;

@Data
public class Person {
    
    private Integer id;
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
    private MessagesPermission messagesPermission; // enum
    private LocalDateTime lastOnlineTime;
    private Boolean isBlocked;
    
}

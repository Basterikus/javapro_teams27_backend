package org.javaproteam27.socialnetwork.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Person {

    public Person(int id, String email) {
        this.id = id;
        this.email = email;
    }

    public enum MessagesPermissionType {
        All,
        FRIENDS
    }

    private Integer id;
    private String firstName;
    private String lastName;
    private Long regDate;
    private Long birthDate;
    private String email;
    private String phone;
    private String photo;
    private String about;
    private City city;
    private Country country;
    private Integer confirmationCode;
    private Boolean isApproved;
    private MessagesPermissionType messagesPermission;
    private Long lastOnlineTime;
    private Boolean isBlocked;
}

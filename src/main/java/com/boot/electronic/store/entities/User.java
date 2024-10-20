package com.boot.electronic.store.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {

    @Id
    private String userId;
    @Column(name = "user_name")
    private String name;
    @Column(name="user_email")
    private String email;
    @Column(name="user_password")
    private String password;
    @Column(name="user_gender")
    private String gender;
    @Column(length = 1000)
    private String about;
    @Column(name="user_image_name")
    private String imagename;

}

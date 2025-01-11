package com.example.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class User {
    @Id
    private long id;
    private String name;
    private String password;
    @Builder
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}

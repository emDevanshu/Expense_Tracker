package com.training.expenseTracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "User_Table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
}
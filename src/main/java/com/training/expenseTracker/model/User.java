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

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;
}
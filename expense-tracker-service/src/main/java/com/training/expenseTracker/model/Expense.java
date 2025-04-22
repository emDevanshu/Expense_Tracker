package com.training.expenseTracker.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Expense_Table")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Amount", nullable = false)
    private Double amount;

    @Column(name = "Category", nullable = false)
    private String category;
}

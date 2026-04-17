package com.employeemanagementsystem.ems.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@ToString
@Table(name = "email_details")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String recipient;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false,length = 1000)
    private String body;
    @Column(nullable = false)
    private LocalDate sentAt;
    @Column(nullable = false)
    private String status;

}

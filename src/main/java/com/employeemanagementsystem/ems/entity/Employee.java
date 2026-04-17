package com.employeemanagementsystem.ems.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "employee_table")
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id ;
    @Column(name = "emp_name",nullable = false,unique = true)
    private String name;
    @Column(name = "emp_gender",nullable = false)
    private String gender ;
    @Column(name = "emp_age",nullable = false)
    private int age ;
    @Column(name = "emp_email",nullable = false,unique = true)
    private String email;
    @Column(name = "emp_username",nullable = false,unique = true)
    private String username;
    @Column(name = "emp_password",nullable = false)
    private String password;
    @Column(name = "emp_designation",nullable = false)
    private String designation;
    @Column(name = "emp_department",nullable = false)
    private String department;
    @Column(name = "emp_salary",nullable = false)
    private double salary;
    @Column(name = "emp_contact ",nullable = false,unique = true)
    private String contact;
    @Column(nullable = false)
    private String accountStatus;
    @Column(nullable = false)
    private String role;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Leave> leaves;
    @Column(name="emp_image",nullable = false,columnDefinition = "LONGBLOB")
    private byte[] picture;


    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Duty> duty;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;




}

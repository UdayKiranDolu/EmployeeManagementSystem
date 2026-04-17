package com.employeemanagementsystem.ems.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "duty_table")
public class Duty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false,length = 3000)
    private String description;

    @ManyToOne
    @JoinColumn(name="emp_id")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name ="manager_id")
    private Manager manager;
    @ManyToOne
    @JoinColumn(name="assignedByManager")
    private Manager assignedByManager;
    @ManyToOne
    @JoinColumn(name="assignedByAdmin")
    private Admin assignedByAdmin;
}

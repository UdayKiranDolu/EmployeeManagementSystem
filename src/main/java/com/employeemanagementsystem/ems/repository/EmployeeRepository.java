package com.employeemanagementsystem.ems.repository;

import com.employeemanagementsystem.ems.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

   //Search Functionality
   public List<Employee> findByNameContainingIgnoreCase(String name);

   public Employee findByUsernameAndPassword(String username, String password);

   public Employee findByUsername(String username);

   public Employee findByEmail(String email);










}

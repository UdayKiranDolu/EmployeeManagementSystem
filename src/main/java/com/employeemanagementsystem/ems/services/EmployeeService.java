package com.employeemanagementsystem.ems.services;

import com.employeemanagementsystem.ems.entity.Duty;
import com.employeemanagementsystem.ems.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public Employee checkEmpLogin(String username, String password);

    public String registerEmployee(Employee emp);

    public Optional<Employee> findEmployeeById(Long id);

    public Employee findEmployeeByUsername(String username);

    public Employee findEmployeeByEmail(String email);

    public List<Employee> viewAllEmployees();

    public String updateAccountStatus(Long empid, String status);

    public List<Duty> viewAssignedDuties(Long empid);

    public String generateRandomResetToken(String email);

    public String updateEmployeeProfile(Employee employee);

    public boolean validateResetToken(String Token);

    public boolean changePassword(Employee employee, String oldPassword, String newPassword);

    public void updatePassword(String token, String newPassword);

    public boolean isTokenExpired(String Token);

    public void deleteResetToken(String Token);







}

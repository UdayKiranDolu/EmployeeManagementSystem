package com.employeemanagementsystem.ems.services;

import com.employeemanagementsystem.ems.entity.Employee;
import com.employeemanagementsystem.ems.entity.Manager;

import java.util.List;

public interface ManagerService {

    public Manager checkManagerLogin(String username, String password);

    public Manager findManagerById(Long id);

    public Manager findManagerByUsername(String username);

    public Manager findManagerByEmail(String email);

    public List<Manager> viewAllManagers();

    public List<Employee> viewEmployees();

    public String updateEmployeeAccountStatus(Long employeeid, String status);



    public String generateRandomResetToken(String email);

    public boolean validateResetToken(String Token);

    public boolean changePassword(Manager manager, String oldPassword, String newPassword);

    public void updatePassword(String token, String newPassword);

    public boolean isTokenExpired(String Token);

    public void deleteResetToken(String Token);






}

package com.employeemanagementsystem.ems.services;

import com.employeemanagementsystem.ems.entity.Admin;
import com.employeemanagementsystem.ems.entity.Employee;
import com.employeemanagementsystem.ems.entity.Leave;
import com.employeemanagementsystem.ems.entity.Manager;

import org.springframework.stereotype.Service;

import java.util.List;


public interface AdminService {

    public Admin checkAdminLogin(String username, String password);

    public Manager addManager(Manager manager);
    public List<Manager> viewAllManagers();
    public Manager viewManagersById(Long eid);
    public String deleteManager(Long eid);
    public Long managerCount();
//    public Employee addEmployee(Employee employee);
    public List<Employee> viewAllEmployees();
    public Employee viewEmployeeById(Long id);
    public String deleteEmployee(Long eid);
    public Long employeeCount();
    public List<Leave> viewAllLeaveApplicants();

//    public String assignedDutyToManager(Duty duty, Long managerid);
//    public String assignedDutyToEmployee(Duty duty, Long employeeid);





}

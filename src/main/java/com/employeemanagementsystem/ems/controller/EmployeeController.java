package com.employeemanagementsystem.ems.controller;

import com.employeemanagementsystem.ems.entity.Duty;
import com.employeemanagementsystem.ems.entity.Employee;
import com.employeemanagementsystem.ems.entity.Leave;
import com.employeemanagementsystem.ems.security.JWTUtilizer;
import com.employeemanagementsystem.ems.services.EmployeeService;
import com.employeemanagementsystem.ems.services.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private JWTUtilizer jwtService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LeaveService leaveService;

    private boolean isAuthorized(String authHeader, String expectedrole){
        try{

        String token = authHeader.substring(7);
        String role = jwtService.validateToken(token).get(expectedrole);

        return role.equals(expectedrole);
        } catch (Exception e) {
            return false;
        }
    }


    @PostMapping(value="/addEmployee",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerEmployee(
            @RequestParam String name,
            @RequestParam String gender,
            @RequestParam String designation,
            @RequestParam String department,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String contact,
            @RequestParam int age,
            @RequestParam double salary,
            @RequestParam MultipartFile picture){

        try{
            Employee employee = new Employee();
            employee.setName(name);
            employee.setGender(gender);
            employee.setDesignation(designation);
            employee.setDepartment(department);
            employee.setUsername(username);
            employee.setEmail(email);
            employee.setContact(contact);
            employee.setSalary(salary);
            employee.setAge(age);
            employee.setPicture(picture.getBytes());

            return ok(employeeService.registerEmployee(employee));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Occured");
        }
    }


    @GetMapping("/viewprofile")
    public ResponseEntity<Optional<Employee>> viewProfile(@RequestParam Long empid, @RequestHeader("Authorization") String authHeader){

        if (!isAuthorized(authHeader,"EMPLOYEE")){
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(employeeService.findEmployeeById(empid));
    }

    @GetMapping("/viewassignedduties")
    public ResponseEntity<List<Duty>> viewAssignedDuties(@RequestParam Long empid, @RequestHeader("Authorisation") String authHeader){

        if (!isAuthorized(authHeader,"EMPLOYEE")){
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(employeeService.viewAssignedDuties(empid));
    }


    @GetMapping("/applyleave")
    public ResponseEntity<Leave> applyLeave(@RequestBody Leave leave, @RequestParam Long empid,@RequestHeader("Authorisation") String authHeader){
        if (!isAuthorized(authHeader,"EMPLOYEE")){
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(leaveService.applyLeaveByEmployee(leave,empid));
    }

    @GetMapping("/viewleaves")
    public ResponseEntity<List<Leave>> viewLeaveByEmpId(@RequestParam Long empid,@RequestHeader("Authorisation") String authHeader) {
        if (!isAuthorized(authHeader, "EMPLOYEE")) {
            return ResponseEntity.status(403).body(null);
        }
        List<Leave> leaves = leaveService.viewLeavesByEmployees(empid);
        return ResponseEntity.ok(leaves);
    }



}

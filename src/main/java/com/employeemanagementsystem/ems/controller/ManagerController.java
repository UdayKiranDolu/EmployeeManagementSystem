package com.employeemanagementsystem.ems.controller;


import com.employeemanagementsystem.ems.entity.Duty;
import com.employeemanagementsystem.ems.entity.Employee;
import com.employeemanagementsystem.ems.entity.Leave;
import com.employeemanagementsystem.ems.entity.Manager;
import com.employeemanagementsystem.ems.security.JWTUtilizer;
import com.employeemanagementsystem.ems.services.DutyService;
import com.employeemanagementsystem.ems.services.EmployeeService;
import com.employeemanagementsystem.ems.services.LeaveService;
import com.employeemanagementsystem.ems.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/manager")
@CrossOrigin("*")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private JWTUtilizer jwtService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private DutyService dutyService;

    private boolean isAuthorized(String authHeader, String expectedRole){
        try {
            String token = authHeader.substring(7);
            String role = jwtService.validateToken(token).get("role");
            return role.equals(expectedRole);
        } catch (Exception e) {
            return false;
        }

    }

    @GetMapping("/viewallemployees")
    public ResponseEntity<List<Employee>> viewAllEmployees(@RequestHeader("Authorization") String authHeader ){

        if(!isAuthorized(authHeader, "MANAGER")){
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(managerService.viewEmployees());
    }

    @GetMapping("/updateempaccstatus")
    public ResponseEntity<String> updateEmpAccStatus(@RequestParam Long empid,@RequestParam String status,@RequestHeader("Authorization") String authHeader){
        if(!isAuthorized(authHeader, "MANAGER")){
            return ResponseEntity.status(403).body(null);
        }
        String message = managerService.updateEmployeeAccountStatus(empid,status.toUpperCase());
        return ResponseEntity.ok(message);
    }

    @PostMapping("/applyleave")
    public ResponseEntity<Leave> applyLeave(@RequestParam Long managerid, @RequestBody Leave leave ,@RequestHeader("Authorization") String authHeader ){
        if(!isAuthorized(authHeader, "MANAGER")){
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(leaveService.applyByManager(leave, managerid));

    }

    @GetMapping("/viewownleaves")
    public ResponseEntity<List<Leave>> viewOwnLeaves(@RequestParam Long managerid,@RequestHeader("Authorization") String authHeader){
        if(!isAuthorized(authHeader, "MANAGER")){
            return ResponseEntity.status(403).body(null);
        }
        List<Leave> leaves = leaveService.viewLeavesByManager(managerid);
        return ResponseEntity.ok(leaves);
    }

    @PutMapping("/updateleavestatus")
    public ResponseEntity<String> updateEmpLeaveStatus(@RequestParam String status,@RequestParam Long leaveid,@RequestHeader("Authorization") String authHeader){
        if(!isAuthorized(authHeader, "MANAGER")){
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(leaveService.updateLeaveStatus(leaveid,status));
    }

    @PostMapping("/assigndutytoemployee")
    public ResponseEntity<String> assignDutyToEmployee(@RequestBody Duty duty,@RequestParam Long managerid,@RequestParam Long empid,@RequestHeader("Authorization") String authHeader){
        if(!isAuthorized(authHeader, "MANAGER")){
            return ResponseEntity.status(403).body(null);
        }
         dutyService.assignDutyByManagerToEmployee(duty,managerid,empid);
        return ResponseEntity.ok("Duty assigned to employee with ID:"+empid+"is successful .");
    }

    public ResponseEntity<List<Duty>> viewAssignedDuties(@RequestParam Long managerid,@RequestHeader("Authorization") String authHeader){
        if(!isAuthorized(authHeader, "MANAGER")){
            return ResponseEntity.status(403).body(null);
        }
        List<Duty> duties = dutyService.viewDutiesAssignedByManager(managerid);
        return ResponseEntity.ok(duties);
    }

}

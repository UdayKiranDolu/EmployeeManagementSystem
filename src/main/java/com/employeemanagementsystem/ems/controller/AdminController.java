package com.employeemanagementsystem.ems.controller;


import com.employeemanagementsystem.ems.entity.*;
import com.employeemanagementsystem.ems.repository.AdminRepository;
import com.employeemanagementsystem.ems.security.JWTUtilizer;
import com.employeemanagementsystem.ems.services.AdminService;
import com.employeemanagementsystem.ems.services.DutyService;
import com.employeemanagementsystem.ems.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private JWTUtilizer jwtService;

    @Autowired
    private DutyService dutyService;

    @PostMapping("/addManager")
    public ResponseEntity<String> addManager(@RequestBody Manager manager,@RequestHeader("Authorization") String authHeader){

         String token = authHeader.substring(7);
         if(!jwtService.validateToken(token).get("role").equals("ADMIN")){
             return ResponseEntity.status(403).body("Access Denied! Admin Privileges Required");
         }
         adminService.addManager(manager);
         return ResponseEntity.ok("Manager added Successfully With ID:"+manager.getId());
    }

    @GetMapping("/viewallmanagers")
    public ResponseEntity<List<Manager>> viewAllManagers(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        if(!jwtService.validateToken(token).get("role").equals("ADMIN")){
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(adminService.viewAllManagers());
    }

    @GetMapping("/viewallemployees")
    public ResponseEntity<List<Employee>> viewAllEmployees(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        if(!jwtService.validateToken(token).get("role").equals("ADMIN")){
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(adminService.viewAllEmployees());
    }

    @PutMapping("/assigndutytomanager")
    public ResponseEntity<String> assignDutyToManager(@RequestBody Duty duty,@RequestParam String assignRole,@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        if(!jwtService.validateToken(token).get("role").equals("ADMIN")){
            return ResponseEntity.status(403).body("Access Denied! Admin Privileges Required");
        }

        Manager manager = new Manager();
        Admin a = new Admin();
        dutyService.assignDutyByAdminToManager(duty,manager.getId(),a.getId());
        return ResponseEntity.ok("Duty is assigned successfully to manager with id:"+manager.getId());
    }

    @PutMapping("/updatempaccstatus")
    public ResponseEntity<String> updateEmployeeAccountStatus(@RequestParam Long eid,@RequestParam String status,@RequestHeader("Authorisation") String authHeader){

        String token = authHeader.substring(7);
        if(!jwtService.validateToken(token).get("role").equals("ADMIN")){
            return ResponseEntity.status(403).body("Access Denied! Admin Privileges Required");
        }
        String message = managerService.updateEmployeeAccountStatus(eid,status.toUpperCase());
        return ResponseEntity.ok(message);
    }

    @PutMapping("/assigndutytoemployee")
    public ResponseEntity<String> assignDutyToEmployee(@RequestBody Duty duty,@RequestParam String assignRole,@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        if(!jwtService.validateToken(token).get("role").equals("ADMIN")){
            return ResponseEntity.status(403).body("Access Denied! Admin Privileges Required");
        }

        Employee employee = new Employee();
        Admin a = new Admin();
        dutyService.assignDutyByAdminToManager(duty,employee.getId(),a.getId());
        return ResponseEntity.ok("Duty is assigned successfully to manager with id:"+employee.getId());
    }

    @GetMapping("/viewallleaves")
    public ResponseEntity<List<Leave>> viewAllLeaves(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        if(!jwtService.validateToken(token).get("role").equals("ADMIN")){
            return ResponseEntity.status(403).body(null);
        }
        List<Leave> leaves = adminService.viewAllLeaveApplicants();
        return ResponseEntity.ok(leaves);
    }

    @DeleteMapping("/deleteemployee")
    public ResponseEntity<String> deleteEmployee(@RequestParam Long eid,@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        if(!jwtService.validateToken(token).get("role").equals("ADMIN")){
            return ResponseEntity.status(403).body(null);
        }
        String res = adminService.deleteEmployee(eid);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/deletemanager")
    public ResponseEntity<String> deleteManager(@RequestParam Long eid,@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        if(!jwtService.validateToken(token).get("role").equals("ADMIN")){
            return ResponseEntity.status(403).body(null);
        }
        String res = adminService.deleteManager(eid);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/viewemployeeduties")
    public ResponseEntity<List<Duty>> viewAssignedDuties(@RequestParam Long eid,@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token).get("role").equals("ADMIN")) {
            return ResponseEntity.status(403).body(null);
        }
        List<Duty> duties = dutyService.viewAllDutiesOfEmployee(eid);
        return ResponseEntity.ok(duties);
    }

    @GetMapping("/viewalldutiesbymanager")
    public ResponseEntity<List<Duty>> viewAllDutiesByManager(@RequestParam Long mid,@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token).get("role").equals("ADMIN")) {
            return ResponseEntity.status(403).body(null);
        }
        List<Duty> duties = dutyService.viewAllDutiesOfEmployee(mid);
        return ResponseEntity.ok(duties);
    }

    @GetMapping("/viewalldutiesbyadmin")
    public ResponseEntity<List<Duty>> viewAllDutiesByAdmin(@RequestParam Long id,@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token).get("role").equals("ADMIN")) {
            return ResponseEntity.status(403).body(null);
        }
        List<Duty> duties = dutyService.viewDutiesAssignedByAdmin(id);
        return ResponseEntity.ok(duties);
    }

    @GetMapping("/managercount")
    public ResponseEntity<Long> getManagerCount(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token).get("role").equals("ADMIN")) {
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(adminService.managerCount());
    }

    @GetMapping("/employeecount")
    public ResponseEntity<Long> getEmployeeCount(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token).get("role").equals("ADMIN")) {
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(adminService.employeeCount());
    }

}

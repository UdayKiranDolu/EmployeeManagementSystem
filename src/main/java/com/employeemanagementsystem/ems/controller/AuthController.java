package com.employeemanagementsystem.ems.controller;


import com.employeemanagementsystem.ems.dto.LoginRequest;
import com.employeemanagementsystem.ems.entity.Admin;
import com.employeemanagementsystem.ems.entity.Employee;
import com.employeemanagementsystem.ems.entity.Manager;
import com.employeemanagementsystem.ems.security.JWTUtilizer;
import com.employeemanagementsystem.ems.services.AdminService;
import com.employeemanagementsystem.ems.services.EmployeeService;
import com.employeemanagementsystem.ems.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/api")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JWTUtilizer jwtservice;

    @GetMapping("/")
    public String home(){
        return "Hey";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        String identifier = loginRequest.getIdentifier();
        String password = loginRequest.getPassword();

        Admin admin = adminService.checkAdminLogin(identifier, password);
        Employee employee = employeeService.checkEmpLogin(identifier, password);
        Manager manager = managerService.checkManagerLogin(identifier, password);

        if(admin!=null){
            String token = jwtservice.generateJJWTToken(admin.getUsername(),"ADMIN");
            Map<String , Object> res = new HashMap<>();
            res.put("role","ADMIN");
            res.put("message","Login Successful");
            res.put("token",token);
            res.put("data",admin);

            return ResponseEntity.ok(res);
        }

        if(manager!=null){
            String token = jwtservice.generateJJWTToken(manager.getUsername(),"ADMIN");
            Map<String , Object> res = new HashMap<>();
            res.put("role","MANAGER");
            res.put("message","Login Successful");
            res.put("token",token);
            res.put("data",manager);

            return ResponseEntity.ok(res);
        }

        if(employee!=null) {
            if (employee.getAccountStatus().equalsIgnoreCase("Accepeted")) {
                String token = jwtservice.generateJJWTToken(employee.getUsername(), "ADMIN");
                Map<String, Object> res = new HashMap<>();
                res.put("role", "EMPLOYEE");
                res.put("message", "Login Successful");
                res.put("token", token);
                res.put("data", employee);

                return ResponseEntity.ok(res);
            }
            else {
                return ResponseEntity.status(401).body(Map.of("message","Account Not Approved Yet PLease Contact the Admin"));
            }
        }
        return ResponseEntity.status(401).body(Map.of("message","Invalid Username/Email and Password"));
    }
}

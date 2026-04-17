package com.employeemanagementsystem.ems.services.serviceImpl;

import com.employeemanagementsystem.ems.entity.*;
import com.employeemanagementsystem.ems.repository.*;
import com.employeemanagementsystem.ems.services.AdminService;
import com.employeemanagementsystem.ems.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveRepository leaveRepository;


    @Override
    public Admin checkAdminLogin(String username, String password) {
        return adminRepository.findByUsernameAndPassword(username,password);

    }

    @Override
    public Manager addManager(Manager manager) {
        int managerId = generateRandomManagerId();
        String randomPassword = generateRandomPassword(8);
        Manager savedManager = managerRepository.save(manager);

        Email e = new Email();
        e.setRecipient(manager.getEmail());
        e.setSubject("Welcome to the EMS");
        e.setBody("Hi"+manager.getName()+
                ",\n\n You have been successfully added. \n\n ManagerID:" +
                manager.getId()+"Here is your username:"+
                manager.getUsername()+
                "\n\n Password :"+manager.getPassword());

        emailRepository.save(e);
        emailService.sendEmail(e.getRecipient(),e.getSubject(),e.getBody());

        return savedManager;
    }

    @Override
    public List<Manager> viewAllManagers() {
        return managerRepository.findAll();
    }

    @Override
    public Manager viewManagersById(Long eid) {
        return managerRepository.findById(eid).orElse(null);
    }


    @Override
    public String deleteManager(Long eid) {
        Optional<Manager> manager = managerRepository.findById(eid);
        if(manager.isPresent()){
            managerRepository.deleteById(eid);
        }
        else {
            return "No Manager is Found with ID:"+eid;
        }
        return "Deleted Manager with managerId:"+eid;
    }

    @Override
    public Long managerCount() {

        return managerRepository.count();

    }

    @Override
    public List<Employee> viewAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee viewEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public String deleteEmployee(Long eid) {
        Optional<Employee> employee = employeeRepository.findById(eid);
        if(employee.isPresent()){
            employeeRepository.deleteById(eid);
        }
        else{
            return ("No Employee is not found with ID:"+eid);
        }
        return ("Employee is deleted successfully with ID:"+eid);
    }

    @Override
    public Long employeeCount() {
        return employeeRepository.count();
    }

    @Override
    public List<Leave> viewAllLeaveApplicants() {
        return leaveRepository.findAll();
    }

//    @Override
//    public String assignedDutyToManager(Duty duty, Long managerid) {
//        Optional<Manager> manager = managerRepository.findById(managerid);
//        if (manager.isPresent()){
//
//        }
//        return "";
//    }
//
//    @Override
//    public String assignedDutyToEmployee(Duty duty, Long employeeid) {
//        return "";
//    }


    private int generateRandomManagerId(){
        Random random = new Random();
        return 1000+random.nextInt(9000);
    }

    private String generateRandomPassword(int length){
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYXZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "~!@#$%^&*";
        String digits = "0123456789";
        String combined = upper+lower+specialCharacters;

        StringBuilder sb= new StringBuilder();
        Random random = new Random();

        sb.append(upper.charAt(random.nextInt(upper.length())));
        sb.append(lower.charAt(random.nextInt(lower.length())));
        sb.append(digits.charAt(random.nextInt(digits.length())));
        sb.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));

        for (int i = 4;i<length; i++){
            sb.append(combined.charAt(random.nextInt(combined.length())));
        }
        return sb.toString();
    }
}

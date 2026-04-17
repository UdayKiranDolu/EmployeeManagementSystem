package com.employeemanagementsystem.ems.services.serviceImpl;

import com.employeemanagementsystem.ems.entity.Duty;
import com.employeemanagementsystem.ems.entity.Employee;
import com.employeemanagementsystem.ems.entity.Manager;
import com.employeemanagementsystem.ems.entity.ResetToken;
import com.employeemanagementsystem.ems.repository.DutyRepository;
import com.employeemanagementsystem.ems.repository.EmployeeRepository;
import com.employeemanagementsystem.ems.repository.ResetTokenRepository;
import com.employeemanagementsystem.ems.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DutyRepository dutyRepository;

    @Autowired
    private ResetTokenRepository resetTokenRepository;


    @Override
    public Employee checkEmpLogin(String username, String password) {
        return employeeRepository.findByUsernameAndPassword(username,password);
    }

    @Override
    public String registerEmployee(Employee emp) {
        Long eid = generateRandomEmployeeId();
        String randomPassword = generateRandomPassword(8);

        emp.setId(eid);
        emp.setPassword(randomPassword);
        emp.setAccountStatus("Pending");
        emp.setRole("Employee");

        employeeRepository.save(emp);

        return "Employee Registered Successfully";
    }

    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee findEmployeeByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    @Override
    public Employee findEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    @Override
    public List<Employee> viewAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public String updateAccountStatus(Long empid, String status) {

        Optional<Employee> emp = employeeRepository.findById(empid);
        if (emp.isPresent()){
            Employee e = new Employee();
            e.setAccountStatus(status);
            employeeRepository.save(e);
            return "Account Status is updated successfully";
        }
        return "No Employee is found with ID:"+empid;
    }

    @Override
    public List<Duty> viewAssignedDuties(Long empid) {
        Employee employee = employeeRepository.findById(empid).orElse(null);
        if(employee!=null){
            return dutyRepository.findByEmployee(employee);
        }
        return Collections.emptyList();
    }

    @Override
    public String generateRandomResetToken(String email) {
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByEmail(email));
        if(employee.isPresent()){
            String token = UUID.randomUUID().toString();
            ResetToken rt = new ResetToken();
            rt.setToken(token);
            rt.setEmail(email);
            rt.setCreatedAt(LocalDateTime.now());
            rt.setSetExpiresAt(LocalDateTime.now().plusMinutes(5));

            resetTokenRepository.save(rt);
            return token;
        }

        return "Token generation Unsuccessful";
    }

    @Override
    public String updateEmployeeProfile(Employee employee) {
        employeeRepository.save(employee);
        return "Employee Updated Successfully !";
    }

    @Override
    public boolean validateResetToken(String Token) {

        Optional<ResetToken> rt = resetTokenRepository.findByToken(Token);
        if(rt.isPresent()&&isTokenExpired(Token)){
            return true;
        }
        return false;
    }

    @Override
    public boolean changePassword(Employee employee, String oldPassword, String newPassword) {

        if(employee.getPassword().equals(oldPassword)){
            employee.setPassword(newPassword);
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    @Override
    public void updatePassword(String Token, String newPassword) {
        Optional<ResetToken> resetToken = resetTokenRepository.findByToken(Token);
        if (resetToken.isPresent()){
            Employee employee = new Employee();
            employee.setPassword(newPassword);
            employeeRepository.save(employee);
            deleteResetToken(Token);
        }
    }

    @Override
    public boolean isTokenExpired(String Token) {
        Optional<ResetToken> resetToken = resetTokenRepository.findByToken(Token);
        if (resetToken.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public void deleteResetToken(String Token) {
        Optional<ResetToken> resetToken = resetTokenRepository.findByToken(Token);
        resetTokenRepository.deleteByToken(Token);
    }

    private Long generateRandomEmployeeId(){
        Random random = new Random();
        return (long) (1000+random.nextInt(9000));
    }

    private String generateRandomPassword(int length) {

        String Upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Lower = "abcdefghijklmnopqrstuvwxyz";
        String Digits="0123456789";
        String SpecialChar="!@#$%^&*";
        String Combined = Upper+Lower+Digits+SpecialChar;

        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        sb.append(Upper.charAt(random.nextInt(Upper.length())));
        sb.append(Lower.charAt(random.nextInt(Lower.length())));
        sb.append(Digits.charAt(random.nextInt(Digits.length())));
        sb.append(SpecialChar.charAt(random.nextInt(SpecialChar.length())));

        for (int i=4;i<length;i++){
            sb.append(Combined.charAt(random.nextInt(Combined.length())));
        }
        return sb.toString();
    }
}

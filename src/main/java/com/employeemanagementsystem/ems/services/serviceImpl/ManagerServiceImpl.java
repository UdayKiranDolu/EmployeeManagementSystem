package com.employeemanagementsystem.ems.services.serviceImpl;

import com.employeemanagementsystem.ems.entity.Employee;
import com.employeemanagementsystem.ems.entity.Manager;
import com.employeemanagementsystem.ems.entity.ResetToken;
import com.employeemanagementsystem.ems.repository.EmployeeRepository;
import com.employeemanagementsystem.ems.repository.ManagerRepository;
import com.employeemanagementsystem.ems.repository.ResetTokenRepository;
import com.employeemanagementsystem.ems.services.ManagerService;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static java.lang.System.setOut;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ResetTokenRepository resetTokenRepository;

    @Override
    public Manager checkManagerLogin(String username, String password) {

        return managerRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public Manager findManagerById(Long id) {

        return managerRepository.findById(id).get();
    }

    @Override
    public Manager findManagerByUsername(String username) {
        return managerRepository.findByUsername(username);
    }

    @Override
    public Manager findManagerByEmail(String email) {
        return managerRepository.findByEmail(email);
    }

    @Override
    public List<Manager> viewAllManagers() {
        return managerRepository.findAll();
    }

    @Override
    public List<Employee> viewEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public String updateEmployeeAccountStatus(Long employeeid, String status) {
        Optional<Employee> emp = employeeRepository.findById(employeeid);
        if(emp.isPresent()){
            Employee e = new Employee();
            e.setAccountStatus(status);
            employeeRepository.save(e);
            return "Employee Account Status Updated Successfully";
        }

        return "Employee is not found with Id :"+employeeid+"\n\nEmployee Account Status Has Not been Updated ";
    }




    @Override
    public String generateRandomResetToken(String email) {
        Optional<Manager> manager = managerRepository.findOptionalByEmail(email);
        if(manager.isPresent()){
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
    public boolean  validateResetToken(String Token) {

        Optional<ResetToken> rt = resetTokenRepository.findByToken(Token);
        if(rt.isPresent()&&isTokenExpired(Token)){
            return true;
        }
        return false;
    }

    @Override
    public boolean changePassword(Manager manager, String oldPassword, String newPassword) {
        if(manager.getPassword().equals(oldPassword)){
            manager.setPassword(newPassword);
            managerRepository.save(manager);
            return true;
        }
        return false;
    }

    @Override
    public void updatePassword(String token, String newPassword) {
        Optional<ResetToken> resetToken = resetTokenRepository.findByToken(token);
        if (resetToken.isPresent()&&!isTokenExpired(token)){
            Manager manager = new Manager();
            manager.setPassword(newPassword);
            managerRepository.save(manager);
            deleteResetToken(token);
        }
    }

    @Override
    public boolean isTokenExpired(String Token) {
        Optional<ResetToken> token = resetTokenRepository.findByToken(Token);
        if (token.isPresent()){
            return token.get().getSetExpiresAt().isBefore(LocalDateTime.now());
        }
        return true;
    }

    @Override
    public void deleteResetToken(String Token) {
        Optional<ResetToken> token = resetTokenRepository.findByToken(Token);
        if (token.isPresent()){
            resetTokenRepository.deleteByToken(Token);
        }
    }
}

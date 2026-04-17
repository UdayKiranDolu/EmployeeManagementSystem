package com.employeemanagementsystem.ems.services.serviceImpl;

import com.employeemanagementsystem.ems.entity.Employee;
import com.employeemanagementsystem.ems.entity.Leave;
import com.employeemanagementsystem.ems.entity.Manager;
import com.employeemanagementsystem.ems.repository.EmployeeRepository;
import com.employeemanagementsystem.ems.repository.LeaveRepository;
import com.employeemanagementsystem.ems.repository.ManagerRepository;
import com.employeemanagementsystem.ems.services.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ManagerRepository managerRepository;


    @Override
    public Leave applyLeaveByEmployee(Leave leave, Long empid) {
        Employee employee = employeeRepository.findById(empid).orElse(null);
        if (employee!=null){
            leave.setEmployee(employee);
            leave.setStatus("Pending");
            return leaveRepository.save(leave);
        }
        return null;
    }

    @Override
    public List<Leave> viewLeavesByEmployees(Long empid) {
        return leaveRepository.findByEmployeeId(empid);

    }

    @Override
    public List<Leave> viewAllPendingLeaves() {
        return leaveRepository.findByStatus("PENDING");
    }

    @Override
    public Leave applyByManager(Leave leave, Long managerId) {
        Manager manager = managerRepository.findById(managerId).orElse(null);
        if(manager!=null){
            leave.setManager(manager);
            leave.setStatus("Pending".toUpperCase());
            return leaveRepository.save(leave);
        }
        return null;
    }

    @Override
    public List<Leave> viewLeavesByManager(Long managerid) {
        return leaveRepository.findByManagerId(managerid);
    }

    @Override
    public String updateLeaveStatus(Long leaveid, String status) {
        Leave leave = leaveRepository.findById(leaveid).orElse(null);
        if(leave!=null){
            leave.setStatus(status.toUpperCase());
            leaveRepository.save(leave);
            return "Status Updated Successfully"+status;
        }
        return "Leave is not found with ID:"+leaveid;
    }
}

package com.employeemanagementsystem.ems.services.serviceImpl;

import com.employeemanagementsystem.ems.entity.Admin;
import com.employeemanagementsystem.ems.entity.Duty;
import com.employeemanagementsystem.ems.entity.Employee;
import com.employeemanagementsystem.ems.entity.Manager;
import com.employeemanagementsystem.ems.repository.AdminRepository;
import com.employeemanagementsystem.ems.repository.DutyRepository;
import com.employeemanagementsystem.ems.repository.EmployeeRepository;
import com.employeemanagementsystem.ems.repository.ManagerRepository;
import com.employeemanagementsystem.ems.services.DutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DutyServiceImpl implements DutyService {

    @Autowired
    private DutyRepository dutyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public Duty assignDutyByAdminToEmployee(Duty duty, Long employeeid, Long adminid) {

        Employee employee = employeeRepository.findById(employeeid).orElse(null);
        Admin admin = adminRepository.findById(adminid).orElse(null);
        if (employee!=null&&admin!=null){
                duty.setEmployee(employee);
                duty.setAssignedByAdmin(admin);
                return dutyRepository.save(duty);
            }

        return null;
    }

    @Override
    public Duty assignDutyByAdminToManager(Duty duty, Long managerid, Long adminid) {

        Admin admin = adminRepository.findById(adminid).orElse(null);
        Manager manager = managerRepository.findById(managerid).orElse(null);
        if(admin!=null && manager!=null){
            duty.setEmployee(null);
            duty.setAssignedByAdmin(admin);
            duty.setManager(manager);
            return dutyRepository.save(duty);
        }
        return null;
    }

    @Override
    public Duty assignDutyByManagerToEmployee(Duty duty, Long employeeid, Long managerid) {

        Manager manager = managerRepository.findById(managerid).orElse(null);
        Employee employee = employeeRepository.findById(employeeid).orElse(null);
        if(manager!=null && employee!=null) {
            duty.setAssignedByManager(manager);
            duty.setEmployee(employee);
            return dutyRepository.save(duty);
        }
        return null;
    }

    @Override
    public List<Duty> viewAllDutiesOfEmployee(Long eid) {
        return dutyRepository.findByEmployeeId(eid);
    }

    @Override
    public List<Duty> viewDutiesAssignedByManager(Long managerid) {
        return dutyRepository.findByAssignedByManagerId(managerid);
    }

    @Override
    public List<Duty> viewDutiesAssignedByAdmin(Long adminid) {
        return dutyRepository.findByAssignedByAdminId(adminid);
    }
}

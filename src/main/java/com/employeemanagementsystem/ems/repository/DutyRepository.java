package com.employeemanagementsystem.ems.repository;


import com.employeemanagementsystem.ems.entity.Duty;
import com.employeemanagementsystem.ems.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DutyRepository extends JpaRepository<Duty,Integer> {

    public List<Duty> findByEmployee(Employee employee);

    public List<Duty> findByEmployeeId(Long employeeid);

    public List<Duty> findByAssignedByManagerId(Long managerid);

    public List<Duty> findByAssignedByAdminId(Long adminid);
}

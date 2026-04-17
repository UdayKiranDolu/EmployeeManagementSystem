package com.employeemanagementsystem.ems.repository;

import com.employeemanagementsystem.ems.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    public List<Leave> findByEmployeeId(Long id);
    public List<Leave> findByStatus(String status);
    public List<Leave> findByManagerId(Long id);


}

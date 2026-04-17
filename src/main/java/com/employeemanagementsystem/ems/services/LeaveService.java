package com.employeemanagementsystem.ems.services;

import com.employeemanagementsystem.ems.entity.Leave;

import java.util.List;

public interface LeaveService {

    public Leave applyLeaveByEmployee(Leave leave, Long empid);

    public List<Leave> viewLeavesByEmployees(Long empid);

    public List<Leave> viewAllPendingLeaves();

    public Leave applyByManager(Leave leave, Long managerId);

    public List<Leave> viewLeavesByManager(Long managerid);

    public String updateLeaveStatus(Long leaveid,String status );



}

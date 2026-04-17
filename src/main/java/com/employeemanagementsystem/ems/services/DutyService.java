package com.employeemanagementsystem.ems.services;

import com.employeemanagementsystem.ems.entity.Duty;

import java.util.List;

public interface DutyService {

    public Duty assignDutyByAdminToEmployee(Duty duty, Long employeeid, Long adminid);

    public Duty  assignDutyByAdminToManager(Duty duty, Long managerid, Long adminid);

    public Duty  assignDutyByManagerToEmployee(Duty duty, Long employeeid, Long managerid);

    public List<Duty> viewAllDutiesOfEmployee(Long eid);

    public List<Duty> viewDutiesAssignedByManager(Long managerid);

    public List<Duty> viewDutiesAssignedByAdmin(Long adminid);
}

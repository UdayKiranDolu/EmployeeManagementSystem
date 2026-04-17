package com.employeemanagementsystem.ems.repository;


import com.employeemanagementsystem.ems.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long>{

    public Admin findByUsernameAndPassword(String username, String password);
}

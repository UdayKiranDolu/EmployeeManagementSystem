package com.employeemanagementsystem.ems.repository;

import com.employeemanagementsystem.ems.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Long> {

    public Manager findByUsernameAndPassword(String username, String password);

    public Manager findByUsername(String username);

    public Manager findByEmail(String email);

//    public Optional<Manager> FindManagerByEmail(String email);

    public Optional<Manager> findOptionalByEmail(String email);
}

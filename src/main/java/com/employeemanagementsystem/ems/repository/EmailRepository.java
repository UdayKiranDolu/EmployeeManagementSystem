package com.employeemanagementsystem.ems.repository;

import com.employeemanagementsystem.ems.entity.Email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email,Long> {
}

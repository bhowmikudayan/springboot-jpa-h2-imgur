package com.imgur.demo.project.spring.h2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imgur.demo.project.spring.h2.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}

package com.spring.employee.repos;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.Spring.entities.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}

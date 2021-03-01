package com.hateoas.work.payroll.controller;

import com.hateoas.work.payroll.entity.Employee;
import com.hateoas.work.payroll.exception.EmployeeNotFoundException;
import com.hateoas.work.payroll.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeRepository reposiroty;

    EmployeeController(EmployeeRepository repository){
        this.reposiroty = repository;
    }

    @GetMapping("/employees")
    public List<Employee> all(){
        return reposiroty.findAll();
    }

    @PostMapping("/employees")
    public Employee newEmployee(@RequestBody Employee newEmployee){
        return reposiroty.save(newEmployee);
    }

    @GetMapping("/employees/{id}")
    public Employee one(@PathVariable Long id){
        return reposiroty.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/employees/{id}")
    public Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id){
        return reposiroty.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return reposiroty.save(employee);
                })
                .orElseGet(()-> {
                    newEmployee.setId(id);
                    return reposiroty.save(newEmployee);
                });
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        reposiroty.deleteById(id);
    }
}

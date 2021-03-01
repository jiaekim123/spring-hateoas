package com.hateoas.work.payroll.controller;

import com.hateoas.work.payroll.entity.Employee;
import com.hateoas.work.payroll.entity.EmployeeModelAssembler;
import com.hateoas.work.payroll.exception.EmployeeNotFoundException;
import com.hateoas.work.payroll.repository.EmployeeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {
    private final EmployeeRepository reposiroty;
    private final EmployeeModelAssembler assembler;

    EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler){
        this.reposiroty = repository;
        this.assembler = assembler;
    }

    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> all(){
        List<EntityModel<Employee>> employees = reposiroty.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @PostMapping("/employees")
    public ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee){
        EntityModel<Employee> entityModel = assembler.toModel(reposiroty.save(newEmployee));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/employees/{id}")
    public EntityModel<Employee> one(@PathVariable Long id){
        Employee employee = reposiroty.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return assembler.toModel(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id){
        Employee updatedEmployee = reposiroty.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return reposiroty.save(employee);
                })
                .orElseGet(()-> {
                    newEmployee.setId(id);
                    return reposiroty.save(newEmployee);
                });
        EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        reposiroty.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package com.hateoas.work.payroll.config;

import com.hateoas.work.payroll.entity.Employee;
import com.hateoas.work.payroll.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    CommandLineRunner initDatabase(EmployeeRepository repository) {
        return args -> {
//            log.info("Preloading " + repository.save(new Employee("BilboBaggins", "burglar")));
//            log.info("Preloading " + repository.save(new Employee("FrodoBaggins", "thief")));
            log.info("Preloading " + repository.save(new Employee("jiae", "kim", "young sister")));
            log.info("Preloading " + repository.save(new Employee("inae", "kim", "old sister")));
        };
    }
}

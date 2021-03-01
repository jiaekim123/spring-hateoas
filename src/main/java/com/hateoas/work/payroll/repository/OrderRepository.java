package com.hateoas.work.payroll.repository;

import com.hateoas.work.payroll.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

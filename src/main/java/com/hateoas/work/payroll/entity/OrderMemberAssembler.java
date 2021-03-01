package com.hateoas.work.payroll.entity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.hateoas.work.payroll.controller.OrderController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class OrderMemberAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    public EntityModel<Order> toModel(Order order) {
        EntityModel<Order> orderEntityModel =  EntityModel.of(order, linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).all()).withRel("orders"));
        if(order.getStatus() == Status.IN_PROGRESS) {
            orderEntityModel.add(linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
            orderEntityModel.add(linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complate"));
        }
        return orderEntityModel;
    }
}

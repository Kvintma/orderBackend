package com.kvintma.demo2.controller;


import com.kvintma.demo2.domain.Order;
import com.kvintma.demo2.domain.OrderResponse;
import com.kvintma.demo2.model.OrderDAO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
public class OrderController {

    private final Pattern phone = Pattern.compile(
            "(\\+[0-9]+[\\- \\.]*)?"
                    + "(\\([0-9]+\\)[\\- \\.]*)?"
                    + "([0-9][0-9\\- \\.][0-9\\- \\.]+[0-9])");

    private final Pattern email = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );


    private OrderDAO orderDAO;

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public OrderResponse createOrder(@RequestBody Order orderData) {
        OrderResponse orderResponse = new OrderResponse();

        if (orderData.getName().isEmpty() || orderData.getFirstname().isEmpty()) {
            orderResponse.setCode(400);
            orderResponse.setMessage("Enter your name or firstname!");
            return orderResponse;
        } else if (!phone.matcher(orderData.getContactPhone()).matches()) {
            orderResponse.setCode(400);
            orderResponse.setMessage("Invalide Phone number!");
            return orderResponse;
        } else if (!email.matcher(orderData.geteMail()).matches()) {
            orderResponse.setCode(400);
            orderResponse.setMessage("Invalide mail!");
            return orderResponse;
        } else if (orderData.getAdress().isEmpty()) {
            orderResponse.setCode(400);
            orderResponse.setMessage("Enter your name or adress!");
            return orderResponse;
        } else if (orderData.getStaff().getId() != 0) {
            orderResponse.setCode(400);
            orderResponse.setMessage("Bad request!");
            return orderResponse;
        }

        return orderDAO.createOrder(orderData);
    }

    public OrderController() {
    }

    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }
}

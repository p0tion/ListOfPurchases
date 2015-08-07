package com.antontulskih.controllers;

import com.antontulskih.domain.Customer;
import com.antontulskih.domain.Product;
import com.antontulskih.service.CustomerService;
import com.antontulskih.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

/**
* @author Tulskih Anton
* @{NAME} 21.07.2015
*/

@WebServlet(name = "AddToOrEditShoppingBasket",
            urlPatterns = "/addToOrEditShoppingBasket")
public class AddToOrEditShoppingBasket extends HttpServlet {

    HttpSession session;
    CustomerService customerService = new CustomerService();
    ProductService productService = new ProductService();
    Customer user;
    Customer customer;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        session = req.getSession(false);
        user = customerService.getById((Integer)session.getAttribute("userId"));

        Set<Product> productList = productService.getAllSortedById();
        Integer id = Integer.parseInt(req.getParameter("id"));
        customer = customerService.getById(id);
        req.setAttribute("customer", customer);
        req.setAttribute("user", user);
        req.setAttribute("productList", productList);
        req.getRequestDispatcher("/jsp/addToOrEditShoppingBasket.jsp")
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

package com.antontulskih.controllers;

import com.antontulskih.domain.Customer;
import com.antontulskih.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
* @author Tulskih Anton
* @{NAME} 20.07.2015
*/
@WebServlet(name = "SignIn",
            urlPatterns = "/signIn")
public class SignIn extends HttpServlet {

    HttpSession session;
    String login = "";
    String password = "";
    String errorMessage = "";
    Set<Customer> customerList = Collections.emptySet();
    CustomerService customerService = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ((session = req.getSession()) != null) {
            session.setAttribute("userId", -1);
        }
        session = req.getSession(true);
        req.setAttribute("login", login);
        req.setAttribute("password", password);
        req.setAttribute("errorMessage", errorMessage);
        req.getRequestDispatcher("/jsp/signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        login = req.getParameter("login");
        password = req.getParameter("password");
        customerList = customerService.getAllSortedById();
        for (Customer c: customerList) {
            if (c.getLogin().equals(login) && c.getPassword().equals(password)) {
                login = "";
                password = "";
                errorMessage = "";
                session = req.getSession(true);
                session.setAttribute("userId", c.getId());
                resp.sendRedirect("/tables");
                return;
            }
        }
        errorMessage = "Incorrect login or password";
        this.doGet(req, resp);
    }
}

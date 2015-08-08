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
import java.util.Set;

/**
* @author Tulskih Anton
* @{NAME} 20.07.2015
*/
@WebServlet(name = "SignUp",
            urlPatterns = "/signUp")
public class SignUp extends HttpServlet {

    String firstName = "";
    String lastName = "";
    String cardNumber = "";
    String login = "";
    String password = "";
    String cardNumberError = "";
    String loginError = "";

    private CustomerService customerService = new CustomerService();
    private Set<Customer> customerList;
    private HttpSession session;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        session = req.getSession(true);
        req.setAttribute("loginError", loginError);
        req.setAttribute("cardNumberError", cardNumberError);
        req.setAttribute("firstName", firstName);
        req.setAttribute("lastName", lastName);
        req.setAttribute("cardNumber", cardNumber);
        req.setAttribute("login", login);
        req.setAttribute("password", password);
        req.getRequestDispatcher("/jsp/signUp.jsp").forward(req, resp);
        loginError = "";
        cardNumberError = "";
        firstName = "";
        lastName = "";
        cardNumber = "";
        login = "";
        password = "";
    }

        @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        firstName = req.getParameter("firstName");
        lastName = req.getParameter("lastName");
        cardNumber = req.getParameter("cardNumber");
        login = req.getParameter("login");
        password = req.getParameter("password");
        customerList = customerService.getAllSortedById();
        for (Customer c: customerList) {
            if (c.getLogin().equals(login)) {
                loginError = "login already registered";
                System.out.println(loginError);
                System.out.println(cardNumberError);
            }
            if (c.getCardNumber().equals(cardNumber)) {
                cardNumberError = "card already registered";
                System.out.println(loginError);
                System.out.println(cardNumberError);
            }
        }
        if (cardNumberError != "" || loginError != "") {
            this.doGet(req, resp);
        }
        Customer customer = new Customer(firstName, lastName, cardNumber,
                login, password);
        customerService.save(customer);
        session = req.getSession(true);
        session.setAttribute("userId", customer.getId());
        resp.sendRedirect("/tables");
    }
}

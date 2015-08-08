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
import java.util.HashSet;
import java.util.Set;

/**
* @author Tulskih Anton
* @{NAME} 21.07.2015
*/

@WebServlet(name = "EditCustomers",
            urlPatterns = "/editCustomers")
public class EditCustomers extends HttpServlet {

    HttpSession session;
    CustomerService customerService = new CustomerService();
    Set<Customer> customerList = Collections.emptySet();
    Customer user;
    String hideAdminElementsFromUser;
    String tableLabel;
    String updateLabel;
    String addCustomerButton;
    String deleteCustomersButton;
    String scrollDownOnSubmit = "";
    StringBuilder message = new StringBuilder();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        session = req.getSession(false);
        user = customerService.getById((Integer)session.getAttribute
                ("userId"));
        if (user.getLogin() == null) {
            resp.sendRedirect("/signIn");
            return;
        }
        if (user.getLogin().equals("admin")) {
            customerList = customerService.getAllSortedById();
            hideAdminElementsFromUser = "";
            tableLabel = "Customers Table";
            updateLabel = "Update profiles";
            addCustomerButton = "<input class=\"button\"\n"
                    + "               type=\"button\"\n"
                    + "               title=\"Add a row for another customer\"\n"
                    + "               value=\"Add customer\"\n"
                    + "               onclick=\"appendRow('table');\"/>";

            deleteCustomersButton = "<input class=\"button\"\n"
                    + "               type=\"button\"\n"
                    + "               title=\"Delete checked customers\"\n"
                    + "               value=\"Delete customers\"\n"
                    + "               onclick=\"deleteCustomer('table');\"/>";

        } else {
            customerList = customerService.getByIds(user.getId());
            hideAdminElementsFromUser = "style=\"visibility: hidden\"";
            tableLabel = "Your profile";
            updateLabel = "Update profile";
            addCustomerButton = "";
            deleteCustomersButton = "";
        }

        req.setAttribute("customerList", customerList);
        req.setAttribute("hideFromUser", hideAdminElementsFromUser);
        req.setAttribute("tableLabel", tableLabel);
        req.setAttribute("user", user);
        req.setAttribute("updateLabel", updateLabel);
        req.setAttribute("addCustomerButton", addCustomerButton);
        req.setAttribute("deleteCustomersButton", deleteCustomersButton);
        req.setAttribute("updateMessage", message);
        req.setAttribute("scrollDownOnSubmit", scrollDownOnSubmit);
        req.getRequestDispatcher("/jsp/editCustomers.jsp").forward(req,
                resp);
        message.delete(0, message.length());
        scrollDownOnSubmit = "";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        customerList = customerService.getAllSortedById();
        scrollDownOnSubmit = "window.onload = window.scrollTo(0, document"
                + ".body.scrollHeight);";

        if (!user.getLogin().equals("admin")) {
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String cardNumber = req.getParameter("cardNumber");
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            boolean updateLogin = true;
            boolean updatePassword = true;
            for (Customer c: customerList) {
                if (c.getLogin().equals(login)
                        && !c.getId().equals(user.getId())) {
                    message.append(String.format("<p style=\"color: red\">"
                                    + "Such login already registered: %s</p>%n",
                            login));
                    updateLogin = false;
                }
                if (c.getCardNumber().equals(cardNumber)
                        && !c.getId().equals(user.getId())) {
                    message.append(String.format("<p style=\"color: red\">"
                            + "Such card number already registered: "
                            + "%s</p>%n", cardNumber));
                    updateLogin = false;
                }
            }
            if (updateLogin && updatePassword) {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setCardNumber(cardNumber);
                user.setLogin(login);
                user.setPassword(password);
                customerService.update(user);
                message.append(String.format("<p style=\"color: green\">"
                        + "Your profile successfully updated</p>%n"));
            }
        } else {
            // removing all customers (if "select all"-checkbox checked)
            if (req.getParameter("deleteAll") != null &&
                    req.getParameter("deleteAll").equals("on")) {
                customerService.removeAll();
                message.append(String.format("<p style=\"color: green\">All "
                        + "customers have been removed</p>%n"));
            } else {
                String ids[] = req.getParameterValues("id");
                String firstNames[] = req.getParameterValues("firstName");
                String lastNames[] = req.getParameterValues("lastName");
                String cardNumbers[] = req.getParameterValues("cardNumber");
                String logins[] = req.getParameterValues("login");
                String passwords[] = req.getParameterValues("password");
                for (Customer c: customerList) {
                    for (int i = 0; i < ids.length; i++) {
                        if (c.getLogin().equals(logins[i])
                                && !c.getId().equals(Integer.parseInt(ids[i]))) {
                            message.append(String.format("<p style=\"color: "
                                    + "red\">Login already registered:"
                                    + " %s</p>%n", logins[i]));
                        }
                        if (c.getCardNumber().equals(cardNumbers[i])
                                && !c.getId().equals(Integer.parseInt(ids[i]))) {
                            message.append(String.format("<p style=\"color: "
                                    + "red\">Card number already registered: "
                                    + "%s</p>%n", cardNumbers[i])) ;
                        }
                    }
                    for (int i = ids.length; i < logins.length; i++) {
                        if (c.getLogin().equals(logins[i])) {
                            message.append(String.format("<p style=\"color: "
                                    + "red\">Login already registered:"
                                    + " %s</p>%n", logins[i]));
                        }
                        if (c.getCardNumber().equals(cardNumbers[i])) {
                            message.append(String.format("<p style=\"color: "
                                    + "red\">Card number already registered: "
                                    + "%s</p>%n", cardNumbers[i])) ;
                        }
                    }
                }
                if (message.length() > 0) {
                    this.doGet(req, resp);
                    return;
                }
                Customer customerFromForm;
                if (req.getParameter("id") != null) {
                    Customer customerFromDB;
                    // updating exiting ones in db
                    for (int i = 0; i < ids.length; i++) {
                        customerFromForm = new Customer(firstNames[i], lastNames[i],
                                cardNumbers[i], logins[i], passwords[i]);
                        customerFromForm.setId(Integer.parseInt(ids[i]));
                        customerFromDB = customerService.getById(Integer.parseInt
                                (ids[i]));
                        if (!customerFromDB.getFirstName().equals(
                                customerFromForm.getFirstName())
                                || !customerFromDB.getLastName().equals(
                                customerFromForm.getLastName())
                                || !customerFromDB.getCardNumber().equals(
                                customerFromForm.getCardNumber())
                                || !customerFromDB.getLogin().equals(
                                customerFromForm.getLogin())
                                || !customerFromDB.getPassword().equals(
                                customerFromForm.getPassword())) {
                            customerService.update(customerFromForm);
                            message.append(String.format("<p style=\"color: "
                                            + "green\">%s %s has been "
                                            + "updated</p>%n",
                                            customerFromForm.getFirstName(),
                                            customerFromForm.getLastName()));
                        }
                    }
                    // removing those who were deleted from the table
                    Set<Integer> idsToRemove = new HashSet<Integer>();
                    for (Customer c : customerList) {
                        boolean isToRemove = true;
                        Integer idOfC = c.getId();
                        for (String id : ids) {
                            if (idOfC == Integer.parseInt(id)) {
                                isToRemove = false;
                                break;
                            }
                        }
                        if (isToRemove) {
                            idsToRemove.add(c.getId());
                            message.append(String.format("<p style=\"color: "
                                            + "green\">%s %s has been "
                                            + "removed</p>%n",
                                            c.getFirstName(),
                                            c.getLastName()));
                        }
                    }
                    customerService.removeByIds(idsToRemove.toArray(new
                            Integer[idsToRemove.size()]));
                }
                // saving new customers
                if (ids == null) {
                    ids = new String[0];
                }
                for (int i = ids.length; i < firstNames.length; i++) {
                    customerFromForm = new Customer(firstNames[i], lastNames[i],
                            cardNumbers[i], logins[i], passwords[i]);
                    customerService.save(customerFromForm);
                    message.append(String.format("<p style=\"color: "
                                    + "green\">%s %s has been added</p>%n",
                                    customerFromForm.getFirstName(),
                                    customerFromForm.getLastName()));
                }
            }
        }
        if (message.length() == 0) {
            message.append(String.format("<p style=\"color: "
                            + "green\">All profiles is up to date</p>%n"));
        }
        this.doGet(req, resp);
    }
}

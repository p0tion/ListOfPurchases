package com.antontulskih.controllers;

import com.antontulskih.domain.Customer;
import com.antontulskih.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Tulskih Anton
 * @{NAME} 21.07.2015
 */

@WebServlet("/jsp/editCustomers")
public class EditCustomers extends HttpServlet {

    CustomerService customerService = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        Set<Customer> customerList = customerService.getAllSortedById();

        if (req.getParameterValues("firstName") != null
                || req.getParameter("deleteAll") != null) {
            // removing all customers (if "select all"-checkbox checked)
            if (req.getParameter("deleteAll") != null &&
                    req.getParameter("deleteAll").equals("on")) {
                customerService.removeAll();
            } else {
                String ids[] = req.getParameterValues("id");
                String firstNames[] = req.getParameterValues("firstName");
                String lastNames[] = req.getParameterValues("lastName");
                String cardNumbers[] = req.getParameterValues("cardNumber");
                Customer customerFromForm;
                if (req.getParameter("id") != null) {
                    Customer customerFromDB;
                    // updating exiting ones in db
                    for (int i = 0; i < ids.length; i++) {
                        customerFromForm = new Customer(firstNames[i], lastNames[i],
                                cardNumbers[i]);
                        customerFromForm.setId(Integer.parseInt(ids[i]));
                        customerFromDB = customerService.getById(Integer.parseInt
                                (ids[i]));
                        if (!customerFromDB.getFirstName().equals(
                                customerFromForm.getFirstName())
                                || !customerFromDB.getLastName().equals(
                                customerFromForm.getLastName())
                                || !customerFromDB.getCardNumber().equals(
                                customerFromForm.getCardNumber())) {
                            customerService.update(customerFromForm);
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
                            cardNumbers[i]);
                    customerService.save(customerFromForm);
                }
            }
            req.getRequestDispatcher("/tables").forward(req,resp);
            return;
        }

        req.setAttribute("customerList", customerList);
        req.getRequestDispatcher("/jsp/editCustomers.jsp").forward(req,
                resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}

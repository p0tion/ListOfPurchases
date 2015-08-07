package com.antontulskih.controllers;

import com.antontulskih.domain.Customer;
import com.antontulskih.service.CustomerService;
import com.antontulskih.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

/**
* @author Tulskih Anton
* @{NAME} 21.07.2015
*/

@WebServlet(name = "UpdateCustomer", urlPatterns = "/updateCustomer")
public class UpdateCustomer extends HttpServlet {

    HttpSession session;
    CustomerService customerService = new CustomerService();
    ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        String idToUpdate = req.getParameter("idToUpdate");
        String products = req.getParameter("products");
        System.out.println(req.getQueryString());
        if (idToUpdate != null) {
            Integer customerId = Integer.parseInt(
                    req.getParameter("idToUpdate"));
            Customer customer = customerService.getById(customerId);
            customer.clearShoppingBasket();
            if (!products.equals("none")) {
                String idArray[] = products.replace("[\"","").replace("\"]","")
                        .split("\",\"");
                System.out.println(Arrays.toString(idArray));

                Integer ids[] = new Integer[idArray.length];
                for (int i = 0; i < idArray.length; i++) {
                    ids[i] = (Integer.parseInt(idArray[i]));
                }
                customer.addProductToShoppingBasket(
                        productService.getByIds(ids)
                );
            }
            customerService.update(customer);
        }
//        req.getRequestDispatcher("/tables").forward(req, resp);
        resp.sendRedirect("/tables");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

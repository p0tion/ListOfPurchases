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
* @{NAME} 20.07.2015
*/
@WebServlet(name = "Tables", urlPatterns = "/tables")
public class Tables extends HttpServlet {

    private CustomerService customerService = new CustomerService();
    private ProductService productService = new ProductService();
    Set<Customer> customerList;
    Set<Product> productList;
    HttpSession session;
    String sortCust;
    String sortProd;
    String hideAdminElementsFromUser;
    String tableLabel;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }

        if (session == null) {
            session = req.getSession(true);
            sortCust = "id";
            sortProd = "id";
            session.setAttribute("sortCust", sortCust);
            session.setAttribute("sortProd", sortProd);
        }

        sortCust = (String)session.getAttribute("sortCust");
        sortProd = (String)session.getAttribute("sortProd");

        switch (sortProd) {
            case "id":
                productList = productService.getAllSortedById();
                break;
            case "name":
                productList = productService.getAllSortedByName();
                break;
            case "price":
                productList = productService.getAllSortedByPrice();
                break;
        }
        Customer user = customerService.getById(
                (Integer)session.getAttribute("userId"));

        if (user.getLogin().equals("admin")) {
            switch (sortCust) {
                case "id":
                    customerList = customerService.getAllSortedById();
                    break;
                case "lastName":
                    customerList = customerService.getAllSortedByLastName();
                    break;
                case "invoice":
                    customerList = customerService.getAllSortedByInvoice();
                    break;
            }
            hideAdminElementsFromUser = "";
            tableLabel = "Customers Table";
        } else {
            customerList = customerService.getByIds(user.getId());
            hideAdminElementsFromUser = "style=\"visibility: hidden\"";
            tableLabel = "Your basket";
        }

        req.setAttribute("productList", productList);
        req.setAttribute("customerList", customerList);
        req.setAttribute("user", user);
        req.setAttribute("tableLabel", tableLabel);
        req.setAttribute("hideFromUser", hideAdminElementsFromUser);
        req.getRequestDispatcher("/jsp/tables.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        sortCust = req.getParameter("sortCustomer");
        sortProd = req.getParameter("sortProduct");
        session.setAttribute("sortCust", sortCust);
        session.setAttribute("sortProd", sortProd);
        this.doGet(req, resp);
    }
}

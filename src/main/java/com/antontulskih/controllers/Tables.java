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
@WebServlet("/tables")
public class Tables extends HttpServlet {

    private CustomerService customerService = new CustomerService();
    private ProductService productService = new ProductService();
    Set<Customer> customerList;
    Set<Product> productList;
    HttpSession session;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        String sortCust = "id";
        String sortProd = "id";
        session = req.getSession(false);
        if (session == null) {
            session = req.getSession(true);
            session.setAttribute("sortCust", sortCust);
            session.setAttribute("sortProd", sortProd);
        }
        if ((req.getParameter("sortCust") != null) && (req.getParameter
                ("sortProd") != null)) {
            sortCust = req.getParameter("sortCust");
            sortProd = req.getParameter("sortProd");
            session.setAttribute("sortCust", sortCust);
            session.setAttribute("sortProd", sortProd);
        }
        sortCust = (String)session.getAttribute("sortCust");
        sortProd = (String)session.getAttribute("sortProd");

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
        req.setAttribute("productList", productList);
        req.setAttribute("customerList", customerList);
        req.getRequestDispatcher("/jsp/tables.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

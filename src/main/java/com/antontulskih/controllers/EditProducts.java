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
import java.util.HashSet;
import java.util.Set;

/**
* @author Tulskih Anton
* @{NAME} 21.07.2015
*/

@WebServlet(name = "EditProducts",
            urlPatterns = "/editProducts")
public class EditProducts extends HttpServlet {

    HttpSession session;
    ProductService productService = new ProductService();
    CustomerService customerService = new CustomerService();
    Set<Product> productList;
    StringBuilder message = new StringBuilder();
    Customer user;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        session = req.getSession(false);
        productList = productService.getAllSortedById();
        user = customerService.getById((Integer)session.getAttribute("userId"));

        req.setAttribute("user", user);
        req.setAttribute("productList", productList);
        req.setAttribute("updateMessage", message);
        req.getRequestDispatcher("/jsp/editProducts.jsp").forward(req,
                resp);
        message.delete(0, message.length());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        productList = productService.getAllSortedById();

        // removing all products (if "select all"-checkbox checked)
        if (req.getParameter("deleteAll") != null &&
                req.getParameter("deleteAll").equals("on")) {
            productService.removeAll();
            message.append(String.format("<p style=\"color: green\">"
                    + "All products have been removed</p>%n"));
        } else {
            String ids[] = req.getParameterValues("id");
            String names[] = req.getParameterValues("name");
            String descriptions[] = req.getParameterValues("description");
            String prices[] = req.getParameterValues("price");
            Product productFromForm;
            for (Product p: productList) {
                for (int i = 0; i < ids.length; i++) {
                    if (p.getName().equals(names[i])
                            && !p.getId().equals(Integer.parseInt(ids[i]))) {
                        message.append(String.format("<p style=\"color: "
                                + "red\">Name already registered:"
                                + " %s</p>%n", names[i]));
                    }
                }
                for (int i = ids.length; i < names.length; i++) {
                    if (p.getName().equals(names[i])) {
                        message.append(String.format("<p style=\"color: "
                                + "red\">Name already registered:"
                                + " %s</p>%n", names[i]));
                    }
                }
            }
            if (message.length() > 0) {
                this.doGet(req, resp);
                return;
            }
            if (req.getParameter("id") != null) {

                Product productFromDB;
                // updating exiting ones in db
                for (int i = 0; i < ids.length; i++) {
                    productFromForm = new Product(names[i], descriptions[i],
                            Double.parseDouble(prices[i]));
                    productFromForm.setId(Integer.parseInt(ids[i]));
                    productFromDB = productService.getById(Integer.parseInt
                            (ids[i]));
                    if (!productFromDB.getName().equals(
                            productFromForm.getName())
                            || !productFromDB.getDescription().equals(
                            productFromForm.getDescription())
                            || !productFromDB.getPrice().equals(
                            productFromForm.getPrice())) {
                        productService.update(productFromForm);
                        message.append(String.format("<p style=\"color: green\">"
                                + "%s have been updated</p>%n",
                                productFromForm.getName()));
                    }
                }
                // removing those which were deleted from the table
                Set<Integer> idsToRemove = new HashSet<Integer>();
                for (Product p : productList) {
                    boolean isToRemove = true;
                    Integer idOfP = p.getId();
                    for (String id : ids) {
                        if (idOfP == Integer.parseInt(id)) {
                            isToRemove = false;
                            break;
                        }
                    }
                    if (isToRemove) {
                        idsToRemove.add(p.getId());
                        message.append(String.format("<p style=\"color: green\">"
                                + "%s have been removed</p>%n",
                                p.getName()));
                    }
                }
                productService.removeByIds(idsToRemove.toArray(new
                        Integer[idsToRemove.size()]));
            }
            // saving new products
            if (ids == null) {
                ids = new String[0];
            }
            for (int i = ids.length; i < names.length; i++) {
                productFromForm = new Product(names[i], descriptions[i],
                        Double.parseDouble(prices[i]));
                productService.save(productFromForm);
                message.append(String.format("<p style=\"color: green\">"
                        + "%s have been saved</p>%n", productFromForm.getName
                        ()));
            }
        }
        this.doGet(req, resp);
    }
}

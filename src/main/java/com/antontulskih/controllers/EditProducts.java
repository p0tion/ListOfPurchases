package com.antontulskih.controllers;

import com.antontulskih.domain.Product;
import com.antontulskih.service.ProductService;

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

@WebServlet("/jsp/editProducts")
public class EditProducts extends HttpServlet {

    ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        Set<Product> productList = productService.getAllSortedById();

        if (req.getParameterValues("name") != null
                || req.getParameter("deleteAll") != null) {
            // removing all products (if "select all"-checkbox checked)
            if (req.getParameter("deleteAll") != null &&
                    req.getParameter("deleteAll").equals("on")) {
                productService.removeAll();
            } else {
                String ids[] = req.getParameterValues("id");
                String names[] = req.getParameterValues("name");
                String description[] = req.getParameterValues("description");
                String price[] = req.getParameterValues("price");
                Product productFromForm;
                if (req.getParameter("id") != null) {
                    Product productFromDB;
                    // updating exiting ones in db
                    for (int i = 0; i < ids.length; i++) {
                        productFromForm = new Product(names[i], description[i],
                                Double.parseDouble(price[i]));
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
                    productFromForm = new Product(names[i], description[i],
                            Double.parseDouble(price[i]));
                    productService.save(productFromForm);
                }
            }
            req.getRequestDispatcher("/tables").forward(req,resp);
            return;
        }

        req.setAttribute("productList", productList);
        req.getRequestDispatcher("/jsp/editProducts.jsp").forward(req,
                resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}

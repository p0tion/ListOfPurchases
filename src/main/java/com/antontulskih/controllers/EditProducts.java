package com.antontulskih.controllers;

import com.antontulskih.domain.Customer;
import com.antontulskih.domain.Product;
import com.antontulskih.service.CustomerService;
import com.antontulskih.service.ProductService;
import com.antontulskih.util.MyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Tulskih Anton
 * @{NAME} 27.09.2015
 */
@Controller
public class EditProducts {

    private static final MyLogger LOGGER = new MyLogger(EditProducts.class);
    private static final String NAME_DUPLICATE_MSG ="Such name already exists:";
    private static final String CSS_STYLE_RED = "style=\"color: red\"";
    private static final String CSS_STYLE_GREEN = "style=\"color: green\"";

    private Set<Product> productList = Collections.emptySet();
    private Customer user;
    private String scrollDownOnSubmit = "";
    private StringBuilder message = new StringBuilder();


    @Autowired
    ProductService productService;

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "editProducts")
    public String showListOfProducts(HttpSession session, Model model) {
        LOGGER.trace("Inside showListOfProducts()");

        if (session.getAttribute("userId") == null) {

            LOGGER.trace("User ID from session equals null. Redirecting to "
                    + "/signin");

            return "redirect:/signIn";
        }

        productList = productService.getAllSortedById();
        user = customerService.getById((Integer)session.getAttribute("userId"));

        model.addAttribute("user", user);
        model.addAttribute("scrollDownOnSubmit", scrollDownOnSubmit);
        model.addAttribute("productList", productList);

        return "editProducts";
    }

    @RequestMapping(value = "editProducts", method = RequestMethod.POST)
    public String updateProducts(HttpServletRequest req, Model model) {
        LOGGER.trace("Inside updateProducts()");

        if (message.length() > 0) {
            message.delete(0, message.length());
        }

        scrollDownOnSubmit = "window.onload = window.scrollTo(0, document"
                + ".body.scrollHeight);";
        productList = productService.getAllSortedById();

        // removing all products (if "select all"-checkbox checked)
        if (req.getParameter("deleteAll") != null &&
                req.getParameter("deleteAll").equals("on")) {
            productService.removeAll();
            message.append(String.format("<p %s>All products has been removed"
                    + "</p>%n", CSS_STYLE_GREEN));
        } else {
            String[] ids = req.getParameterValues("id");
            String[] names = req.getParameterValues("name");
            String[] descriptions = req.getParameterValues("description");
            String[] prices = req.getParameterValues("price");
            LOGGER.debug(String.format("%n"
                    + "ids: %s%n"
                    + "names: %s%n"
                    + "descriptions: %s%n"
                    + "prices: %s",
                    Arrays.toString(ids),
                    Arrays.toString(names),
                    Arrays.toString(descriptions),
                    Arrays.toString(prices)
                    ));
            Product productFromForm;
            for (Product p: productList) {
                for (int i = 0; i < ids.length; i++) {
                    if (p.getName().equals(names[i])
                            && !p.getId().equals(Integer.parseInt(ids[i]))) {
                        message.append(String.format("<p %s>%s %s</p>%n",
                                CSS_STYLE_RED, NAME_DUPLICATE_MSG, names[i]));
                    }
                }
                for (int i = ids.length; i < names.length; i++) {
                    if (p.getName().equals(names[i])) {
                        message.append(String.format("<p %s>%s %s</p>%n",
                                CSS_STYLE_RED, NAME_DUPLICATE_MSG, names[i]));
                    }
                }
            }
            if (message.length() > 0) {
                productList = productService.getAllSortedById();
                model.addAttribute("user", user);
                model.addAttribute("scrollDownOnSubmit", scrollDownOnSubmit);
                model.addAttribute("productList", productList);
                model.addAttribute("updateMessage", message);
                return "editProducts";
            }
            if (req.getParameter("id") != null) {

                Product productFromDB;
                // updating exiting ones in db
                for (int i = 0; i < ids.length; i++) {
                    productFromForm = new Product(
                            names[i],
                            descriptions[i],
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
                        message.append(String.format("<p %s>%s has been "
                                        + "updated</p>%n", CSS_STYLE_GREEN,
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
                        message.append(String.format("<p %s>%s has been "
                                        + "removed</p>%n", CSS_STYLE_GREEN,
                                        p.getName()));
                    }
                }
                productService.removeByIds(idsToRemove.toArray(
                        new Integer[idsToRemove.size()]));
            }
            // saving new products
            if (ids == null) {
                ids = new String[0];
            }
            for (int i = ids.length; i < names.length; i++) {
                productFromForm = new Product(names[i], descriptions[i],
                        Double.parseDouble(prices[i]));
                productService.save(productFromForm);
                message.append(String.format("<p %s>%s has been saved</p>%n",
                        CSS_STYLE_GREEN, productFromForm.getName()));
            }
        }
        if (message.length() == 0) {
            message.append(String.format("<p %s>All products are up to "
                    + "date</p>%n", CSS_STYLE_GREEN));
        }
        productList = productService.getAllSortedById();
        model.addAttribute("user", user);
        model.addAttribute("scrollDownOnSubmit", scrollDownOnSubmit);
        model.addAttribute("productList", productList);
        model.addAttribute("updateMessage", message);
        return "editProducts";
    }
}

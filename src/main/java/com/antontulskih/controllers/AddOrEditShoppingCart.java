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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Set;

/**
 * @author Tulskih Anton
 * @{NAME} 28.09.2015
 */
@Controller
public class AddOrEditShoppingCart {

    private static final MyLogger LOGGER =
            new MyLogger(AddOrEditShoppingCart.class);

    private Customer user;
    private Customer customer;
    private String pageTitle = "";

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @RequestMapping(value = {"addToShoppingCart", "editShoppingCart"})
    public String showShoppingCart(HttpSession session, Model model,
                                   @RequestParam("id") Integer id,
                                   HttpServletRequest req) {
        LOGGER.trace("Inside showShoppingCart()");

        if (session.getAttribute("userId") == null) {

            LOGGER.trace("User ID from session equals null. Redirecting to "
                    + "/signin");

            return "redirect:/signIn";
        }
        String url = req.getServletPath();
        LOGGER.debug("url is " + url);
        if (url.equals("/editShoppingCart")) {
            pageTitle = "edit";
        } else {
            pageTitle = "add";
        }

        Set<Product> productList = productService.getAllSortedById();
        user = customerService.getById((Integer)session.getAttribute("userId"));
        customer = customerService.getById(id);
        model.addAttribute("pageTtl", pageTitle);
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        model.addAttribute("productList", productList);

        if (user.getId().equals(customer.getId())
                || user.getLogin().equals("admin")) {
            return "addToOrEditShoppingCart";
        } else {
            return "redirect:tables";
        }
    }

    @RequestMapping(value = "updateCustomer")
    public String updateShoppingCart(HttpSession session,
                                   @RequestParam("idToUpdate") Integer id,
                                   @RequestParam("products") String products) {
        LOGGER.trace("Inside updateShoppingCart()");
        LOGGER.debug(String.format("idToUpdate %s%n"
                                 + "products %s",
                                 id, products));
        user = customerService.getById((Integer)session.getAttribute("userId"));
        customer = customerService.getById(id);
        if (Objects.equals(customer.getId(), user.getId())
                || user.getLogin().equals("admin")) {
            customer.clearShoppingBasket();
            if (!products.equals("none")) {
                String idArray[] = products.replace("[\"","").replace("\"]","")
                        .split("\",\"");

                Integer ids[] = new Integer[idArray.length];
                for (int i = 0; i < idArray.length; i++) {
                    ids[i] = (Integer.parseInt(idArray[i]));
                }
                customer.addProductToShoppingBasket(productService.getByIds(ids));
            }
            customerService.update(customer);
        }
        return "redirect:tables";
    }
}

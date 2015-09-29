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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Set;

import static java.lang.String.format;

/**
 * @author Tulskih Anton
 * @{NAME} 13.08.2015
 */
@Controller
public class TablesController {

    static final MyLogger LOGGER = new MyLogger(TablesController.class);
    private static String userRole;

    private Customer user;
    private Set<Customer> customerList;
    private Set<Product> productList;
    private String sortCust;
    private String sortProd;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "tables")
    public String showTablesPage(Model model, HttpSession session) {

        LOGGER.trace("Inside showTablesPage()");

        if (session.getAttribute("userId") == null) {

            LOGGER.trace("User ID from session equals null. Redirecting to "
                    + "/signin");

            return "redirect:/signIn";
        }

        LOGGER.debug(String.format("%nsortCust from session is %s%n"
                + "sortProd from session is %s",
                session.getAttribute("sortCust"),
                session.getAttribute("sortProd")));

        user = customerService.getById((Integer)session.getAttribute("userId"));
        LOGGER.debug(format("User ID from session is %d", user.getId()));
        userRole = user.getLogin();
        LOGGER.debug(format("User role is %s", userRole));

        if (session.getAttribute("sortCust") == null ||
                session.getAttribute("sortProd") == null) {

            LOGGER.debug("sortCust from session is null, sortProd "
                    + "from session is null, will assign \"ID\" values "
                    + "automatically");

            sortCust = "id";
            sortProd = "id";
            session.setAttribute("sortCust", sortCust);
            session.setAttribute("sortProd", sortProd);
        }

        LOGGER.debug(String.format("%nsortCust from session is %s%n"
                        + "sortProd from session is %s",
                session.getAttribute("sortCust"),
                session.getAttribute("sortProd")));

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

        if (userRole.equals("admin")) {

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

            model.addAttribute("hideFromUser", "");
            model.addAttribute("tableLabel", "Customers Table");

        } else {

            customerList = customerService.getByIds(user.getId());
            model.addAttribute("hideFromUser", "style=\"visibility: hidden\"");
            model.addAttribute("tableLabel", "Your Cart");

        }

        model.addAttribute("sortCust", sortCust);
        model.addAttribute("sortProd", sortProd);
        model.addAttribute("productList", productList);
        model.addAttribute("customerList", customerList);
        model.addAttribute("user", user);

        return "tables";
    }

    @RequestMapping(value = "tables", method = RequestMethod.POST)
    public String sortTables(@RequestParam String sortCustomer,
                             @RequestParam String sortProduct,
                             HttpSession session) {

        if (session.getAttribute("userId") == null) {

            LOGGER.trace("User ID from session equals null. Redirecting to "
                    + "/signin");

            return "redirect:/signIn";
        }

        LOGGER.trace("Inside sortTables()");
        LOGGER.debug(format("%nsortCustomer - %s%nsortProduct - %s",
                            sortCustomer, sortProduct));
        sortCust = sortCustomer;
        sortProd = sortProduct;

        session.setAttribute("sortCust", sortCust);
        session.setAttribute("sortProd", sortProd);

        LOGGER.debug(String.format("%nsortCust from session is %s%n"
                        + "sortProd from session is %s",
                session.getAttribute("sortCust"),
                session.getAttribute("sortProd")));

        return "redirect:/tables";
    }
}

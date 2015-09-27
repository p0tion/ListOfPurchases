package com.antontulskih.controllers;

import com.antontulskih.domain.Customer;
import com.antontulskih.service.CustomerService;
import com.antontulskih.util.MyLogger;
import com.antontulskih.util.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Tulskih Anton
 * @{NAME} 24.09.2015
 */
@Controller
public class EditCustomers {

    private static final MyLogger LOGGER = new MyLogger(EditCustomers.class);
    private static String userRole;
    private static final PasswordEncryptor PASSWORD_ENCRYPTOR =
            new PasswordEncryptor();
    private static final String LOGIN_DUPLICATE_MSG = "Such login already "
            + "registered:";
    private static final String CARD_DUPLICATE_MSG = "Such card number already "
            + "registered:";
    private static final String CSS_STYLE_RED = "style=\"color: red\"";
    private static final String CSS_STYLE_GREEN = "style=\"color: green\"";

    private Customer user;
    private Set<Customer> customerList = Collections.emptySet();
    private String hideAdminElementsFromUser = "";
    private String tableLabel = "";
    private String updateLabel = "";
    private String scrollDownOnSubmit = "";
    private StringBuilder message = new StringBuilder();

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "editCustomers")
    public String showListOfCustomers(HttpSession session, Model model) {
        LOGGER.trace("Inside showListOfCustomers()");

        if (session.getAttribute("userId") == null) {

            LOGGER.trace("User ID from session equals null. Redirecting to "
                    + "/signin");

            return "redirect:/signIn";
        }

        user = customerService.getById((Integer)session.getAttribute("userId"));
        LOGGER.debug("userId is " + session.getAttribute("userId"));
        userRole = user.getLogin();
        LOGGER.debug("userRole is " + user.getLogin());

        if (userRole.equals("admin")) {
            LOGGER.trace("userRole is admin, define set of customers etc.");
            customerList = customerService.getAllSortedById();
            hideAdminElementsFromUser = "";
            tableLabel = "Customers Profiles";
            updateLabel = "Update profiles";
        } else {
            LOGGER.trace("userRole is not admin, define one customer, hide "
                    + "admin elements etc.");
            customerList = customerService.getByIds(user.getId());
            hideAdminElementsFromUser = "nonVisible";
            tableLabel = "Your Profile";
            updateLabel = "Update profile";
        }

        model.addAttribute("customerList", customerList);
        model.addAttribute("hideFromUser", hideAdminElementsFromUser);
        model.addAttribute("tableLabel", tableLabel);
        model.addAttribute("user", user);
        model.addAttribute("updateLabel", updateLabel);
        model.addAttribute("scrollDownOnSubmit", scrollDownOnSubmit);

        return "editCustomers";
    }

    @RequestMapping(value = "editCustomers", method = RequestMethod.POST)
    public String updateCustomers(HttpServletRequest req, Model model) {

        LOGGER.trace("Inside updateCustomers()");

        if (message.length() > 0) {
            message.delete(0, message.length());
        }

        if (!user.getLogin().equals("admin")) {
            LOGGER.trace("User is not admin");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String cardNumber = req.getParameter("cardNumber");
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            String cryptedPassword =
                    PASSWORD_ENCRYPTOR.getCryptString(password);
            LOGGER.debug(String.format("%n"
                            + "firstName is %s%n"
                            + "lastName is %s%n"
                            + "cardNumber is %s%n"
                            + "login is %s%n"
                            + "password is %s", firstName, lastName, cardNumber,
                            login, password));
            boolean updateLogin = true;
            boolean updateCardNumber = true;
            for (Customer c: customerList) {
                if (c.getLogin().equals(login)
                        && !c.getId().equals(user.getId())) {
                    LOGGER.trace("will produce \"such login already "
                                    + "registered\" message");
                    message.append(String.format("<p %s>%s %s</p>%n",
                            CSS_STYLE_RED, LOGIN_DUPLICATE_MSG, login));
                    updateLogin = false;
                }
                if (c.getCardNumber().equals(cardNumber)
                        && !c.getId().equals(user.getId())) {
                    LOGGER.trace("will produce \"such card number already "
                            + "registered\" message");
                    message.append(String.format("<p %s>%s %s</p>%n",
                            CSS_STYLE_RED, CARD_DUPLICATE_MSG, cardNumber));
                    updateCardNumber = false;
                }
            }
            if (updateLogin && updateCardNumber) {
                LOGGER.trace("will update user");
                if (!user.getFirstName().equals(firstName)) {
                    user.setFirstName(firstName);
                    message.append(String.format("<p %s>"
                                    + "Your first name has been changed to "
                                    + "%s</p>%n",
                            CSS_STYLE_GREEN, firstName));
                }
                if (!user.getLastName().equals(lastName)) {
                    user.setLastName(lastName);
                    message.append(String.format("<p %s>"
                                    + "Your last name has been  changed to "
                                    + "%s</p>%n",
                            CSS_STYLE_GREEN, lastName));
                }
                if (!user.getCardNumber().equals(cardNumber)) {
                    user.setCardNumber(cardNumber);
                    message.append(String.format("<p %s>"
                                    + "Your card number has been changed to "
                                    + "%s</p>%n",
                            CSS_STYLE_GREEN, cardNumber));
                }
                if (!user.getLogin().equals(login)) {
                    user.setLogin(login);
                    message.append(String.format("<p %s>"
                                    + "Your login has been changed to "
                                    + "%s</p>%n",
                            CSS_STYLE_GREEN, login));
                }
                if (!user.getPassword().equals(cryptedPassword)
                        && !password.equals("")) {
                    user.setPassword(cryptedPassword);
                    message.append(String.format("<p %s>"
                                    + "Your password has been changed</p>%n",
                            CSS_STYLE_GREEN));
                }
                if (message.length() == 0) {
                    message.append(String.format("<p %s>"
                            + "Your profile is up to date</p>%n",
                            CSS_STYLE_GREEN));
                } else {
                    customerService.update(user);
                }
            }
            hideAdminElementsFromUser = "nonVisible";
            tableLabel = "Your Profile";
            updateLabel = "Update profile";
            customerList = customerService.getByIds(user.getId());
            model.addAttribute("customerList", customerList);
            model.addAttribute("hideFromUser", hideAdminElementsFromUser);
            model.addAttribute("tableLabel", tableLabel);
            model.addAttribute("user", user);
            model.addAttribute("updateLabel", updateLabel);
            model.addAttribute("updateMessage", message);
            return "editCustomers";
        } else {
            // removing all customers (if "select all"-checkbox checked)
            if (req.getParameter("deleteAll") != null &&
                    req.getParameter("deleteAll").equals("on")) {
                customerService.removeAll();
                message.append(String.format("<p %s>All customers have been "
                        + "removed</p>%n", CSS_STYLE_GREEN));
            } else {
                customerList = customerService.getAllSortedById();
                scrollDownOnSubmit = "window.onload = window.scrollTo(0, "
                        + "document.body.scrollHeight);";
                String[] ids = req.getParameterValues("id");
                String[] firstNames = req.getParameterValues("firstName");
                String[] lastNames = req.getParameterValues("lastName");
                String[] cardNumbers = req.getParameterValues("cardNumber");
                String[] logins = req.getParameterValues("login");
                String[] passwords = req.getParameterValues("password");
                for (Customer c: customerList) {
                    for (int i = 0; i < ids.length; i++) {
                        if (c.getLogin().equals(logins[i])
                                && !c.getId().equals(Integer.parseInt(ids[i]))) {
                            message.append(String.format("<p %s>%s %s</p>%n",
                                    CSS_STYLE_RED, LOGIN_DUPLICATE_MSG,
                                    logins[i]));
                        }
                        if (c.getCardNumber().equals(cardNumbers[i])
                                && !c.getId().equals(Integer.parseInt(ids[i]))) {
                            message.append(String.format("<p %s>%s %s</p>%n",
                                    CSS_STYLE_RED, CARD_DUPLICATE_MSG,
                                    cardNumbers[i])) ;
                        }
                    }
                    for (int i = ids.length; i < logins.length; i++) {
                        if (c.getLogin().equals(logins[i])) {
                            message.append(String.format("<p %s>%s %s</p>%n",
                                    CSS_STYLE_RED, LOGIN_DUPLICATE_MSG,
                                    logins[i]));
                        }
                        if (c.getCardNumber().equals(cardNumbers[i])) {
                            message.append(String.format("<p %s>%s %s</p>%n",
                                    CSS_STYLE_RED, CARD_DUPLICATE_MSG,
                                    cardNumbers[i])) ;
                        }
                    }
                }
                if (message.length() > 0) {
                    customerList = customerService.getAllSortedById();
                    hideAdminElementsFromUser = "";
                    tableLabel = "Customers Profiles";
                    updateLabel = "Update profiles";
                    model.addAttribute("customerList", customerList);
                    model.addAttribute("hideFromUser", hideAdminElementsFromUser);
                    model.addAttribute("tableLabel", tableLabel);
                    model.addAttribute("user", user);
                    model.addAttribute("updateLabel", updateLabel);
                    model.addAttribute("updateMessage", message);
                    model.addAttribute("scrollDownOnSubmit", scrollDownOnSubmit);
                    return "editCustomers";
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
                                || (!PASSWORD_ENCRYPTOR.getCryptString(
                                customerFromForm.getPassword()).equals(
                                customerFromDB.getPassword())
                                && !customerFromForm.getPassword().equals("")))
                        {
                            if (customerFromForm.getPassword().equals("")) {
                                customerFromForm.setPassword(
                                        customerFromDB.getPassword());
                            } else {
                                customerFromForm.setPassword(
                                        PASSWORD_ENCRYPTOR.getCryptString(
                                        customerFromForm.getPassword()));
                            }
                            customerService.update(customerFromForm);
                            message.append(String.format("<p %s>%s %s has been "
                                            + "updated</p>%n",
                                    CSS_STYLE_GREEN,
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
                            message.append(String.format("<p %s>%s %s has been "
                                            + "removed</p>%n",
                                    CSS_STYLE_GREEN,
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
                    message.append(String.format("<p %s>%s %s has been "
                                    + "added</p>%n",
                            CSS_STYLE_GREEN,
                            customerFromForm.getFirstName(),
                            customerFromForm.getLastName()));
                }
            }
        }
        if (message.length() == 0) {
            message.append(String.format("<p %s>All profiles are up to "
                    + "date</p>%n", CSS_STYLE_GREEN));
        }
        customerList = customerService.getAllSortedById();
        hideAdminElementsFromUser = "";
        tableLabel = "Customers Profiles";
        updateLabel = "Update profiles";
        model.addAttribute("customerList", customerList);
        model.addAttribute("hideFromUser", hideAdminElementsFromUser);
        model.addAttribute("tableLabel", tableLabel);
        model.addAttribute("user", user);
        model.addAttribute("updateLabel", updateLabel);
        model.addAttribute("updateMessage", message);
        model.addAttribute("scrollDownOnSubmit", scrollDownOnSubmit);
        return "editCustomers";
    }
}

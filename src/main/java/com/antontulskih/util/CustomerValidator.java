package com.antontulskih.util;


import com.antontulskih.domain.Customer;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tulskih Anton
 * @{NAME} 11.08.2015
 */
public class CustomerValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Customer.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer user = (Customer) target;

        Pattern pattern = Pattern.compile("[A-Za-z]{2,15}");
        Matcher matcher = pattern.matcher(user.getFirstName());
        if (user.getFirstName().trim().equals("")) {
            errors.rejectValue("firstName", "validator.empty.firstName");
        } else if (!matcher.matches()) {
            errors.rejectValue("firstName", "validator.pattern.firstName");
        }

        pattern = Pattern.compile("[A-Za-z]{2,15}");
        matcher = pattern.matcher(user.getLastName());
        if (user.getLastName().trim().equals("")) {
            errors.rejectValue("lastName", "validator.empty.lastName");
        } else if (!matcher.matches()) {
            errors.rejectValue("lastName", "validator.pattern.lastName");
        }

        pattern = Pattern.compile("[0-9]{16}");
        matcher = pattern.matcher(user.getCardNumber());
        if (user.getCardNumber().trim().equals("")) {
            errors.rejectValue("cardNumber", "validator.empty.cardNumber");
        } else if (!matcher.matches()) {
            errors.rejectValue("cardNumber", "validator.pattern.cardNumber");
        }

        pattern = Pattern.compile("[A-Za-z0-9_]{2,15}");
        matcher = pattern.matcher(user.getLogin());
        if (user.getLogin().trim().equals("")) {
            errors.rejectValue("login", "validator.empty.login");
        } else if (!matcher.matches()) {
            errors.rejectValue("login", "validator.pattern.login");
        }

        pattern = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}");
        matcher = pattern.matcher(user.getPassword());
        if (user.getPassword().trim().equals("")) {
            errors.rejectValue("password", "validator.empty.password");
        } else if (!matcher.matches()) {
            errors.rejectValue("password", "validator.pattern.password");
        }

    }
}

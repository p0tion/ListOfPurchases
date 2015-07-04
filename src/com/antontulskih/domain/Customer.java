/**
 * @{NAME}
 *
 * ${DATE}
 *
 * @author Tulskih Anton
 */

package com.antontulskih.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Customer implements Serializable {

    private static final double ONE_HUNDRED_PERCENT = 100.0;
    private static Long uniqueId = 0L;
    private Long id;
    private String customerFirstName;
    private String customerLastName;
    private String customerCellphoneNumber;
    private String customerCardNumber;
    private String customerEmail;
    private Integer discount;
    private Double invoice;
    private List<Product> shoppingBasket;

    public Customer() { }

    public Customer(final String customerFirstName,
                    final String customerLastName,
                    final String customerCellphoneNumber,
                    final String customerCardNumber,
                    final String customerEmail,
                    final Integer discount) {
        super();
        this.id = ++uniqueId;
        setCustomerFirstName(customerFirstName);
        setCustomerLastName(customerLastName);
        setCustomerCellphoneNumber(customerCellphoneNumber);
        setCustomerCardNumber(customerCardNumber);
        setCustomerEmail(customerEmail);
        setDiscount(discount);
        this.shoppingBasket = new ArrayList<Product>();
        this.invoice = 0.0;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(final String customerFirstName) {
        if ((customerFirstName == null) || (customerFirstName.equals(""))) {
            throw new IllegalArgumentException("Customers first name is"
                    + " invalid.");
        } else {
            this.customerFirstName = customerFirstName.trim();
        }
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(final String customerLastName) {
        if ((customerLastName == null) || (customerLastName.equals(""))) {
            throw new IllegalArgumentException("Customers last name is"
                    + " invalid.");
        } else {
            this.customerLastName = customerLastName.trim();
        }
    }

    public String getCustomerCellphoneNumber() {
        return customerCellphoneNumber;
    }

    public void setCustomerCellphoneNumber(
            final String customerCellphoneNumber) {
        String validationExpression = "\\d+";
        Pattern pattern = Pattern.compile(validationExpression);
        Matcher matcher = pattern.matcher(customerCellphoneNumber);
        if ((customerCellphoneNumber == null)
                || (customerCellphoneNumber.equals(""))
                || (!matcher.matches())) {
            throw new IllegalArgumentException("Customers cellphone number is"
                    + " invalid.");
        } else {
            this.customerCellphoneNumber = customerCellphoneNumber.trim();
        }
    }

    public String getCustomerCardNumber() {
        return customerCardNumber;
    }

    public void setCustomerCardNumber(final String customerCardNumber) {
        String validationExpression = "\\d+";
        Pattern pattern = Pattern.compile(validationExpression);
        Matcher matcher = pattern.matcher(customerCardNumber);
        if ((customerCardNumber == null)
                || (customerCardNumber.equals(""))
                || (!matcher.matches())) {
            throw new IllegalArgumentException("Customers card number is"
                    + " invalid.");
        } else {
            this.customerCardNumber = customerCardNumber.trim();
        }
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(final String customerEmail) {
        String validationExpression = "^.+@.+\\..+$";
        Pattern pattern = Pattern.compile(validationExpression);
        Matcher matcher = pattern.matcher(customerEmail);
        if ((customerEmail == null)
                || (customerEmail.equals(""))
                || (!matcher.matches())) {
            throw new IllegalArgumentException("Customers email is invalid.");
        } else {
            this.customerEmail = customerEmail.trim();
        }
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(final Integer discount) {
        final int minDiscount = 0;
        final int maxDiscount = 100;
        if ((discount == null)
                || (discount < minDiscount)
                || (discount > maxDiscount)) {
            throw new IllegalArgumentException("Customers discount is"
                    + " invalid.");
        } else {
            this.discount = discount;
        }
    }

    public void addProductToShoppingBasket(final Product... p) {
        System.out.println("\n***");
        System.out.println(this.getCustomerFirstName()
                + " "
                + this.getCustomerLastName()
                + " added to their purchase basket:");

        for (Product s: p) {
            shoppingBasket.add(s);
            invoice += s.getProductPrice();
            System.out.println("- " + s.getProductName());
        }
        System.out.println("***");
        invoice = invoice * ((ONE_HUNDRED_PERCENT - discount) / 100.0);
    }

    public void showShoppingBasket() {
        for (Product p: shoppingBasket) {
            System.out.print(p);
        }
    }

    public Double getInvoice() {
        return invoice;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }

        Customer customer = (Customer) o;

        if (!customerCardNumber.equals(customer.customerCardNumber)) {
            return false;
        }
        if (!customerFirstName.equals(customer.customerFirstName)) {
            return false;
        }
        if (!customerLastName.equals(customer.customerLastName)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = customerFirstName.hashCode();
        result = 31 * result + customerLastName.hashCode();
        result = 31 * result + customerCardNumber.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("%d  %s %s  %s  %s  %d",
                id,
                customerFirstName,
                customerLastName,
                customerCellphoneNumber,
                customerEmail,
                discount);
    }
}

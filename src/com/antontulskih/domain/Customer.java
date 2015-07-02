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
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerCellphoneNumber = customerCellphoneNumber;
        this.customerCardNumber = customerCardNumber;
        this.customerEmail = customerEmail;
        this.discount = discount;
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
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(final String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerCellphoneNumber() {
        return customerCellphoneNumber;
    }

    public void setCustomerCellphoneNumber(
            final String customerCellphoneNumber) {
        this.customerCellphoneNumber = customerCellphoneNumber;
    }

    public String getCustomerCardNumber() {
        return customerCardNumber;
    }

    public void setCustomerCardNumber(final String customerCardNumber) {
        this.customerCardNumber = customerCardNumber;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(final String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(final Integer discount) {
        this.discount = discount;
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

    public void setInvoice(final Double invoice) {
        this.invoice = invoice;
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

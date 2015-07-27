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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.antontulskih.util.ProductFormattedTable.*;
import static java.lang.System.out;

public final class Customer implements Serializable {

    private Integer id;
    private String firstName;
    private String lastName;
    private String cardNumber;
    private Integer quantity;
    private Double invoice;
    private List<Product> shoppingBasket;

    public Customer() { this.shoppingBasket = new ArrayList<Product>(); }

    public Customer(final String firstName,
                    final String lastName,
                    final String cardNumber) {
        this();
        setFirstName(firstName);
        setLastName(lastName);
        setCardNumber(cardNumber);
        setInvoice(0.0);
        setQuantity(0);

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if ((id == null) || (id <= 0)) {
            throw new IllegalArgumentException("Invalid ID value. Must be " +
                    "greater than 0.");
        } else {
            this.id = id;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        if ((firstName == null) || (firstName.equals(""))) {
            throw new IllegalArgumentException("Customers first name is"
                    + " invalid.");
        } else {
            this.firstName = firstName.trim();
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        if ((lastName == null) || (lastName.equals(""))) {
            throw new IllegalArgumentException("Customers last name is "
                    + "invalid.");
        } else {
            this.lastName = lastName.trim();
        }
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(final String cardNumber) {
        if (cardNumber == null) {
            throw new IllegalArgumentException("Customers card number is"
                    + " invalid.");
        }
        String validationExpression = "\\d+";
        Pattern pattern = Pattern.compile(validationExpression);
        Matcher matcher = pattern.matcher(cardNumber);
        if ((cardNumber.equals("")) || (!matcher.matches())) {
            throw new IllegalArgumentException("Customers card number is"
                    + " invalid.");
        } else {
            this.cardNumber = cardNumber.trim();
        }
    }

    public void addProductToShoppingBasket(final Set<Product> set) {
        addProductToShoppingBasket(set.toArray(new Product[set.size()]));
    }

    public void addProductToShoppingBasket(final Product... products) {
        out.println("\n***");
        out.println(this.getFirstName()
                + " "
                + this.getLastName()
                + " added to their purchase basket:");
        for (Product p: products) {
            this.shoppingBasket.add(p);
            invoice += p.getPrice();
            invoice = (double)(Math.round(invoice * 100)) / 100;
            out.println("- " + p.getName());
        }
        quantity = this.shoppingBasket.size();
        out.println("***");
    }

    public void showShoppingBasket() {
        out.println("\n*** Displaying shopping basket of "
                + this.getFirstName() + " " + this.getLastName() + " ***");
        printLine();
        printHeader();
        printLine();
        for (Product p: shoppingBasket) {
            printProduct(p);
        }
        printLine();
    }

    public Double getInvoice() {
        return this.invoice;
    }

    public void setInvoice(final Double invoice) {
        this.invoice = invoice;
    }

    public List<Product> getShoppingBasket() {
        return shoppingBasket;
    }

    public void setShoppingBasket(final List<Product> shoppingBasket) {
        this.shoppingBasket.clear();
        this.shoppingBasket.addAll(shoppingBasket);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public void clearShoppingBasket() {
        out.println("*** Clearing shopping basket of "
                + this.getFirstName() + " " + this.getLastName() + " ***");
        this.invoice = 0.0;
        this.quantity = 0;
        shoppingBasket.clear();
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

        if (!cardNumber.equals(customer.cardNumber)) {
            return false;
        }
        if (!firstName.equals(customer.firstName)) {
            return false;
        }
        if (!lastName.equals(customer.lastName)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + cardNumber.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("%d  %s %s",
                id,
                firstName,
                lastName
        );
    }
}
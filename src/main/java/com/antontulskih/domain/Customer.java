/**
 * @{NAME}
 *
 * ${DATE}
 *
 * @author Tulskih Anton
 */

package com.antontulskih.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.antontulskih.util.ProductFormattedTable.*;
import static java.lang.System.out;

@Entity
public final class Customer implements Serializable {

    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private String cardNumber;
    private Integer quantity;
    private Double invoice;
    private String login;
    private String password;
    @Transient
    private List<Product> shoppingBasket;

    public Customer() { this.shoppingBasket = new ArrayList<Product>(); }

    public Customer(final String firstName,
                    final String lastName,
                    final String cardNumber,
                    final String login,
                    final String password) {
        this();
        setFirstName(firstName);
        setLastName(lastName);
        setCardNumber(cardNumber);
        setLogin(login);
        setPassword(password);
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
        out.printf("%n%n*** %s %s added to their purchase basket:%n",
                this.getFirstName(), this.getLastName());
        for (Product p: products) {
            this.shoppingBasket.add(p);
            invoice += p.getPrice();
            out.println("- " + p.getName());
        }
        quantity = this.shoppingBasket.size();
        out.println("***");
    }

    public void showShoppingBasket() {
        out.printf("%n*** Displaying shopping basket of %s %s ***%n",
                this.getFirstName(), this.getLastName());
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
        out.printf("%n*** Clearing shopping basket of %s %s ***",
                this.getFirstName(), this.getLastName());
        this.invoice = 0.0;
        this.quantity = 0;
        shoppingBasket.clear();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        if (login == null) {
            throw new IllegalArgumentException("Customers login is "
                    + "invalid.");
        }
        String validationExpression = "[A-Za-z0-9_]{2,15}";
        Pattern pattern = Pattern.compile(validationExpression);
        Matcher matcher = pattern.matcher(login);
        if ((login.equals("")) || (!matcher.matches())) {
            throw new IllegalArgumentException("Customers login is "
                    + "invalid.");
        } else {
            this.login = login.trim();
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password is invalid.");
        }
        String validationExpression = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}";
        Pattern pattern = Pattern.compile(validationExpression);
        Matcher matcher = pattern.matcher(password);
        if ((password.equals("")) || (!matcher.matches())) {
            throw new IllegalArgumentException("Password is invalid.");
        } else {
            this.password = password.trim();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (!cardNumber.equals(customer.cardNumber)) return false;
        if (!firstName.equals(customer.firstName)) return false;
        if (!id.equals(customer.id)) return false;
        if (!invoice.equals(customer.invoice)) return false;
        if (!lastName.equals(customer.lastName)) return false;
        if (!login.equals(customer.login)) return false;
        if (!password.equals(customer.password)) return false;
        if (!quantity.equals(customer.quantity)) return false;
        if (!shoppingBasket.equals(customer.shoppingBasket)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + cardNumber.hashCode();
        result = 31 * result + quantity.hashCode();
        result = 31 * result + invoice.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + shoppingBasket.hashCode();
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
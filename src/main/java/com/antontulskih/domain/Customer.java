/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.antontulskih.util.ProductFormattedTable.*;
import static java.lang.System.out;

@Entity
@Table(name = "customer_table")
public class Customer implements Serializable {

    private Integer id;
    private String firstName;
    private String lastName;
    private String cardNumber;
    private Integer quantity;
    private Double invoice;
    private String login;
    private String password;
    private List<Product> shoppingBasket;

    public Customer() {
        this.shoppingBasket = new ArrayList<Product>();
    }

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

    public Customer(final String firstName,
                    final String lastName,
                    final String cardNumber,
                    final String login,
                    final String password) {
        this(firstName, lastName, cardNumber);
        setLogin(login);
        setPassword(password);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
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

    @Column(name = "first_name")
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

    @Column(name = "last_name")
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

    @Column(name = "card_number", unique = true)
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(final String cardNumber) {
        if (cardNumber == null) {
            throw new IllegalArgumentException("Customers card number cannot "
                    + "be null.");
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

    public void addProductToShoppingBasket(final List<Product> list) {
        addProductToShoppingBasket(list.toArray(new Product[list.size()]));
    }

    public void addProductToShoppingBasket(final Product... products) {
        out.printf("%n%n*** %s %s added to their purchase basket:%n",
                this.getFirstName(), this.getLastName());
        for (Product p: products) {
            this.shoppingBasket.add(p);
            invoice += p.getPrice();
            // to truncate garbage of double like 58.54890000000000000001
            invoice = Math.round(invoice * 100) / 100.0;
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

    @Column(name = "invoice")
    public Double getInvoice() {
        return invoice;
    }

    public void setInvoice(final Double invoice) {
        this.invoice = invoice;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "customer_product_table",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id"))
    public List<Product> getShoppingBasket() {
        return shoppingBasket;
    }

    public void setShoppingBasket(final List<Product> anotherShoppingBasket) {
        //kill me for the next two lines, they had cost me four days of my life
//        this.shoppingBasket.clear();
//        this.shoppingBasket.addAll(anotherShoppingBasket);
        this.shoppingBasket = anotherShoppingBasket;
    }

    @Column(name = "quantity")
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

    @Column(name = "login", unique = true)
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

    @Column(name = "password")
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
        return String.format("ID - %d, name - %s %s",
                id,
                firstName,
                lastName
        );
    }
}

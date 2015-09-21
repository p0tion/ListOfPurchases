/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.domain;

import com.antontulskih.util.MyLogger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.antontulskih.util.ProductFormattedTable.*;
import static java.lang.System.out;

@Entity
@Table(name = "customer_table")
public class Customer implements Serializable {

    static final MyLogger LOGGER = new MyLogger(Customer.class);
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
        this.firstName = firstName.trim();
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName.trim();
    }

    @Column(name = "card_number", unique = true)
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(final String cardNumber) {
        this.cardNumber = cardNumber.trim();
    }

    public void addProductToShoppingBasket(final Set<Product> set) {
        addProductToShoppingBasket(set.toArray(new Product[set.size()]));
    }

    public void addProductToShoppingBasket(final List<Product> list) {
        addProductToShoppingBasket(list.toArray(new Product[list.size()]));
    }

    public void addProductToShoppingBasket(final Product... products) {
        LOGGER.info(String.format("%n%n*** %s %s added to their purchase "
                + "basket:%n", this.getFirstName(), this.getLastName()));
        for (Product p: products) {
            this.shoppingBasket.add(p);
            invoice += p.getPrice();
            // to truncate garbage of double like 58.54890000000000000001
            invoice = Math.round(invoice * 100) / 100.0;
            LOGGER.info(String.format("- %s%n", p.getName()));
        }
        quantity = this.shoppingBasket.size();
        LOGGER.info("***");
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
        LOGGER.info(String.format("%n*** Clearing shopping basket of %s %s ***",
                this.getFirstName(), this.getLastName()));
        this.invoice = 0.0;
        this.quantity = 0;
        shoppingBasket.clear();
    }

    @Column(name = "login", unique = true)
    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
            this.login = login.trim();
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
            this.password = password.trim();
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
        int result = 1;
        result = 31 * result + ((id == null) ? 0 : id.hashCode());
        result = 31 * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = 31 * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = 31 * result +
                ((cardNumber == null) ? 0 : cardNumber.hashCode());
        result = 31 * result + ((login == null) ? 0 : login.hashCode());
        result = 31 * result + ((password == null) ? 0 : password.hashCode());
        result = 31 * result +
                ((shoppingBasket == null) ? 0 : shoppingBasket.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return String.format("ID - %d, name - %s %s", id, firstName, lastName);
    }
}

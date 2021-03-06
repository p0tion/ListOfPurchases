/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.persistence.Implementation.Collection;

import com.antontulskih.domain.Customer;
import com.antontulskih.persistence.DAO.CustomerDAO;
import com.antontulskih.util.MyLogger;

import java.util.Set;
import java.util.TreeSet;

import static com.antontulskih.util.CustomerComparator.IdSorterComparator;
import static com.antontulskih.util.CustomerComparator.LastNameSorterComparator;

public class CustomerDAO_Impl_Coll implements CustomerDAO {

    static final MyLogger LOGGER = new MyLogger(CustomerDAO_Impl_Coll.class);
    private static Integer id = 1;
    private static Set<Customer> customerList =
            new TreeSet<Customer>(new IdSorterComparator());

    public static CustomerDAO_Impl_Coll customerDAOCollImpl;

    private CustomerDAO_Impl_Coll() { }

    public static CustomerDAO_Impl_Coll getCustomerDAOCollImpl() {
        if (customerDAOCollImpl == null) {
            customerDAOCollImpl = new CustomerDAO_Impl_Coll();
        }
        return customerDAOCollImpl;
    }

    public static Set<Customer> getCustomerList() { return customerList; }

    @Override
    public boolean save(final Customer... customers) {
        for (Customer c: customers) {
            if (c == null) {
                throw new IllegalArgumentException("Input customer cannot be "
                        + "null. Your customer: " + c);
            }
            if (customerList.size() == 0) {
                c.setId(id++);
                customerList.add(c);
            } else {
                for (Customer d: customerList) {
                    if ((c.getFirstName().equals(d.getFirstName()))
                            && (c.getLastName().equals(d.getLastName()))) {
                        String errorMessage = String.format("Customer with such"
                                + " first and last names already exists: %s "
                                + "%s. ID - %d",
                                d.getFirstName(), d.getLastName(), d.getId());
                        throw new IllegalArgumentException(errorMessage);
                    }
                }
                c.setId(id++);
                customerList.add(c);
            }
            LOGGER.info(String.format("*** %s %s has been saved. ID - %d "
                    + "***", c.getFirstName(), c.getLastName(), c.getId()));
        }
        return true;
    }

    @Override
    public boolean remove(final Customer... customers) {
        for (Customer c: customers) {
            if (c == null) {
                throw new IllegalArgumentException("Input customer cannot be "
                        + "null. Your customer: " + c);
            }
            if (customerList.contains(c)) {
                LOGGER.info(String.format("*** %s %s has been removed ***",
                        c.getFirstName(), c.getLastName()));
                customerList.remove(c);
            } else {
                throw new IllegalArgumentException(String.format("There is no "
                        + "such customer:%s %s",
                        c.getFirstName(), c.getLastName()));
            }
        }
        return true;
    }

    @Override
    public boolean removeByIds(final Integer... ids) {
        Customer customerArray[] = new Customer[ids.length];
        int count = 0;
        for (Integer i: ids) {
            if (i == null || i < 1) {
                throw new IllegalArgumentException("ID cannot be null and "
                        + "should be greater than 0. Your ID: " +  i);
            }
            boolean isInList = false;
            for (Customer c: customerList) {
                if (c.getId().equals(i)) {
                    customerArray[count++] = c;
                    isInList = true;
                }
            }
            if (!isInList) {
                throw new IllegalArgumentException("There is no customer with "
                        + "such ID: " + i);
            }
        }
        remove(customerArray);
        return false;
    }

    @Override
    public boolean removeAll() {
        LOGGER.info("*** Removing all customers from the list of customers "
                + "***");
        customerList.clear();
        return true;
    }

    @Override
    public Customer getByName(final String firstName, final String lastName) {
        if (firstName == null || lastName == null
                || firstName.equals("") || lastName.equals("")) {
            throw new IllegalArgumentException(String.format("First and last "
                    + "name cannot be null or blank. First name: %s, last "
                    + "name: %s.", firstName, lastName ));
        }
        boolean isInList = false;
        for (Customer c: customerList) {
            if (c.getFirstName().equals(firstName)
                    && c.getLastName().equals(lastName)) {
                LOGGER.info(String.format("*** Getting %s %s ***",
                        c.getFirstName(), c.getLastName()));
                return c;
            }
        }
        if (!isInList) {
            throw new IllegalArgumentException(String.format("There is no "
                    + "customer with such first and last name: %s %s",
                    firstName, lastName));
        }
        return null;
    }

    @Override
    public Customer getByLoginAndPassword(final String login,
                                          final String password) {
        if (login == null || login.equals("") ||
                password == null || password.equals("")) {
            throw new IllegalArgumentException(String.format("login and "
                    + "password cannot be null or blank. "
                    + "Login: %s, password: %s", login, password));
        }
        boolean isInList = false;
        for (Customer c : customerList) {
            if (c.getLogin().equals(login) && c.getPassword().equals(password)){
                LOGGER.info(String.format("*** Getting %s %s ***",
                        c.getFirstName(), c.getLastName()));
                return c;
            }
            if (!isInList) {
                throw new IllegalArgumentException(String.format("There is no "
                        + "customer with such login or password.%n"
                        + "Login: %s, password: %s", login, password));
            }
        }
        return null;
    }

    @Override
    public Customer getByCardNumber(final String cardNumber) {
        if (cardNumber == null || cardNumber.equals("")) {
            throw new IllegalArgumentException(String.format("card number "
                    + "cannot be null or blank.%n "
                    + "Card number: %s", cardNumber));
        }
        boolean isInList = false;
        for (Customer c : customerList) {
            if (c.getCardNumber().equals(cardNumber)){
                LOGGER.info(String.format("*** Getting %s %s ***",
                        c.getFirstName(), c.getLastName()));
                return c;
            }
            if (!isInList) {
                throw new IllegalArgumentException(String.format("There is no "
                        + "customer with such card number.%n"
                        + "Card number: %s", cardNumber));
            }
        }
        return null;
    }

    @Override
    public Customer getByLogin(final String login) {
        if (login == null || login.equals("")) {
            throw new IllegalArgumentException(String.format("login "
                    + "cannot be null or blank.%n "
                    + "Login: %s", login));
        }
        boolean isInList = false;
        for (Customer c : customerList) {
            if (c.getLogin().equals(login)){
                LOGGER.info(String.format("*** Getting %s %s ***",
                        c.getFirstName(), c.getLastName()));
                return c;
            }
            if (!isInList) {
                throw new IllegalArgumentException(String.format("There is no "
                        + "customer with such login.%n"
                        + "Login: %s", login));
            }
        }
        return null;
    }

    @Override
    public Set<Customer> getByIds(final Integer... ids) {
        Set<Customer> set= new TreeSet<Customer>(new IdSorterComparator());
        for (Integer i: ids) {
            set.add(getById(i));
        }
        return set;
    }

    @Override
    public Customer getById(final Integer id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException(String.format("ID cannot be "
                    + "null and should be greater than 0. Your ID: %d.", id));
        }
        boolean isInList = false;
        for (Customer c: customerList) {
            if (c.getId().equals(id)) {
                LOGGER.info(String.format("*** Getting customer with ID %d "
                        + "from the list of customers ***", id));
                return c;
            }
        }
        if (!isInList) {
            throw new IllegalArgumentException("There is no customer with "
                    + "such ID: " + id);
        }
        return null;
    }

    @Override
    public boolean update(Customer... customers) {
        for (Customer c: customers) {
            if (c == null) {
                throw new IllegalArgumentException("Customer cannot be null.\n"
                        + "Your customer: " + c);
            }
            for (Customer d: customerList) {
                if (c.getId().equals(d.getId())) {
                    LOGGER.info(String.format("*** Updating %s %s ***",
                            c.getFirstName(), c.getLastName()));
                    d.setFirstName(c.getFirstName());
                    d.setLastName(c.getLastName());
                    d.setCardNumber(c.getCardNumber());
                    d.setLogin(c.getLogin());
                    d.setPassword(c.getPassword());
                    d.setQuantity(c.getQuantity());
                    d.setInvoice(c.getInvoice());
                    d.setShoppingBasket(c.getShoppingBasket());
                }
            }
        }
        return false;
    }

    @Override
    public boolean updateAll() {
        return false;
    }

    @Override
    public Set<Customer> getAllSortedById() {
        LOGGER.info("*** Getting all customers from the list ordered "
                + "by ID ***");
        return customerList;
    }

    @Override
    public Set<Customer> getAllSortedByLastName() {
        LOGGER.info("*** Getting all customers from the list ordered "
                + "by last name ***");
        Set<Customer> lastNameSortedSet = new TreeSet<Customer>(
                new LastNameSorterComparator());
        lastNameSortedSet.addAll(customerList);
        return lastNameSortedSet;
    }

    @Override
    public Set<Customer> getAllSortedByInvoice() {
        LOGGER.info("*** Getting all customers from the list ordered "
                + "by invoice ***");
        Set<Customer> lastNameSortedSet = new TreeSet<Customer>(
                new LastNameSorterComparator());
        lastNameSortedSet.addAll(customerList);
        return null;
    }

}

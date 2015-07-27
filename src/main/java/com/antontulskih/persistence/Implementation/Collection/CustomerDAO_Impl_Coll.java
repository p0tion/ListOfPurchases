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

import java.util.Set;
import java.util.TreeSet;

import static com.antontulskih.util.CustomerComparator.IdSorterComparator;
import static com.antontulskih.util.CustomerComparator.LastNameSorterComparator;
import static java.lang.System.out;

public class CustomerDAO_Impl_Coll implements CustomerDAO {

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
            if (customerList.size() == 0) {
                c.setId(id++);
                customerList.add(c);
                out.println("*** " + c.getFirstName()
                        + " "
                        + c.getLastName()
                        + " has been saved. ID - "
                        + c.getId() + " ***");
            } else {
                for (Customer d: customerList) {
                    if ((c.getFirstName().equals(d.getFirstName()))
                            && (c.getLastName().equals(d.getLastName()))) {
                        throw new IllegalArgumentException("Customer with such"
                                + " first and last names already exists:"
                                + d.getFirstName() + " "
                                + d.getLastName() + ". "
                                + "ID - " + d.getId());
                    }
                }
                c.setId(id++);
                customerList.add(c);
                out.println("*** " + c.getFirstName()
                        + " "
                        + c.getLastName()
                        + " has been saved. ID - "
                        + c.getId() + " ***");

            }
        }
        return true;
    }

    @Override
    public boolean remove(final Customer... customers) {
        for (Customer c: customers) {
            if (customerList.contains(c)) {
                out.println("*** " + c.getFirstName() + " "
                        + c.getLastName() + " has been removed ***");
                customerList.remove(c);
            } else {
                throw new IllegalArgumentException("There is no such customer: "
                        + c.getFirstName() + " " + c.getLastName());
            }
        }
        return true;
    }

    @Override
    public boolean removeByIds(final Integer... ids) {
        Customer customerArray[] = new Customer[ids.length];
        int count = 0;
        for (Integer i: ids) {
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
        out.println("*** Removing all customers from the list of customers "
                + "***");
        customerList.clear();
        return true;
    }

    @Override
    public Customer getByName(final String firstName, final String lastName) {
        boolean isInList = false;
        for (Customer c: customerList) {
            if (c.getFirstName().equals(firstName)
                    && c.getLastName().equals(lastName)) {
                out.println("*** Getting "
                        + c.getFirstName()
                        + " " + c.getLastName()
                        + " ***");
                return c;
            }
        }
        if (!isInList) {
            throw new IllegalArgumentException("There is no customer with such "
                    + " first and last name: " + firstName + " " + lastName);
        }
        return null;
    }

    @Override
    public Set<Customer> getByIds(final Integer... ids) {
        Set<Customer> set= new TreeSet<Customer>(new IdSorterComparator());
        for (Integer i: ids) {
            boolean isInList = false;
            for (Customer c: customerList) {
                if (c.getId().equals(i)) {
                    out.println("*** Getting customer with ID "
                            + i + " from the list of customers ***");
                    isInList = true;
                    set.add(c);
                }
            }
            if (!isInList) {
                throw new IllegalArgumentException("There is no customer with "
                        + "such ID: " + id);
            }
        }
        return set;
    }

    @Override
    public Customer getById(final Integer id) {
        boolean isInList = false;
        for (Customer c: customerList) {
            if (c.getId().equals(id)) {
                out.println("*** Getting customer with ID "
                        + id + " from the list of customers ***");
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
            for (Customer d: customerList) {
                if (c.getId().equals(d.getId())) {
                    out.println("*** Updating "
                            + c.getFirstName() + " " + c.getLastName()
                            + " ***");

                    d.setFirstName(c.getFirstName());
                    d.setLastName(c.getLastName());
                    d.setCardNumber(c.getCardNumber());
                    d.setQuantity(c.getQuantity());
                    d.setInvoice(c.getInvoice());
                    d.setShoppingBasket(c.getShoppingBasket());
                }
            }
        }
        return false;
    }

    @Override
    public Set<Customer> getAllSortedById() {
        out.println("\n*** Getting all customers from the list ordered "
                + "by ID ***");
        return customerList;
    }

    @Override
    public Set<Customer> getAllSortedByLastName() {
        out.println("\n*** Getting all customers from the list ordered "
                + "by last name ***");
        Set<Customer> lastNameSortedSet = new TreeSet<Customer>(
                new LastNameSorterComparator());
        lastNameSortedSet.addAll(customerList);
        return lastNameSortedSet;
    }

    @Override
    public Set<Customer> getAllSortedByInvoice() {
        out.println("\n*** Getting all customers from the list ordered "
                + "by invoice***");
        Set<Customer> lastNameSortedSet = new TreeSet<Customer>(
                new LastNameSorterComparator());
        lastNameSortedSet.addAll(customerList);
        return null;
    }

}

/**
 * @author Tulskih Anton
 *         $ {DATE}.
 */

package com.antontulskih.persistence.Implementation;

import com.antontulskih.domain.Customer;
import com.antontulskih.domain.Product;
import com.antontulskih.persistence.DAO.CustomerDAO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class CustomerDAOCollImpl implements CustomerDAO {
    private static List<Customer> ordersList = new ArrayList<Customer>();

    private static CustomerDAOCollImpl customerDAOCollImpl;

    private CustomerDAOCollImpl() { }

    public static CustomerDAOCollImpl getCustomerDAOCollImpl() {
        if (customerDAOCollImpl == null) {
            customerDAOCollImpl = new CustomerDAOCollImpl();
        }
        return customerDAOCollImpl;
    }

    @Override
    public boolean addToOrderProcessingList(final Customer... customer) {
        for (Customer c: customer) {
            ordersList.add(c);
            System.out.println("\n***" + c.getCustomerFirstName()
                    + " "
                    + c.getCustomerLastName()
                    + " has been added to the list of orders***");
        }
        return true;
    }

    @Override
    public boolean removeFromOrderProcessingList(final Customer... customer) {
        for (Customer c: customer) {
            ordersList.remove(c);
            System.out.println("\n***" + c.getCustomerFirstName()
                    + " "
                    + c.getCustomerLastName()
                    + " has been removed from the list of orders***");
        }
        return true;
    }

    @Override
    public Customer getFromOrderProcessingListByName(final String firstName,
                                                     final String lastName) {
        for (Customer c: ordersList) {
            if (c.getCustomerFirstName().equals(firstName)
                    && c.getCustomerLastName().equals(lastName)) {
                System.out.println("\n***Getting "
                        + c.getCustomerFirstName()
                        + " " + c.getCustomerLastName()
                        + " from the list of orders***");
                return c;
            }
        }
        return null;
    }

    @Override
    public Customer getFromOrderProcessingListById(final Long id) {
        for (Customer c: ordersList) {
            if (c.getId() == id) {
                System.out.println("\n***Getting customer with ID \""
                        + c.getId() + "\" from the list of orders***");
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Customer> getAllListOfOrders() {
        return ordersList;
    }

    @Override
    public void showAllOrdersById() {
        System.out.println("\n***Displaying the list of orders "
                + "sorted by ID***");
        Collections.sort(ordersList, new IdSorterComparator());
        for (Customer c: ordersList) {
            System.out.print(c);
            System.out.print(String.format(",  %.2f\n", c.getInvoice()));
        }
    }

    @Override
    public void showAllOrdersByLastName() {
        System.out.println("\n***Displaying the list of orders "
                + "sorted by last name***");
        Collections.sort(ordersList, new LastNameSorterComparator());
        for (Customer c: ordersList) {
            System.out.print(c);
            System.out.print(String.format(",  %.2f\n", c.getInvoice()));
        }
    }

    @Override
    public void showAllOrdersByInvoice() {
        System.out.println("\n***Displaying the list of orders "
                + "sorted by invoice***");
        Collections.sort(ordersList, new InvoiceSorterComparator());
        for (Customer c: ordersList) {
            System.out.print(c);
            System.out.print(String.format(",  %.2f\n", c.getInvoice()));
        }
    }

    @Override
    public void saveOrderProcessingList(final String saveType) {
        if (saveType.toLowerCase().startsWith("ser")) {
            final String fileName = "ordersList.ser";
            System.out.println("\n***Serializing list of orders to "
                    + fileName
                    + "***");
            try {
                ObjectOutputStream oos = new ObjectOutputStream(
                        new FileOutputStream(fileName));
                oos.writeObject(ordersList);
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (saveType.toLowerCase().startsWith("xml")) {
            final String fileName = "ordersList.xml";
            System.out.println("\n***Marshalling list of orders to "
                    + fileName
                    + "***");
            try {
                XStream xStream = new XStream();
                xStream.alias("Customer", Customer.class);
                xStream.alias("Product", Product.class);
                xStream.toXML(ordersList, new FileOutputStream(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (saveType.toLowerCase().startsWith("json")) {
            final String fileName = "ordersList.json";
            System.out.println("\n***Saving list of orders to "
                    + fileName
                    + "***");
            try {
                XStream xStream = new XStream(new JettisonMappedXmlDriver());
                xStream.toXML(ordersList, new FileOutputStream(fileName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Customer> loadOrderProcessingList(final String fileType) {
        List<Customer> newOrdersList = Collections.EMPTY_LIST;
        if (fileType.toLowerCase().startsWith("ser")) {
            final String fileName = "ordersList.ser";
            System.out.println("\n***Deserializing list of orders from "
                    + fileName
                    + "***");
            try {
                ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(fileName));
                newOrdersList = (List<Customer>) ois.readObject();
                ois.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (fileType.toLowerCase().startsWith("xml")) {
            final String fileName = "ordersList.xml";
            System.out.println("\n***Unmarshalling list of orders from "
                    + fileName
                    + "***");
            try {
                XStream xStream = new XStream();
                xStream.alias("Customer", Customer.class);
                xStream.alias("Product", Product.class);
                newOrdersList = (List<Customer>) xStream.fromXML(
                        new FileInputStream(fileName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (fileType.toLowerCase().startsWith("json")) {
            final String fileName = "ordersList.json";
            System.out.println("\n***Loading list of orders from "
                    + fileName
                    + "***");
            try {
                XStream xStream = new XStream(new JettisonMappedXmlDriver());
                newOrdersList = (List<Customer>) xStream.fromXML(
                        new FileInputStream(fileName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return newOrdersList;
    }

    private class IdSorterComparator implements Comparator<Customer> {
        @Override
        public int compare(final Customer c1, final Customer c2) {
            return (int) (c1.getId() - c2.getId());
        }
    }

    private class LastNameSorterComparator implements Comparator<Customer> {
        @Override
        public int compare(final Customer c1, final Customer c2) {
            return c1.getCustomerLastName().compareTo(c2.getCustomerLastName());
        }
    }

    private class InvoiceSorterComparator implements Comparator<Customer> {
        @Override
        public int compare(final Customer c1, final Customer c2) {
            if (c1.getInvoice() > c2.getInvoice()) {
                return 1;
            } else if (c1.getInvoice() < c2.getInvoice()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}

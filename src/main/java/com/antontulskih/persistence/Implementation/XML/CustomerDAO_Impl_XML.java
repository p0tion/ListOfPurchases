/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.persistence.Implementation.XML;

import com.antontulskih.domain.Customer;
import com.antontulskih.domain.Product;
import com.antontulskih.persistence.DAO.CustomerDAO;
import com.antontulskih.persistence.Implementation.Collection.CustomerDAO_Impl_Coll;
import com.antontulskih.util.MyLogger;
import com.thoughtworks.xstream.XStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class CustomerDAO_Impl_XML implements CustomerDAO {

    static final MyLogger LOGGER = new MyLogger(CustomerDAO_Impl_XML.class);

    protected CustomerDAO_Impl_Coll customerDAOImplColl;
    protected String fileName;

    public CustomerDAO_Impl_XML() {
        customerDAOImplColl = CustomerDAO_Impl_Coll.getCustomerDAOCollImpl();
        CustomerDAO_Impl_Coll.getCustomerList().clear();
        fileName = "customerList.xml";
    }

    @Override
    public boolean save(final Customer... customers) {
        for(Customer c: customers) {
            customerDAOImplColl.save(c);
        }
        return writeToFile();
    }

    public boolean writeToFile() {
        LOGGER.info(String.format("*** Saving the list of customers to %s ***",
                fileName));
        try {
            XStream xStream = new XStream();
            xStream.alias("Customer", Customer.class);
            xStream.alias("Product", Product.class);
            xStream.toXML(CustomerDAO_Impl_Coll.getCustomerList(),
                    new FileOutputStream(fileName));
        } catch (IOException ioe) {
            LOGGER.error("IOException occurred", ioe);
        }
        return true;
    }

    public boolean readFromFile() {
        CustomerDAO_Impl_Coll.getCustomerList().clear();
        LOGGER.info(String.format("*** Loading the list of customers from %s "
                        + "***", fileName));
        try {
            XStream xStream = new XStream();
            xStream.alias("Customer", Customer.class);
            xStream.alias("Product", Product.class);
            CustomerDAO_Impl_Coll.getCustomerList().addAll(
                    (Set<Customer>) xStream.fromXML(
                            new FileInputStream(fileName)));
        } catch (FileNotFoundException fnfe) {
            LOGGER.error(String.format("File %s wasn't found", fileName), fnfe);
        }
        return true;
    }

    @Override
    public boolean remove(final Customer... customers) {
        readFromFile();
        for(Customer c: customers) {
            customerDAOImplColl.remove(c);
        }
        return writeToFile();
    }

    @Override
    public boolean removeByIds(final Integer... ids) {
        readFromFile();
        for (Integer i: ids) {
            customerDAOImplColl.removeByIds(i);
        }
        return writeToFile();
    }

    @Override
    public boolean removeAll() {
        readFromFile();
        customerDAOImplColl.removeAll();
        return writeToFile();
    }

    @Override
    public Customer getByName(final String firstName, final String lastName) {
        readFromFile();
        return customerDAOImplColl.getByName(firstName, lastName);
    }

    @Override
    public Customer getByLoginAndPassword(final String login,
                                          final String password) {
        readFromFile();
        return customerDAOImplColl.getByLoginAndPassword(login, password);
    }

    @Override
    public Customer getByCardNumber(final String cardNumber) {
        readFromFile();
        return customerDAOImplColl.getByCardNumber(cardNumber);
    }

    @Override
    public Customer getByLogin(final String login) {
        readFromFile();
        return customerDAOImplColl.getByLogin(login);
    }

    @Override
    public Set<Customer> getByIds(final Integer... ids) {
        readFromFile();
        return customerDAOImplColl.getByIds(ids);
    }

    @Override
    public Customer getById(final Integer id) {
        readFromFile();
        return customerDAOImplColl.getById(id);
    }

    @Override
    public Set<Customer> getAllSortedById() {
        readFromFile();
        return customerDAOImplColl.getAllSortedById();
    }

    @Override
    public Set<Customer> getAllSortedByLastName() {
        readFromFile();
        return customerDAOImplColl.getAllSortedByLastName();
    }

    @Override
    public Set<Customer> getAllSortedByInvoice() {
        readFromFile();
        return customerDAOImplColl.getAllSortedByInvoice();
    }

    @Override
    public boolean update(Customer... customers) {
        readFromFile();
        customerDAOImplColl.update(customers);
        return writeToFile();
    }
}

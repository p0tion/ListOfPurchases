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
import com.thoughtworks.xstream.XStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import static java.lang.System.out;

public class CustomerDAO_Impl_XML implements CustomerDAO {

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
        out.println("\n*** Saving list of customers to " + fileName + " ***\n");
        try {
            XStream xStream = new XStream();
            xStream.alias("Customer", Customer.class);
            xStream.alias("Product", Product.class);
            xStream.toXML(CustomerDAO_Impl_Coll.getCustomerList(),
                    new FileOutputStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean readFromFile() {
        CustomerDAO_Impl_Coll.getCustomerList().clear();
        out.println("\n*** Loading list of customers from " + fileName
                + " ***\n");
        try {
            XStream xStream = new XStream();
            xStream.alias("Customer", Customer.class);
            xStream.alias("Product", Product.class);
            CustomerDAO_Impl_Coll.getCustomerList().addAll(
                    (Set<Customer>) xStream.fromXML(
                            new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
    public boolean removeById(final Integer... ids) {
        readFromFile();
        for (Integer i: ids) {
            customerDAOImplColl.removeById(i);
        }
        return writeToFile();
    }

    @Override
    public Customer getByName(final String firstName, final String lastName) {
        readFromFile();
        return customerDAOImplColl.getByName(firstName, lastName);
    }

    @Override
    public Set<Customer> getById(final Integer... ids) {
        readFromFile();
        return customerDAOImplColl.getById(ids);
    }

    @Override
    public Customer getById(final Integer id) {
        readFromFile();
        return customerDAOImplColl.getById(id);
    }

    @Override
    public Set<Customer> getAll() {
        readFromFile();
        return customerDAOImplColl.getAll();
    }

    @Override
    public boolean update(Customer... customers) {
        readFromFile();
        customerDAOImplColl.update(customers);
        return writeToFile();
    }

    @Override
    public void showAllById() {
        readFromFile();
        customerDAOImplColl.showAllById();
    }

    @Override
    public void showAllByLastName() {
        readFromFile();
        customerDAOImplColl.showAllByLastName();
    }

    @Override
    public void showAllByInvoice() {
        readFromFile();
        customerDAOImplColl.showAllByInvoice();
    }
}

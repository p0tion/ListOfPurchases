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
import java.util.TreeSet;

import static com.antontulskih.util.CustomerComparator.IdSorterComparator;
import static java.lang.System.out;

public class CustomerDAO_Impl_XML implements CustomerDAO {

    protected CustomerDAO_Impl_Coll customerDAOImplColl =
            CustomerDAO_Impl_Coll.getCustomerDAOCollImpl();

    @Override
    public boolean save(final Customer... customers) {
        for(Customer c: customers) {
            customerDAOImplColl.save(c);
        }
        return writeToFile();
    }

    public boolean writeToFile() {
        final String fileName = "customerList.xml";
        out.println("\n*** Saving list of customers to " + fileName + " ***");
        try {
            XStream xStream = new XStream();
            xStream.alias("Customer", Customer.class);
            xStream.alias("Product", Product.class);
            xStream.toXML(customerDAOImplColl.getCustomerList(),
                    new FileOutputStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Set<Customer> readFromFile() {
        Set<Customer> newOrdersList =
                new TreeSet<Customer>(new IdSorterComparator());
        final String fileName = "customerList.xml";
        out.println("\n*** Loading list of customers from " + fileName
                + " ***");
        try {
            XStream xStream = new XStream();
            xStream.alias("Customer", Customer.class);
            xStream.alias("Product", Product.class);
            newOrdersList.addAll((Set<Customer>) xStream.fromXML(
                    new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return newOrdersList;
    }

    @Override
    public boolean remove(final Customer... customers) {
        for(Customer c: customers) {
            customerDAOImplColl.remove(c);
        }
        return writeToFile();
    }

    @Override
    public boolean removeById(final Integer... ids) {
        for (Integer i: ids) {
            customerDAOImplColl.removeById(i);
        }
        return writeToFile();
    }

    @Override
    public Customer getByName(final String firstName, final String lastName) {
        return customerDAOImplColl.getByName(firstName, lastName);
    }

    @Override
    public Set<Customer> getById(final Integer... ids) {
            return customerDAOImplColl.getById(ids);
    }

    @Override
    public Customer getById(final Integer id) {
        return customerDAOImplColl.getById(id);
    }

    @Override
    public boolean update(Customer... customers) {
        return customerDAOImplColl.update(customers);
    }

    @Override
    public void showAllById() {
        customerDAOImplColl.showAllById();
    }

    @Override
    public void showAllByLastName() {
        customerDAOImplColl.showAllByLastName();
    }

    @Override
    public void showAllByInvoice() {
        customerDAOImplColl.showAllByInvoice();
    }
}

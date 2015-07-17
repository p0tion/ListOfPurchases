/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.persistence.Implementation.Serialization;

import com.antontulskih.domain.Customer;
import com.antontulskih.persistence.Implementation.XML.CustomerDAO_Impl_XML;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

import static com.antontulskih.util.CustomerComparator.IdSorterComparator;
import static java.lang.System.out;

public final class CustomerDAO_Impl_Ser extends
        CustomerDAO_Impl_XML {

    @Override
    public boolean writeToFile() {
        final String fileName = "ordersList.ser";
        out.println("\n***Serializing list of orders to " + fileName + "***");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(fileName));
            oos.writeObject(customerDAOImplColl.getCustomerList());
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Set<Customer> readFromFile() {
        Set<Customer> newOrdersList =
                new TreeSet<Customer>(new IdSorterComparator());
        final String fileName = "ordersList.ser";
        out.println("\n***Deserializing list of orders from "
                + fileName + "***");
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(fileName))) {
            newOrdersList.addAll((Set<Customer>) ois.readObject());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newOrdersList;
    }
}

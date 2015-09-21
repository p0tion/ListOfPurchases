/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.persistence.Implementation.Serialization;

import com.antontulskih.domain.Customer;
import com.antontulskih.persistence.Implementation.Collection.CustomerDAO_Impl_Coll;
import com.antontulskih.persistence.Implementation.XML.CustomerDAO_Impl_XML;
import com.antontulskih.util.MyLogger;

import java.io.*;
import java.util.Set;

public final class CustomerDAO_Impl_Ser extends
        CustomerDAO_Impl_XML {

    static final MyLogger LOGGER = new MyLogger(CustomerDAO_Impl_Ser.class);

    public CustomerDAO_Impl_Ser() {
        customerDAOImplColl = CustomerDAO_Impl_Coll.getCustomerDAOCollImpl();
        CustomerDAO_Impl_Coll.getCustomerList().clear();
        fileName = "customerList.ser";
    }

    @Override
    public boolean writeToFile() {
        LOGGER.info(String.format("*** Saving list of customers to %s ***",
                fileName));
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(fileName))){
            oos.writeObject(CustomerDAO_Impl_Coll.getCustomerList());
        } catch (IOException ioe) {
            LOGGER.error("IOException occurred", ioe);
        }
        return true;
    }

    @Override
    public boolean readFromFile() {
        CustomerDAO_Impl_Coll.getCustomerList().clear();
        LOGGER.info(String.format("*** Loading list of customers from %s ***",
                fileName));
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(fileName))) {
            CustomerDAO_Impl_Coll.getCustomerList().addAll(
                    (Set<Customer>) ois.readObject());
        } catch (ClassNotFoundException cnfe) {
            LOGGER.error("ClassNotFoundException occurred", cnfe);
        } catch (IOException ioe) {
            LOGGER.error("IOException occurred", ioe);
        }
        return true;
    }
}

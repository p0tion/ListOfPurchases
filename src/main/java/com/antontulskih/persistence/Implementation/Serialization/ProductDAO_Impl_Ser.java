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
import com.antontulskih.persistence.Implementation.Collection.ProductDAO_Impl_Coll;
import com.antontulskih.persistence.Implementation.XML.ProductDAO_Impl_XML;
import com.antontulskih.util.MyLogger;

import java.io.*;
import java.util.Set;

public final class ProductDAO_Impl_Ser extends
        ProductDAO_Impl_XML {

    static final MyLogger LOGGER = new MyLogger(ProductDAO_Impl_Ser.class);

    public ProductDAO_Impl_Ser() {
        productDAOImplColl = ProductDAO_Impl_Coll.getProductDAOCollImpl();
        ProductDAO_Impl_Coll.getProductList().clear();
        fileName = "productList.ser";
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

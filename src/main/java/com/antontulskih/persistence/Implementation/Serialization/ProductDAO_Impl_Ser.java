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

import java.io.*;
import java.util.Set;

import static java.lang.System.out;

public final class ProductDAO_Impl_Ser extends
        ProductDAO_Impl_XML {

    public ProductDAO_Impl_Ser() {
        productDAOImplColl = ProductDAO_Impl_Coll.getProductDAOCollImpl();
        ProductDAO_Impl_Coll.getProductList().clear();
        fileName = "productList.ser";
    }

    @Override
    public boolean writeToFile() {
        out.println("\n*** Saving list of customers to " + fileName + " ***\n");
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(fileName))){
            oos.writeObject(CustomerDAO_Impl_Coll.getCustomerList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean readFromFile() {
        CustomerDAO_Impl_Coll.getCustomerList().clear();
        out.println("\n*** Loading list of customers from "
                + fileName + " ***\n");
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(fileName))) {
            CustomerDAO_Impl_Coll.getCustomerList().addAll(
                    (Set<Customer>) ois.readObject());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}

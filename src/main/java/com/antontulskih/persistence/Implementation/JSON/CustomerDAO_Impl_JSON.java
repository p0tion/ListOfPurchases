/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.persistence.Implementation.JSON;

import com.antontulskih.domain.Customer;
import com.antontulskih.persistence.Implementation.Collection.CustomerDAO_Impl_Coll;
import com.antontulskih.persistence.Implementation.XML.CustomerDAO_Impl_XML;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Set;

import static java.lang.System.out;

public final class CustomerDAO_Impl_JSON extends
        CustomerDAO_Impl_XML {

    public CustomerDAO_Impl_JSON() {
        customerDAOImplColl = CustomerDAO_Impl_Coll.getCustomerDAOCollImpl();
        CustomerDAO_Impl_Coll.getCustomerList().clear();
        fileName = "customerList.json";
    }

    @Override
    public boolean writeToFile() {
        out.println("\n*** Saving list of customers to " + fileName + " ***\n");
        try {
            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            xStream.toXML(CustomerDAO_Impl_Coll.getCustomerList(),
                    new FileOutputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean readFromFile() {
        CustomerDAO_Impl_Coll.getCustomerList().clear();
        out.println("\n***Loading list of orders from " + fileName + "***");
        try {
            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            CustomerDAO_Impl_Coll.getCustomerList().addAll(
                    (Set<Customer>) xStream.fromXML(
                            new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }
}

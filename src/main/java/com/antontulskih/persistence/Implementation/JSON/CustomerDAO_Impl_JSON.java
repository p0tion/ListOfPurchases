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
import com.antontulskih.util.MyLogger;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Set;

import static java.lang.System.out;

public final class CustomerDAO_Impl_JSON extends
        CustomerDAO_Impl_XML {

    static final MyLogger LOGGER = new MyLogger(CustomerDAO_Impl_JSON.class);

    public CustomerDAO_Impl_JSON() {
        customerDAOImplColl = CustomerDAO_Impl_Coll.getCustomerDAOCollImpl();
        CustomerDAO_Impl_Coll.getCustomerList().clear();
        fileName = "customerList.json";
    }

    @Override
    public boolean writeToFile() {
        LOGGER.info(String.format("*** Saving list of customers to %s ***",
                fileName));
        try {
            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            xStream.toXML(CustomerDAO_Impl_Coll.getCustomerList(),
                    new FileOutputStream(fileName));
        } catch (FileNotFoundException fnfe) {
            LOGGER.error(String.format("File %s wasn't found", fileName), fnfe);
        }
        return true;
    }

    @Override
    public boolean readFromFile() {
        CustomerDAO_Impl_Coll.getCustomerList().clear();
        LOGGER.info(String.format("*** Loading list of customers from %s ***",
                fileName));
        try {
            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            CustomerDAO_Impl_Coll.getCustomerList().addAll(
                    (Set<Customer>) xStream.fromXML(
                            new FileInputStream(fileName)));
        } catch (FileNotFoundException fnfe) {
            LOGGER.error(String.format("File %s wasn't found", fileName), fnfe);
        }
        return true;
    }
}

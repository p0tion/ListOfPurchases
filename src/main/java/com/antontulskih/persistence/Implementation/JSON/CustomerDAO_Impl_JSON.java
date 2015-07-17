/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.persistence.Implementation.JSON;

import com.antontulskih.domain.Customer;
import com.antontulskih.persistence.Implementation.XML.CustomerDAO_Impl_XML;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Set;
import java.util.TreeSet;

import static com.antontulskih.util.CustomerComparator.IdSorterComparator;
import static java.lang.System.out;

public final class CustomerDAO_Impl_JSON extends
        CustomerDAO_Impl_XML {

    @Override
    public boolean writeToFile() {
        final String fileName = "ordersList.json";
        out.println("\n***Saving list of orders to " + fileName + "***");
        try {
            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            xStream.toXML(customerDAOImplColl.getCustomerList(),
                    new FileOutputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Set<Customer> readFromFile() {
        Set<Customer> newOrdersList =
                new TreeSet<Customer>(new IdSorterComparator());
        final String fileName = "ordersList.json";
        out.println("\n***Loading list of orders from " + fileName + "***");
        try {
            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            newOrdersList.addAll((Set<Customer>) xStream.fromXML(
                    new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return newOrdersList;
    }
}

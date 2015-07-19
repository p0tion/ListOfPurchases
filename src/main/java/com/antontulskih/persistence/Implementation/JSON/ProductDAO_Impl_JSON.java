/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.persistence.Implementation.JSON;

import com.antontulskih.domain.Product;
import com.antontulskih.persistence.Implementation.Collection.ProductDAO_Impl_Coll;
import com.antontulskih.persistence.Implementation.XML.ProductDAO_Impl_XML;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Set;

import static java.lang.System.out;

public final class ProductDAO_Impl_JSON extends
        ProductDAO_Impl_XML {

    public ProductDAO_Impl_JSON() {
        productDAOImplColl = ProductDAO_Impl_Coll.getProductDAOCollImpl();
        ProductDAO_Impl_Coll.getProductList().clear();
        fileName = "productList.json";
    }

    @Override
    public boolean writeToFile() {
        out.println("\n*** Saving list of products to " + fileName + " ***\n");
        try {
            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            xStream.toXML(ProductDAO_Impl_Coll.getProductList(),
                    new FileOutputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean readFromFile() {
        ProductDAO_Impl_Coll.getProductList().clear();
        out.println("\n*** Loading list of products from " + fileName + " ***\n");
        try {
            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            ProductDAO_Impl_Coll.getProductList().addAll(
                    (Set<Product>) xStream.fromXML(
                            new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }
}

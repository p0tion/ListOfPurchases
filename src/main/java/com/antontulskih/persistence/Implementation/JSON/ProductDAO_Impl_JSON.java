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
import com.antontulskih.util.MyLogger;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Set;

public final class ProductDAO_Impl_JSON extends
        ProductDAO_Impl_XML {

    static final MyLogger LOGGER = new MyLogger(ProductDAO_Impl_JSON.class);

    public ProductDAO_Impl_JSON() {
        productDAOImplColl = ProductDAO_Impl_Coll.getProductDAOCollImpl();
        ProductDAO_Impl_Coll.getProductList().clear();
        fileName = "productList.json";
    }

    @Override
    public boolean writeToFile() {
        LOGGER.info(String.format("*** Saving list of products to %s ***",
                fileName));
        try {
            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            xStream.toXML(ProductDAO_Impl_Coll.getProductList(),
                    new FileOutputStream(fileName));
        } catch (FileNotFoundException fnfe) {
            LOGGER.error(String.format("File %s wasn't found", fileName), fnfe);
        }
        return true;
    }

    @Override
    public boolean readFromFile() {
        ProductDAO_Impl_Coll.getProductList().clear();
        LOGGER.info(String.format("*** Loading list of products from %s",
                fileName));
        try {
            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            ProductDAO_Impl_Coll.getProductList().addAll(
                    (Set<Product>) xStream.fromXML(
                            new FileInputStream(fileName)));
        } catch (FileNotFoundException fnfe) {
            LOGGER.error(String.format("File %s wasn't found", fileName), fnfe);
        }
        return true;
    }
}

/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.persistence.Implementation.XML;

import com.antontulskih.domain.Product;
import com.antontulskih.persistence.DAO.ProductDAO;
import com.antontulskih.persistence.Implementation.Collection.ProductDAO_Impl_Coll;
import com.thoughtworks.xstream.XStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import static java.lang.System.out;

public class ProductDAO_Impl_XML implements ProductDAO {

    protected ProductDAO_Impl_Coll productDAOImplColl;
    protected String fileName;

    public ProductDAO_Impl_XML() {
        productDAOImplColl = ProductDAO_Impl_Coll.getProductDAOCollImpl();
        ProductDAO_Impl_Coll.getProductList().clear();
        fileName = "productList.xml";
    }

    @Override
    public boolean save(final Product... products) {
        for(Product p: products) {
            productDAOImplColl.save(p);
        }
        return writeToFile();
    }

    public boolean writeToFile() {
        out.printf("%n*** Saving list of products to %s ***%n", fileName);
        try {
            XStream xStream = new XStream();
            xStream.alias("Product", Product.class);
            xStream.toXML(ProductDAO_Impl_Coll.getProductList(),
                    new FileOutputStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean readFromFile() {
        out.printf("%n*** Loading list of products from ***%n", fileName);
        try {
            XStream xStream = new XStream();
            xStream.alias("Product", Product.class);
            ProductDAO_Impl_Coll.getProductList().addAll(
                    (Set<Product>) xStream.fromXML(
                            new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean remove(final Product... products) {
        readFromFile();
        for(Product p: products) {
            productDAOImplColl.remove(p);
        }
        return writeToFile();
    }

    @Override
    public boolean removeByIds(final Integer... ids) {
        readFromFile();
        for (Integer i: ids) {
            productDAOImplColl.removeByIds(i);
        }
        return writeToFile();
    }

    @Override
    public boolean removeAll() {
        readFromFile();
        productDAOImplColl.removeAll();
        return writeToFile();
    }

    @Override
    public Set<Product> getByNames(final String... names) {
        readFromFile();
        return productDAOImplColl.getByNames(names);
    }

    @Override
    public Product getByName(final String name) {
        readFromFile();
        return productDAOImplColl.getByName(name);
    }

    @Override
    public Set<Product> getByIds(final Integer... ids) {
        readFromFile();
        return productDAOImplColl.getByIds(ids);
    }

    @Override
    public Product getById(final Integer id) {
        readFromFile();
        return productDAOImplColl.getById(id);
    }

    @Override
    public Set<Product> getAllSortedById() {
        readFromFile();
        return productDAOImplColl.getAllSortedById();
    }

    @Override
    public Set<Product> getAllSortedByName() {
        readFromFile();
        return productDAOImplColl.getAllSortedByName();
    }

    @Override
    public Set<Product> getAllSortedByPrice() {
        readFromFile();
        return productDAOImplColl.getAllSortedByPrice();
    }

    @Override
    public boolean update(Product... products) {
        readFromFile();
        productDAOImplColl.update(products);
        return writeToFile();
    }
}

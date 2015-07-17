/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.persistence.Implementation.XML;

import com.thoughtworks.xstream.XStream;
import com.antontulskih.domain.Product;
import com.antontulskih.persistence.DAO.ProductDAO;
import com.antontulskih.persistence.Implementation.Collection.ProductDAO_Impl_Coll;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.System.out;
import static com.antontulskih.util.ProductComparator.IdSorterComparator;

public class ProductDAO_Impl_XML implements ProductDAO {

    protected ProductDAO_Impl_Coll productDAOImplColl =
            ProductDAO_Impl_Coll.getProductDAOCollImpl();

    @Override
    public boolean save(final Product... products) {
        for(Product p: products) {
            productDAOImplColl.save(p);
        }
        return writeToFile();
    }

    public boolean writeToFile() {
        final String fileName = "productList.xml";
        out.println("\n*** Saving list of products to " + fileName + " ***");
        try {
            XStream xStream = new XStream();
            xStream.alias("Product", Product.class);
            xStream.toXML(productDAOImplColl.getProductList(),
                    new FileOutputStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Set<Product> readFromFile() {
        Set<Product> newProductList =
                new TreeSet<Product>(new IdSorterComparator());
        final String fileName = "productList.xml";
        out.println("\n*** Loading list of products from " + fileName
                + " ***");
        try {
            XStream xStream = new XStream();
            xStream.alias("Product", Product.class);
            newProductList.addAll((Set<Product>) xStream.fromXML(
                    new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return newProductList;
    }

    @Override
    public boolean remove(final Product... products) {
        for(Product p: products) {
            productDAOImplColl.remove(p);
        }
        return writeToFile();
    }

    @Override
    public boolean removeById(final Integer... ids) {
        for (Integer i: ids) {
            productDAOImplColl.removeById(i);
        }
        return writeToFile();
    }

    @Override
    public Set<Product> getByName(final String... names) {
            return productDAOImplColl.getByName(names);
    }

    @Override
    public Product getByName(final String name) {
        return productDAOImplColl.getByName(name);
    }

    @Override
    public Set<Product> getById(final Integer... ids) {
            return productDAOImplColl.getById(ids);
    }

    @Override
    public Product getById(final Integer id) {
        return productDAOImplColl.getById(id);
    }

    @Override
    public boolean update(Product... products) {
        return productDAOImplColl.update(products);
    }

    @Override
    public void showAllById() {
        productDAOImplColl.showAllById();
    }

    @Override
    public void showAllByName() {
        productDAOImplColl.showAllByName();

    }

    @Override
    public void showAllByPrice() {
        productDAOImplColl.showAllByPrice();
    }

}

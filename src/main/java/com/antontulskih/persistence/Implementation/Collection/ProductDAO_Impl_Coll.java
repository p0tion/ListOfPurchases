/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.persistence.Implementation.Collection;

import com.antontulskih.domain.Product;
import com.antontulskih.persistence.DAO.ProductDAO;
import com.antontulskih.util.MyLogger;

import java.util.Set;
import java.util.TreeSet;

import static com.antontulskih.util.ProductComparator.IdSorterComparator;
import static com.antontulskih.util.ProductComparator.NameSorterComparator;

public class ProductDAO_Impl_Coll implements ProductDAO {

    static final MyLogger LOGGER = new MyLogger(ProductDAO_Impl_Coll.class);
    private static Integer id = 1;
    private static Set<Product> productList =
            new TreeSet<Product>(new IdSorterComparator());

    public static ProductDAO_Impl_Coll productDAOCollImpl;

    private ProductDAO_Impl_Coll() { }

    public static ProductDAO_Impl_Coll getProductDAOCollImpl() {
        if (productDAOCollImpl == null) {
            productDAOCollImpl = new ProductDAO_Impl_Coll();
        }
        return productDAOCollImpl;
    }

    public static Set<Product> getProductList() { return productList; }

    @Override
    public boolean save(final Product... products) {
        for (Product p: products) {
            if (p == null) {
                throw new IllegalArgumentException(String.format("Input "
                        + "product cannot be null: %s.", p));
            }
            if (productList.size() == 0) {
                p.setId(id++);
                productList.add(p);
            } else {
                for (Product d: productList) {
                    if (p.getName().equals(d.getName())) {
                        String errorMessage = String.format("Product with such"
                                + " name already exists: %s. ID - %d",
                                d.getName(), d.getId());
                        throw new IllegalArgumentException(errorMessage);
                    }
                }
                p.setId(id++);
                productList.add(p);
            }
            LOGGER.info(String.format("*** %s has been saved. ID - %d",
                    p.getName(), p.getId()));
        }
        return true;
    }

    @Override
    public Set<Product> getByIds(final Integer... ids) {
        Set<Product> set= new TreeSet<Product>(new IdSorterComparator());
        for (Integer i: ids) {
            set.add(getById(i));
        }
        return set;
    }

    @Override
    public Product getById(final Integer id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("ID cannot be null and should "
                    + "be greater than 0. Your ID: " + id);
        }
        for (Product p: productList) {
            if (p.getId().equals(id)) {
                LOGGER.info(String.format("*** Getting product with ID %d from "
                        + "the list of products ***", id));
                return p;
            } else {
                throw new IllegalArgumentException("There is no product with "
                        + "such ID: " + id);
            }
        }
        return null;
    }

    @Override
    public Set<Product> getAllSortedById() {
        LOGGER.info(String.format("%n*** Getting all products from the list "
                + "ordered by ID ***"));
        return productList;
    }

    @Override
    public Set<Product> getAllSortedByName() {
        LOGGER.info(String.format("%n*** Getting all products from the list "
                + "ordered by name ***"));
        Set<Product> nameSortedProductList = new TreeSet<Product>(
                new NameSorterComparator());
        nameSortedProductList.addAll(productList);
        return nameSortedProductList;
    }

    @Override
    public Set<Product> getAllSortedByPrice() {
        LOGGER.info(String.format("%n*** Getting all products from the list "
                + "ordered by price ***"));
        Set<Product> priceSortedProductList = new TreeSet<Product>(
                new NameSorterComparator());
        priceSortedProductList.addAll(productList);
        return priceSortedProductList;
    }

    @Override
    public boolean update(final Product... products) {
        for (Product p: products) {
            if (p == null) {
                throw new IllegalArgumentException("Input product cannot be "
                        + "null. Your product: " + p);
            }
            for (Product d: productList) {
                if (p.getId().equals(d.getId())) {
                    LOGGER.info(String.format("*** Updating %s ***",
                            p.getName()));
                    d.setName(p.getName());
                    d.setDescription(p.getDescription());
                    d.setPrice(p.getPrice());
                }
            }
        }
        return false;
    }

    @Override
    public boolean remove(final Product... products) {
        for (Product p: products) {
            if (p == null) {
                throw new IllegalArgumentException("Input product cannot be "
                        + "null. Your product: " + p);
            }
            if (productList.contains(p)) {
                LOGGER.info(String.format("*** %s has been removed from the "
                        + "list of products ***", p.getName()));
                productList.remove(p);
            } else {
                throw new IllegalArgumentException("There is no product with "
                        + "such name: " + p.getName());
            }
        }
        return true;
    }

    @Override
    public boolean removeByIds(final Integer... ids) {
        Product productArray[] = new Product[ids.length];
        int count = 0;
        for (Integer i: ids) {
            if (i == null || i < 1) {
                throw new IllegalArgumentException("ID cannot be null and "
                        + "should be greater than 0. Your ID: " + i);
            }
            boolean isInList = false;
            for (Product p: productList) {
                if (p.getId().equals(i)) {
                    isInList = true;
                    productArray[count++] = p;
                }
            }
            if (!isInList) {
                throw new IllegalArgumentException("There is no product with "
                        + "such ID: " + i);
            }
        }
        remove(productArray);
        return false;
    }

    @Override
    public boolean removeAll() {
        LOGGER.info("*** Removing all products from the list of products ***");
        productList.clear();
        return true;
    }

    @Override
    public Set<Product> getByNames(final String... names) {
        Set<Product> set = new TreeSet<Product>(new IdSorterComparator());
        for (String n: names) {
        set.add(productDAOCollImpl.getByName(n));
        }
        return set;
    }

    @Override
    public Product getByName(final String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Input name cannot be null or "
                    + "blank. Your name: " + name);
        }
        boolean isInList = false;
        for (Product p: productList) {
            if (p.getName().equals(name)) {
                LOGGER.info(String.format("*** Getting %s ***", p.getName()));
                return p;
            }
        }
        if (!isInList) {
            throw new IllegalArgumentException("There is no product with "
                    + "such name: " + name);
        }
        return null;
    }

}

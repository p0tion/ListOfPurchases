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

import java.util.Set;
import java.util.TreeSet;

import static com.antontulskih.util.ProductComparator.IdSorterComparator;
import static com.antontulskih.util.ProductComparator.NameSorterComparator;
import static java.lang.System.out;

public class ProductDAO_Impl_Coll implements ProductDAO {
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
            if (productList.size() == 0) {
                p.setId(id++);
                productList.add(p);
                out.println("*** " + p.getName()
                        + " has been saved. ID - "
                        + p.getId() + " ***");
            } else {
                for (Product d: productList) {
                    if (p.getName().equals(d.getName())) {
                        throw new IllegalArgumentException("Product with such"
                                + " name already exists:"
                                + d.getName() + " "
                                + "ID - " + d.getId());
                    }
                }
                p.setId(id++);
                productList.add(p);
                out.println("*** " + p.getName()
                        + " "
                        + " has been saved. ID - "
                        + p.getId() + " ***");
            }
        }
        return true;
    }

    @Override
    public Set<Product> getByIds(final Integer... ids) {
        Set<Product> set= new TreeSet<Product>(new IdSorterComparator());
        for (Integer i: ids) {
            boolean isInList = false;
            for (Product p: productList) {
                if (p.getId().equals(i)) {
                    out.println("*** Getting product with ID "
                            + i + " from the list of products ***");
                    isInList = true;
                    set.add(p);
                }
            }
            if (!isInList) {
                throw new IllegalArgumentException("There is no product with "
                        + "such ID: " + i);
            }
        }
        return set;
    }

    @Override
    public Product getById(final Integer id) {
        for (Product p: productList) {
            if (p.getId().equals(id)) {
                out.println("*** Getting product with ID "
                        + id + " from the list of products ***");
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
        out.println("\n*** Getting all products from the list ordered "
                + "by ID ***");
        return productList;
    }

    @Override
    public Set<Product> getAllSortedByName() {
        out.println("\n*** Getting all products from the list ordered "
                + "by name ***");
        Set<Product> nameSortedProductList = new TreeSet<Product>(
                new NameSorterComparator());
        nameSortedProductList.addAll(productList);
        return nameSortedProductList;
    }

    @Override
    public Set<Product> getAllSortedByPrice() {
        out.println("\n*** Getting all products from the list ordered "
                + "by price ***");
        Set<Product> priceSortedProductList = new TreeSet<Product>(
                new NameSorterComparator());
        priceSortedProductList.addAll(productList);
        return priceSortedProductList;
    }

    @Override
    public boolean update(final Product... products) {
        for (Product p: products) {
            for (Product d: productList) {
                if (p.getId().equals(d.getId())) {
                    out.println("*** Updating "
                            + p.getName() + " ***");

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
            if (productList.contains(p)) {
                out.println("*** " + p.getName() + " has been removed from "
                        + " the list of products ***");
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
        out.println("*** Removing all products from the list of products ***");
        productList.clear();
        return true;
    }

    @Override
    public Set<Product> getByNames(final String... names) {
        Set<Product> set = new TreeSet<Product>(new IdSorterComparator());
        for (String n: names) {
            boolean isInList = false;
            for (Product p: productList) {
                if (p.getName().equals(n)) {
                    out.println("*** Getting " + p.getName() + " from the list "
                            + "of products ***");
                    isInList = true;
                    set.add(p);
                }
            }
            if (!isInList) {
                throw new IllegalArgumentException("There is no product with "
                        + "such name: " + n);
            }
        }
        return set;
    }

    @Override
    public Product getByName(final String name) {
        boolean isInList = false;
        for (Product p: productList) {
            if (p.getName().equals(name)) {
                out.println("*** Getting " + p.getName() + " ***");
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

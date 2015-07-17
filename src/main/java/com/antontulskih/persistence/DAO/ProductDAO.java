/**
 * @{NAME}
 *
 * ${DATE}
 *
 * @author Tulskih Anton
 */

package com.antontulskih.persistence.DAO;

import com.antontulskih.domain.Product;

import java.util.Set;

public interface ProductDAO extends AbstarctDAO<Product> {
    //    delete products
    boolean remove(final Product... products);
    //    get by name
    Set<Product> getByName(final String... names);
    Product getByName(final String name);
    //    show the full list of orders by customers last name
    void showAllByName();
    //    show the full list of orders by invoice
    void showAllByPrice();
}

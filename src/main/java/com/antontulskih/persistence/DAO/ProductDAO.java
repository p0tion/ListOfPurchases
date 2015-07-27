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

    Set<Product> getByNames(final String... names);
    Product getByName(final String name);
    Set<Product> getAllSortedByName();
    Set<Product> getAllSortedByPrice();
}

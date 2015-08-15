package com.antontulskih.service;

import com.antontulskih.domain.Product;

import java.util.Set;

/**
* @author Tulskih Anton
* @{NAME} 20.07.2015
*/
public interface ProductService {

    public boolean save(final Product... products);

    public Set<Product> getAllSortedById();

    public Set<Product> getAllSortedByName();

    public Set<Product> getAllSortedByPrice();

    public Product getByName(final String name);

    public boolean remove(final Product... products);

    public boolean removeByIds(final Integer... ids);

    public boolean removeAll();

    public Set<Product> getByIds(final Integer... ids);

    public Product getById(final Integer id);

    public boolean update(final Product... products);
}

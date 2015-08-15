package com.antontulskih.service.Impl;

import com.antontulskih.domain.Product;
import com.antontulskih.persistence.DAO.ProductDAO;
import com.antontulskih.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author Tulskih Anton
 * @{NAME} 20.07.2015
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Transactional
    public boolean save(final Product... products) {
        return productDAO.save(products);
    }

    @Transactional
    public Set<Product> getAllSortedById() {
        return productDAO.getAllSortedById();
    }

    @Transactional
    public Set<Product> getAllSortedByName() {
        return productDAO.getAllSortedByName();
    }

    @Transactional
    public Set<Product> getAllSortedByPrice() {
        return productDAO.getAllSortedByPrice();
    }

    @Transactional
    public Product getByName(final String name) {
        return productDAO.getByName(name);
    }

    @Transactional
    public boolean remove(final Product... products) {
        return productDAO.remove(products);
    }

    @Transactional
    public boolean removeByIds(final Integer... ids) {
        return productDAO.removeByIds(ids);
    }

    @Transactional
    public boolean removeAll() {
        return productDAO.removeAll();
    }

    @Transactional
    public Set<Product> getByIds(final Integer... ids){
        return productDAO.getByIds(ids);
    }

    @Transactional
    public Product getById(final Integer id){
        return productDAO.getById(id);
    }

    @Transactional
    public boolean update(final Product... products){
        return productDAO.update(products);
    }
}

package com.antontulskih.service;

import com.antontulskih.domain.Product;
import com.antontulskih.persistence.DAO.ProductDAO;
import com.antontulskih.persistence.Implementation.Hibernate.ProductDAO_Impl_Hibernate;

import java.util.Set;

/**
* @author Tulskih Anton
* @{NAME} 20.07.2015
*/
public class ProductService {
    private ProductDAO productDAO = new ProductDAO_Impl_Hibernate();

    public boolean save(Product... products) {
        return productDAO.save(products);
    }

    public Set<Product> getAllSortedById() {
        return productDAO.getAllSortedById();
    }

    public Set<Product> getAllSortedByName() {
        return productDAO.getAllSortedByName();
    }

    public Set<Product> getAllSortedByPrice() {
        return productDAO.getAllSortedByPrice();
    }

    public Product getByName(String name) {
        return productDAO.getByName(name);
    }

    public boolean remove(Product... products) {
        return productDAO.remove(products);
    }

    public boolean removeByIds(Integer... ids) {
        return productDAO.removeByIds(ids);
    }

    public boolean removeAll() {
        return productDAO.removeAll();
    }

    public Set<Product> getByIds(Integer... ids){
        return productDAO.getByIds(ids);
    }

    public Product getById(Integer id){
        return productDAO.getById(id);
    }

    public boolean update(Product... products){
        return productDAO.update(products);
    }

}

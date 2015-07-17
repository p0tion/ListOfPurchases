package com.antontulskih.persistence.DAO_Factory;

import com.antontulskih.persistence.DAO.CustomerDAO;
import com.antontulskih.persistence.DAO.ProductDAO;

/**
 * @author Tulskih Anton
 * @{NAME} 15.07.2015
 */
public interface DAO_AbstractFactory {
    CustomerDAO getCustomerDAO();
    ProductDAO getProductDAO();
}

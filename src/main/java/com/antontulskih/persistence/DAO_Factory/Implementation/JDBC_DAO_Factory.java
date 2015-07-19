/**
 * @{NAME}
 *
 * ${DATE}
 *
 * @author Tulskih Anton
 */

package com.antontulskih.persistence.DAO_Factory.Implementation;

import com.antontulskih.persistence.DAO.CustomerDAO;
import com.antontulskih.persistence.DAO.ProductDAO;
import com.antontulskih.persistence.DAO_Factory.DAO_AbstractFactory;
import com.antontulskih.persistence.Implementation.JDBC.CustomerDAO_Impl_JDBC;
import com.antontulskih.persistence.Implementation.JDBC.ProductDAO_Impl_JDBC;

public class JDBC_DAO_Factory implements DAO_AbstractFactory {
    @Override
    public CustomerDAO getCustomerDAO() {
        return new CustomerDAO_Impl_JDBC();
    }

    @Override
    public ProductDAO getProductDAO() { return new ProductDAO_Impl_JDBC(); }
}

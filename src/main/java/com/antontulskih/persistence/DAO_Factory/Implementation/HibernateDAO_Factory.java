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
import com.antontulskih.persistence.Implementation.Hibernate.CustomerDAO_Impl_Hibernate;
import com.antontulskih.persistence.Implementation.Hibernate.ProductDAO_Impl_Hibernate;

public class HibernateDAO_Factory implements DAO_AbstractFactory {
    @Override
    public CustomerDAO getCustomerDAO() {
        return new CustomerDAO_Impl_Hibernate();
    }

    @Override
    public ProductDAO getProductDAO() {
        return new ProductDAO_Impl_Hibernate();
    }
}

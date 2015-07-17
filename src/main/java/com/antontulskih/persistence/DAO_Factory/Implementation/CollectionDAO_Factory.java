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
import com.antontulskih.persistence.Implementation.Collection.CustomerDAO_Impl_Coll;
import com.antontulskih.persistence.Implementation.Collection.ProductDAO_Impl_Coll;

public class CollectionDAO_Factory implements DAO_AbstractFactory {
    @Override
    public CustomerDAO getCustomerDAO() {
        return CustomerDAO_Impl_Coll.getCustomerDAOCollImpl();
    }

    @Override
    public ProductDAO getProductDAO() {
        return ProductDAO_Impl_Coll.getProductDAOCollImpl();
    }
}

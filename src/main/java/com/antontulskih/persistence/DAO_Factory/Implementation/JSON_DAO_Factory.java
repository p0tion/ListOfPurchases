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
import com.antontulskih.persistence.Implementation.JSON.CustomerDAO_Impl_JSON;
import com.antontulskih.persistence.Implementation.JSON.ProductDAO_Impl_JSON;

public class JSON_DAO_Factory implements DAO_AbstractFactory {
    @Override
    public CustomerDAO getCustomerDAO() {
        return new CustomerDAO_Impl_JSON();
    }

    @Override
    public ProductDAO getProductDAO() { return new ProductDAO_Impl_JSON(); }
}

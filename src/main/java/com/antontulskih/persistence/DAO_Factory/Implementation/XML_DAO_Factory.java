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
import com.antontulskih.persistence.Implementation.XML.CustomerDAO_Impl_XML;
import com.antontulskih.persistence.Implementation.XML.ProductDAO_Impl_XML;

public class XML_DAO_Factory implements DAO_AbstractFactory {
    @Override
    public CustomerDAO getCustomerDAO() {
        return new CustomerDAO_Impl_XML();
    }

    @Override
    public ProductDAO getProductDAO() { return new ProductDAO_Impl_XML(); }
}

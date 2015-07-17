/**
 * @{NAME}
 *
 * ${DATE}
 *
 * @author Tulskih Anton
 */

package com.antontulskih.persistence.DAO;

import com.antontulskih.domain.Customer;

public interface CustomerDAO extends AbstarctDAO<Customer> {

    //    get by name
    Customer getByName(final String firstName, final String lastName);
    //    show the full list of orders by customers last name
    void showAllByLastName();
    //    show the full list of orders by invoice
    void showAllByInvoice();
}

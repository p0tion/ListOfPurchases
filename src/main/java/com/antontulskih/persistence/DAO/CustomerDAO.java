/**
 * @{NAME}
 *
 * ${DATE}
 *
 * @author Tulskih Anton
 */

package com.antontulskih.persistence.DAO;

import com.antontulskih.domain.Customer;

import java.util.Set;

public interface CustomerDAO extends AbstarctDAO<Customer> {

    Customer getByName(final String firstName, final String lastName);
    Set<Customer> getAllSortedByLastName();
    Set<Customer> getAllSortedByInvoice();
    Customer getByLoginAndPassword(final String login, final String password);
    Customer getByLogin(final String login);
    Customer getByCardNumber(final String cardNumber);
}

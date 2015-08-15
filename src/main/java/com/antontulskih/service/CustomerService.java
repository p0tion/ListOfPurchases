package com.antontulskih.service;

import com.antontulskih.domain.Customer;

import java.util.Set;

/**
* @author Tulskih Anton
* @{NAME} 20.07.2015
*/
public interface CustomerService {

    public boolean save(final Customer... customers);

    public Set<Customer> getAllSortedById();

    public Set<Customer> getAllSortedByLastName();

    public Set<Customer> getAllSortedByInvoice();

    public Customer getByName(final String firstName, final String lastName);

    public Customer getByLoginAndPassword(final String login,
                                          final String password);

    public boolean remove(final Customer... customers);

    public boolean removeByIds(final Integer... ids);

    public boolean removeAll();

    public Set<Customer> getByIds(final Integer... ids);

    public Customer getById(final Integer id);

    public boolean update(final Customer... customers);
}

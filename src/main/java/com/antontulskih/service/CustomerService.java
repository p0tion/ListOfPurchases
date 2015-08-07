package com.antontulskih.service;

import com.antontulskih.domain.Customer;
import com.antontulskih.persistence.DAO.CustomerDAO;
import com.antontulskih.persistence.Implementation.Hibernate.CustomerDAO_Impl_Hibernate;

import java.util.Set;

/**
* @author Tulskih Anton
* @{NAME} 20.07.2015
*/
public class CustomerService {
    private CustomerDAO customerDAO = new CustomerDAO_Impl_Hibernate();

    public boolean save(Customer... customers) {
        return customerDAO.save(customers);
    }

    public Set<Customer> getAllSortedById() {
        return customerDAO.getAllSortedById();
    }

    public Set<Customer> getAllSortedByLastName() {
        return customerDAO.getAllSortedByLastName();
    }

    public Set<Customer> getAllSortedByInvoice() {
        return customerDAO.getAllSortedByInvoice();
    }

    public Customer getByName(String firstName, String lastName) {
        return customerDAO.getByName(firstName, lastName);
    }

    public boolean remove(Customer... customers) {
        return customerDAO.remove(customers);
    }

    public boolean removeByIds(Integer... ids) {
        return customerDAO.removeByIds(ids);
    }

    public boolean removeAll() {
        return customerDAO.removeAll();
    }

    public Set<Customer> getByIds(Integer... ids){
        return customerDAO.getByIds(ids);
    }

    public Customer getById(Integer id){
        return customerDAO.getById(id);
    }

    public boolean update(Customer... customers){
        return customerDAO.update(customers);
    }

}

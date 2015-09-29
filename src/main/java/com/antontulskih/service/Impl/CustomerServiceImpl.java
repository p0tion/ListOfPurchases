package com.antontulskih.service.Impl;

import com.antontulskih.domain.Customer;
import com.antontulskih.persistence.DAO.CustomerDAO;
import com.antontulskih.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author Tulskih Anton
 * @{NAME} 20.07.2015
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Transactional
    public boolean save(final Customer... customers) {
        return customerDAO.save(customers);
    }

    @Transactional
    public Set<Customer> getAllSortedById() {
        return customerDAO.getAllSortedById();
    }

    @Transactional
    public Set<Customer> getAllSortedByLastName() {
        return customerDAO.getAllSortedByLastName();
    }

    @Transactional
    public Set<Customer> getAllSortedByInvoice() {
        return customerDAO.getAllSortedByInvoice();
    }

    @Transactional
    public Customer getByName(final String firstName, final String lastName) {
        return customerDAO.getByName(firstName, lastName);
    }

    @Transactional
    public Customer getByLoginAndPassword(final String login,
                                          final String password) {
        return customerDAO.getByLoginAndPassword(login, password);
    }

    @Transactional
    public boolean remove(final Customer... customers) {
        return customerDAO.remove(customers);
    }

    @Transactional
    public boolean removeByIds(final Integer... ids) {
        return customerDAO.removeByIds(ids);
    }

    @Transactional
    public boolean removeAll() {
        return customerDAO.removeAll();
    }

    public Set<Customer> getByIds(final Integer... ids){
        return customerDAO.getByIds(ids);
    }

    @Transactional
    public Customer getById(final Integer id){
        return customerDAO.getById(id);
    }

    @Transactional
    public boolean update(final Customer... customers){
        return customerDAO.update(customers);
    }

    @Transactional
    public boolean updateAll(){
        return customerDAO.updateAll();
    }
}

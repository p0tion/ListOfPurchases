package com.antontulskih.persistence.Implementation.Hibernate;

import com.antontulskih.domain.Customer;
import com.antontulskih.persistence.DAO.CustomerDAO;
import com.antontulskih.util.CustomerComparator.LastNameSorterComparator;
import com.antontulskih.util.MyLogger;
import com.antontulskih.util.PasswordEncryptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.antontulskih.util.CustomerComparator.IdSorterComparator;
import static com.antontulskih.util.CustomerComparator.InvoiceSorterComparator;

/**
* @author Tulskih Anton
* @{NAME} 27.07.2015
*/
@Repository
public class CustomerDAO_Impl_Hibernate implements CustomerDAO {

    static final MyLogger LOGGER = new MyLogger(CustomerDAO_Impl_Hibernate
            .class);
    static final String ROLLBACK_EXC_MSG = "Couldn’t roll back transaction";

    static final PasswordEncryptor PASSWORD_ENCRYPTOR = new PasswordEncryptor();

    @Autowired
    private SessionFactory sf;

    @Override
    public Customer getByName(final String firstName, final String lastName) {
        Session session = null;
        Transaction transaction = null;
        Customer customer = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Customer where firstName = "
                    + ":firstName and lastName = :lastName");
            query.setString("firstName", firstName);
            query.setString("lastName", lastName);
            customer = (Customer) query.uniqueResult();
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re){
                LOGGER.error(ROLLBACK_EXC_MSG, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customer;
    }

    @Override
    public Customer getByLoginAndPassword(final String login, final String
            password) {
        Session session = null;
        Transaction transaction = null;
        Customer customer = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Customer where login = "
                    + ":login and password = :password");
            query.setString("login", login);
            query.setString("password",
                    PASSWORD_ENCRYPTOR.getCryptString(password));
            customer = (Customer) query.uniqueResult();
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re) {
                LOGGER.error(ROLLBACK_EXC_MSG, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customer;
    }

    @Override
    public Customer getByLogin(String login) {
        Session session = null;
        Transaction transaction = null;
        Customer customer = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Customer where login = "
                    + ":login");
            query.setString("login", login);
            customer = (Customer) query.uniqueResult();
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re) {
                LOGGER.error(ROLLBACK_EXC_MSG, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customer;
    }

    @Override
    public Customer getByCardNumber(String cardNumber) {
        Session session = null;
        Transaction transaction = null;
        Customer customer = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Customer where cardNumber = "
                    + ":cardNumber");
            query.setString("cardNumber", cardNumber);
            customer = (Customer) query.uniqueResult();
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re) {
                LOGGER.error(ROLLBACK_EXC_MSG, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customer;
    }

    @Override
    public Set<Customer> getAllSortedById() {
        Session session = null;
        Transaction transaction = null;
        Set<Customer> customerList = Collections.emptySet();
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            List<Customer> list = session.createQuery("from Customer").list();
            customerList = new TreeSet<Customer>(
                    new IdSorterComparator());
            customerList.addAll(list);
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re) {
                LOGGER.error(ROLLBACK_EXC_MSG, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customerList;
    }

    @Override
    public Set<Customer> getAllSortedByLastName() {
        Session session = null;
        Transaction transaction = null;
        Set<Customer> customerList = Collections.emptySet();
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            List<Customer> list = session.createQuery("from Customer").list();
            customerList = new TreeSet<Customer>(
                    new LastNameSorterComparator());
            customerList.addAll(list);
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re) {
                LOGGER.error(ROLLBACK_EXC_MSG, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customerList;
    }

    @Override
    public Set<Customer> getAllSortedByInvoice() {
        Session session = null;
        Transaction transaction = null;
        Set<Customer> customerList = Collections.emptySet();
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            List<Customer> list = session.createQuery("from Customer").list();
            customerList = new TreeSet<Customer>(
                    new InvoiceSorterComparator());
            customerList.addAll(list);
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re) {
                LOGGER.error(ROLLBACK_EXC_MSG, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customerList;
    }


    @Override
    public boolean save(final Customer... customers) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            for (Customer c: customers) {
                c.setPassword(PASSWORD_ENCRYPTOR.getCryptString(c.getPassword()));
                c.setId((Integer) session.save(c));
            }
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re) {
                LOGGER.error(ROLLBACK_EXC_MSG, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return true;
    }

    @Override
    public Customer getById(final Integer id) {
        Session session = null;
        Transaction transaction = null;
        Customer customer;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Customer where id = :id");
            query.setInteger("id", id);
            customer = (Customer) query.uniqueResult();
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re) {
                LOGGER.error(ROLLBACK_EXC_MSG, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customer;
    }

    @Override
    public Set<Customer> getByIds(final Integer... ids) {
        Set<Customer> customerList = new TreeSet<Customer>(
                new IdSorterComparator());
        for (Integer id: ids) {
            customerList.add(getById(id));
        }
        return customerList;
    }

    @Override
    public boolean update(final Customer... customers) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            for (Customer c: customers) {
                Double invoice = c.getInvoice();
                session.update(c);
                c.setInvoice(invoice);
            }
            transaction.commit();
        } catch (RuntimeException e){
            try {
                transaction.rollback();
            } catch(RuntimeException re) {
                LOGGER.error(ROLLBACK_EXC_MSG, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return true;
    }

    @Override
    public boolean updateAll() {
        Session session = null;
        Transaction transaction = null;
        Set<Customer> customerList = Collections.EMPTY_SET;
        Customer customer;
        try {
            session = sf.openSession();
            customerList = getAllSortedById();
            transaction = session.beginTransaction();
            for (Customer c: customerList) {
                customer = new Customer();
                customer.setId(c.getId());
                customer.setFirstName(c.getFirstName());
                customer.setLastName(c.getLastName());
                customer.setCardNumber(c.getCardNumber());
                customer.setInvoice(0.0);
                customer.setLogin(c.getLogin());
                customer.setPassword(c.getPassword());
                customer.addProductToShoppingBasket(c.getShoppingBasket());
                session.update(customer);
            }
            transaction.commit();
        } catch (RuntimeException e){
            try {
                transaction.rollback();
            } catch(RuntimeException re) {
                LOGGER.error(ROLLBACK_EXC_MSG, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return true;
    }

    @Override
    public boolean remove(final Customer... customers) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            for (Customer c: customers) {
                session.delete(c);
            }
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re) {
                LOGGER.error(ROLLBACK_EXC_MSG, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return true;
    }

    @Override
    public boolean removeAll() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete from Customer");
            query.executeUpdate();
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re) {
                LOGGER.error(ROLLBACK_EXC_MSG, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return true;
    }

    @Override
    public boolean removeByIds(final Integer... ids) {
        Session session = null;
        Transaction transaction = null;
        Customer customer;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            for (Integer id: ids) {
                customer = new Customer();
                customer.setId(id);
                session.delete(customer);
            }
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException re) {
                LOGGER.error(ROLLBACK_EXC_MSG, re);
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return true;
    }
}

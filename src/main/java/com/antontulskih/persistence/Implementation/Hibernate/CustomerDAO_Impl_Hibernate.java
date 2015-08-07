package com.antontulskih.persistence.Implementation.Hibernate;

import com.antontulskih.domain.Customer;
import com.antontulskih.persistence.DAO.CustomerDAO;
import com.antontulskih.util.CustomerComparator.LastNameSorterComparator;
import com.antontulskih.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
public class CustomerDAO_Impl_Hibernate implements CustomerDAO {

    private SessionFactory sf = HibernateUtil.getSessionFactory();

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
            }catch(RuntimeException rbe){
                System.out.println("Couldn’t roll back transaction");
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
            }catch(RuntimeException rbe){
                System.out.println("Couldn’t roll back transaction");
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
            }catch(RuntimeException rbe){
                System.out.println("Couldn’t roll back transaction");
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
            }catch(RuntimeException rbe){
                System.out.println("Couldn’t roll back transaction");
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
                c.setId((Integer) session.save(c));
            }
            transaction.commit();
        } catch (RuntimeException e){
            try{
                transaction.rollback();
            }catch(RuntimeException rbe){
                System.out.println("Couldn’t roll back transaction");
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
            }catch(RuntimeException rbe){
                System.out.println("Couldn’t roll back transaction");
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
            try{
                transaction.rollback();
            }catch(RuntimeException rbe){
                System.out.println("Couldn’t roll back transaction");
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
            }catch(RuntimeException rbe){
                System.out.println("Couldn’t roll back transaction");
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
            }catch(RuntimeException rbe){
                System.out.println("Couldn’t roll back transaction");
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
            }catch(RuntimeException rbe){
                System.out.println("Couldn’t roll back transaction");
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

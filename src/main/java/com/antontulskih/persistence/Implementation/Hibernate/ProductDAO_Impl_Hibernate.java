package com.antontulskih.persistence.Implementation.Hibernate;

import com.antontulskih.domain.Product;
import com.antontulskih.persistence.DAO.ProductDAO;
import com.antontulskih.util.ProductComparator.NameSorterComparator;
import com.antontulskih.util.ProductComparator.PriceSorterComparator;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.antontulskih.util.ProductComparator.IdSorterComparator;

/**
* @author Tulskih Anton
* @{NAME} 28.07.2015
*/
@Repository
public class ProductDAO_Impl_Hibernate implements ProductDAO {

    @Autowired
    SessionFactory sf;

    @Override
    public Product getByName(final String name) {
        Session session = null;
        Transaction transaction = null;
        Product product = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Product where name = :name");
            query.setString("name", name);
            product = (Product) query.uniqueResult();
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
        return product;
    }

    @Override
    public Set<Product> getByNames(final String... names) {
        Set<Product> productList = new TreeSet<Product>(new
                IdSorterComparator());
        for (String name: names) {
            productList.add(getByName(name));
        }
        return productList;
    }

    @Override
    public Set<Product> getAllSortedById() {
        Session session = null;
        Transaction transaction = null;
        Set<Product> customerList;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            List<Product> list = session.createQuery("from Product").list();
            customerList = new TreeSet<Product>(
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
    public Set<Product> getAllSortedByName() {
        Session session = null;
        Transaction transaction = null;
        Set<Product> customerList;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            List<Product> list = session.createQuery("from Product").list();
            customerList = new TreeSet<Product>(
                    new NameSorterComparator());
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
    public Set<Product> getAllSortedByPrice() {
        Session session = null;
        Transaction transaction = null;
        Set<Product> customerList;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            List<Product> list = session.createQuery("from Product").list();
            customerList = new TreeSet<Product>(
                    new PriceSorterComparator());
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
    public boolean save(final Product... products) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            for (Product p: products) {
//                p.setId((Integer) session.save(p));
                session.saveOrUpdate(p);
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
    public Product getById(final Integer id) {
        Session session = null;
        Transaction transaction = null;
        Product product;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Product where id = :id");
            query.setInteger("id", id);
            product = (Product) query.uniqueResult();
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
        return product;
    }

    @Override
    public Set<Product> getByIds(final Integer... ids) {
        Set<Product> productList = new TreeSet<Product>(
                new IdSorterComparator());
        for (Integer id: ids) {
            productList.add(getById(id));
        }
        return productList;
    }

    @Override
    public boolean update(final Product... products) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            for (Product p: products) {
                session.update(p);
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
    public boolean remove(final Product... products) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            for (Product p: products) {
                session.delete(p);
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
            Query query = session.createQuery("delete from Product");
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
        Product product;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            for (Integer id: ids) {
                product = new Product();
                product.setId(id);
                session.delete(product);
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

/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/
package com.antontulskih.app;

import com.antontulskih.domain.Customer;
import com.antontulskih.domain.Product;
import com.antontulskih.persistence.DAO.CustomerDAO;
import com.antontulskih.persistence.DAO.ProductDAO;
import com.antontulskih.persistence.DAO_Factory.DAO_AbstractFactory;
import com.antontulskih.persistence.DAO_Factory.DAO_FactoryInitializer;
import com.antontulskih.persistence.DAO_Factory.StorageType;

import static com.antontulskih.util.CustomerFormattedTable.printListOfCustomers;
import static com.antontulskih.util.CustomerFormattedTable.printOneCustomer;
import static com.antontulskih.util.ProductFormattedTable.printListOfProducts;

final class App {

    private App() { }

    public static void main(final String[] args) {

        Product potato = new Product("Potato", "Potato description", 5.5);
        Product cucumber = new Product("Cucumber", "Cucum descr", 3.75);
        Product tomato = new Product("Tomato", "Red round tomato", 4.0);
        Product cheese = new Product("Cheese", "Stinky", 15.0);
        Product garlic = new Product("Garlic", "Vampire resistance +3", 8.21);
        Product bread = new Product("Bread", "Fresh", 7.15);
        Product nuts = new Product("Nuts", "Useful", 13.50);
        Product minWater = new Product("Mineral water", "Very mineral", 9.30);
        Product saladPepper = new Product("Salad pepper", "Red, yellow", 19.78);

        Customer customer1 = new Customer("Mike", "Cobs", "7777888899994444",
                "Mike", "Qw1234");
        Customer customer2 = new Customer("John", "Smith", "1111222233337777",
                "John", "Qw1234");
        Customer customer3 = new Customer("Tom", "Cruise", "5555999988883333",
                "Tom", "Qw1234");
        Customer customer4 = new Customer("Buz", "Lighter", "9999555511118888",
                "Buz", "Qw1234");
        Customer customer5 = new Customer("Bill", "Gates", "2222444499993333",
                "Bill", "Qw1234");
        Customer customer6 = new Customer("Jackie", "Chan", "8888777755551111",
                "Jackie", "Qw1234");
        Customer customer7 = new Customer("Celine", "Dion", "3333555544448888",
                "Celine", "Qw1234");
        Customer customer8 = new Customer("Darth", "Vader", "4444555599992222",
                "Darth", "Qw1234");

        DAO_AbstractFactory daoAbstractFactory;
        switch(args[0]) {
            case "coll":
                daoAbstractFactory =
                        DAO_FactoryInitializer.getDAO_Factory(
                                StorageType.COLLECTION
                        );
                break;
            case "xml":
                daoAbstractFactory =
                        DAO_FactoryInitializer.getDAO_Factory(StorageType.XML);
                break;
            case "ser":
                daoAbstractFactory =
                        DAO_FactoryInitializer.getDAO_Factory(
                                StorageType.SERIALIZATION
                        );
                break;
            case "json":
                daoAbstractFactory =
                        DAO_FactoryInitializer.getDAO_Factory(StorageType.JSON);
                break;
            case "jdbc":
                daoAbstractFactory =
                        DAO_FactoryInitializer.getDAO_Factory(StorageType.JDBC);
                break;
            case "hibernate":
                daoAbstractFactory =
                        DAO_FactoryInitializer.getDAO_Factory(
                                StorageType.HIBERNATE);
                break;
            default:
                daoAbstractFactory =
                        DAO_FactoryInitializer.getDAO_Factory(StorageType.JDBC);
                break;
        }

//        SessionFactory sf = HibernateUtil.getSessionFactory();
//        Session session = sf.openSession();
//        System.out.println("session opened");
//        session.beginTransaction();
//        session.save(new com.antontulskih.domain.Customer("Bryan", "SHNAPS",
//                "7777888899994444", "kuzya", "Qw1234"));
//        String hql = "from Customer where firstName = :name";
//        Query query = session.createQuery(hql);
//        query.setString("name","Bryan");
//        Customer customer = (Customer) query.uniqueResult();
//        System.out.println(customer);
//        session.getTransaction().commit();
//        session.close();
//        System.out.println("session closed");

        CustomerDAO customerDAOImpl = daoAbstractFactory.getCustomerDAO();
        ProductDAO productDAOImpl = daoAbstractFactory.getProductDAO();

        customerDAOImpl.save(customer1, customer2, customer3, customer4,
                customer5, customer6, customer7, customer8);

        printListOfCustomers(customerDAOImpl.getAllSortedById());

        productDAOImpl.save(potato, cucumber, garlic, cheese, minWater, nuts,
                tomato, bread, saladPepper);

        customer6.addProductToShoppingBasket(
                productDAOImpl.getByNames("Bread", "Nuts", "Cheese", "Tomato")
        );

        customer4.addProductToShoppingBasket(
                productDAOImpl.getAllSortedById()
        );

        customerDAOImpl.update(customer1, customer6, customer4);

        printListOfCustomers(customerDAOImpl.getAllSortedById());

        printListOfProducts(productDAOImpl.getAllSortedByPrice());
        customer5.addProductToShoppingBasket(
                productDAOImpl.getByIds(1, 3, 5)
        );
        customer5.addProductToShoppingBasket(
                productDAOImpl.getByNames("Nuts", "Salad pepper")
        );

        customer5.showShoppingBasket();

        customerDAOImpl.update(customer5);
        printListOfCustomers(customerDAOImpl.getAllSortedById());
        printOneCustomer(customer5);

        customer8.addProductToShoppingBasket(
                productDAOImpl.getByIds(1, 5, 7, 9)
        );
        customer5.clearShoppingBasket();

        customerDAOImpl.remove(customer1, customer3);
        customerDAOImpl.removeByIds(6, 7);
        customerDAOImpl.update(customer5, customer8);
        printListOfCustomers(customerDAOImpl.getAllSortedById());

        productDAOImpl.remove(bread);
        productDAOImpl.removeByIds(2, 4);
        printListOfProducts(productDAOImpl.getAllSortedByPrice());

        customerDAOImpl.remove(customer5, customer8);
        printListOfCustomers(customerDAOImpl.getAllSortedByLastName());

        productDAOImpl.removeByIds(1,3,5);
        productDAOImpl.remove(nuts, tomato, saladPepper);

        customerDAOImpl.removeByIds(4,2);

        productDAOImpl.removeAll();
        customerDAOImpl.removeAll();
        printListOfCustomers(customerDAOImpl.getAllSortedById());
        printListOfProducts(productDAOImpl.getAllSortedById());

    }
}

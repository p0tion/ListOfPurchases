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
import com.antontulskih.util.CustomerFormattedTable;

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

        Customer customer1 = new Customer("Mike", "Cobs", "7777888899994444");
        Customer customer2 = new Customer("John", "Smith", "1111222233337777");
        Customer customer3 = new Customer("Tom", "Cruise", "5555999988883333");
        Customer customer4 = new Customer("Buz", "Lighter", "9999555511118888");
        Customer customer5 = new Customer("Bill", "Gates", "2222444499993333");
        Customer customer6 = new Customer("Jackie", "Chan", "8888777755551111");
        Customer customer7 = new Customer("Celine", "Dion", "3333555544448888");
        Customer customer8 = new Customer("Darth", "Vader", "4444555599992222");

        DAO_AbstractFactory daoAbstractFactory =
                DAO_FactoryInitializer.getDAO_Factory(StorageType.COLLECTION);
        CustomerDAO customerDAOImplColl = daoAbstractFactory.getCustomerDAO();
        ProductDAO productDAOImplColl = daoAbstractFactory.getProductDAO();

        customerDAOImplColl.save(customer1, customer2, customer3, customer4,
                customer5, customer6, customer7, customer8);

        customerDAOImplColl.showAllById();

        productDAOImplColl.save(potato, cucumber, garlic, cheese, minWater,
                nuts, tomato, bread, saladPepper);

        productDAOImplColl.showAllById();

        Customer newCustomer5 = customerDAOImplColl.getById(5);
        newCustomer5.addProductToShoppingBasket(
                productDAOImplColl.getById(1,2,3)
        );
        newCustomer5.addProductToShoppingBasket(
                productDAOImplColl.getByName("Salad pepper", "Nuts")
        );

        newCustomer5.showShoppingBasket();

        customerDAOImplColl.update(newCustomer5);
        customerDAOImplColl.showAllById();
        CustomerFormattedTable.printOneCustomer(newCustomer5);

        Customer newCustomer8 =
                customerDAOImplColl.getByName("Darth", "Vader");

        newCustomer8.addProductToShoppingBasket(
                productDAOImplColl.getById(1,5,7,9,4,2)
        );
        newCustomer5.clearShoppingBasket();

        customerDAOImplColl.remove(customer1, customer3);
        customerDAOImplColl.removeById(6, 7);
        customerDAOImplColl.update(newCustomer5, newCustomer8);
        customerDAOImplColl.showAllById();

        productDAOImplColl.remove(potato, minWater);
        productDAOImplColl.removeById(2, 6);
        productDAOImplColl.showAllByPrice();
//
//        customer1.addProductToShoppingBasket(potato, tomato, garlic, bread);
//        customer4.addProductToShoppingBasket(mineralWater, cheese, saladPepper,
//                garlic, nuts);
//        customer6.addProductToShoppingBasket(garlic, cheese, cucumber);
//        customer2.addProductToShoppingBasket(garlic, tomato, bread,
//                mineralWater);
//        customer3.addProductToShoppingBasket(cheese, mineralWater, nuts);
//        customer7.addProductToShoppingBasket(tomato, cheese, garlic);
//        customer8.addProductToShoppingBasket(bread, cheese, saladPepper);
//        customer5.addProductToShoppingBasket(potato, cucumber, bread, cheese,
//                mineralWater, nuts, garlic);
//
//        CustomerDAO customerDAOImplColl = DAO_FactoryInitializer.
//                getDAO_Factory(StorageType.COLLECTION);
//        customerDAOImplColl.save(customer1, customer4, customer6,
//                customer2);
//        customerDAOImplColl.showAllById();
//        customerDAOImplColl.showAllByInvoice();
//        customerDAOImplColl.showAllByLastName();
//        printListOfCustomers(customerDAOImplColl.getByName("Anton", "Tulskih"));
//        printListOfCustomers(customerDAOImplColl.getByName("Vasya", "Pupkin"));
//        customerDAOImplColl.remove(customer1, customer4);
//        customerDAOImplColl.showAllById();
//
//        CustomerDAO customerDAOSerImpl = DAO_FactoryInitializer.
//                getDAO_Factory(StorageType.SERIALIZATION);
//        customerDAOSerImpl.save(customer3);
//        printListOfCustomers(customerDAOSerImpl.loadListOfOrders());
//        customerDAOSerImpl.remove(customer2);
//        printListOfCustomers(customerDAOSerImpl.loadListOfOrders());
//
//        CustomerDAO customerDAOXMLImpl = DAO_FactoryInitializer.
//                getDAO_Factory(StorageType.XML);
//        customerDAOXMLImpl.save(customer7, customer1);
//        printListOfCustomers(customerDAOXMLImpl.loadListOfOrders());
//        customerDAOXMLImpl.removeById(3);
//        printListOfCustomers(customerDAOXMLImpl.loadListOfOrders());
//
//        CustomerDAO customerDAOJSONImpl = DAO_FactoryInitializer.
//                getDAO_Factory(StorageType.JSON);
//        customerDAOJSONImpl.save(customer5);
//        printListOfCustomers(customerDAOJSONImpl.loadListOfOrders());
//        printListOfCustomers(customerDAOJSONImpl.getById(7,1,6,5,8,2));
//        customerDAOJSONImpl.removeById(5,7);
//        printListOfCustomers(customerDAOJSONImpl.loadListOfOrders());
//
//        CustomerDAO customerDAOJDBCImpl = CustomerDAO_Factory.
//                getDAO_Factory(StorageType.JDBC);
//        customerDAOJDBCImpl.save(customer2, customer1, customer4,
//                customer6);
//        out.println(customerDAOXMLImpl.loadListOfOrders());

    }

}

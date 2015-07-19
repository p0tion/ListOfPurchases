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
import com.antontulskih.util.ProductFormattedTable;

import static com.antontulskih.util.CustomerFormattedTable.printOneCustomer;

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
//             DAO_FactoryInitializer.getDAO_Factory(StorageType.COLLECTION);
//             DAO_FactoryInitializer.getDAO_Factory(StorageType.XML);
//             DAO_FactoryInitializer.getDAO_Factory(StorageType.SERIALIZATION);
//             DAO_FactoryInitializer.getDAO_Factory(StorageType.JSON);
             DAO_FactoryInitializer.getDAO_Factory(StorageType.JDBC);


        CustomerDAO customerDAOImpl = daoAbstractFactory.getCustomerDAO();
        ProductDAO productDAOImpl = daoAbstractFactory.getProductDAO();

        customerDAOImpl.save(customer1, customer2, customer3, customer4,
                customer5, customer6, customer7, customer8);

        customerDAOImpl.showAllById();

        productDAOImpl.save(potato, cucumber, garlic, cheese, minWater, nuts,
                tomato, bread, saladPepper);

        productDAOImpl.showAllById();

        CustomerFormattedTable.printListOfCustomers(customerDAOImpl.getAll());
        ProductFormattedTable.printListOfProducts(productDAOImpl.getAll());

        Customer newCustomer5 = customerDAOImpl.getById(5);
        newCustomer5.addProductToShoppingBasket(
                productDAOImpl.getById(1, 3, 5)
        );
        newCustomer5.addProductToShoppingBasket(
                productDAOImpl.getByName("Nuts", "Salad pepper")
        );

        newCustomer5.showShoppingBasket();

        customerDAOImpl.update(newCustomer5);
        customerDAOImpl.showAllById();
        printOneCustomer(newCustomer5);

        Customer newCustomer8 =
                customerDAOImpl.getByName("Darth", "Vader");

        newCustomer8.addProductToShoppingBasket(
                productDAOImpl.getById(1, 5, 7, 9)
        );
        newCustomer5.clearShoppingBasket();

        customerDAOImpl.remove(customer1, customer3);
        customerDAOImpl.removeById(6, 7);
        customerDAOImpl.update(newCustomer5, newCustomer8);
        customerDAOImpl.showAllById();

        productDAOImpl.remove(bread);
        productDAOImpl.removeById(2, 4);
        productDAOImpl.showAllByPrice();

        customerDAOImpl.remove(newCustomer5, newCustomer8);
        customerDAOImpl.showAllByLastName();

        productDAOImpl.removeById(1,3,5);
        productDAOImpl.remove(nuts, tomato, saladPepper);

        customerDAOImpl.removeById(4,2);

        customerDAOImpl.showAllById();
        productDAOImpl.showAllById();

//        CustomerDAO_Impl_JDBC.resetTables();

    }
}

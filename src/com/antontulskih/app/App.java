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
import com.antontulskih.persistence.Implementation.CustomerDAOCollImpl;

final class App {

    private App() { }

    public static void main(final String[] args) {

        Product potato = new Product("Картошка обыкновенная", "Описание "
                + "картохи обыкновенной", 5.5, true);
        Product cucumber = new Product("Огурец зеленый", "Ну очень зеленый "
                + "огурец", 3.75, true);
        Product tomato = new Product("Круглый красный помидор", "На самом "
                + "деле скорее овальный", 4.0, true);
        Product cheese = new Product("Сыр Голландский", "Оченб вонючий", 15.0,
                true);
        Product garlic = new Product("Чеснок", "Vampire resistance +3", 8.21,
                true);
        Product bread = new Product("Хлеб", "Свежий", 7.15, true);
        Product nuts = new Product("Орехи", "Полезные", 13.50, true);
        Product mineralWater = new Product("Минеральная вода", "Очень много "
                + "минералов", 9.30, true);
        Product saladPepper = new Product("Салатный перец", "Желтый, красный,"
                + " какой хошь", 19.78, true);
        Product.showProductList();

        Customer customer1 = new Customer("Антон", "Тульских", "0633355555",
                "3215987468487541", "anton.tulskih@gmail.com", 5);
        Customer customer2 = new Customer("Глеб", "Хлебушкин", "0505005050",
                "4444555511116666", "baton@yahoo.com", 0);
        Customer customer3 = new Customer("Раиса", "Гулькина", "0980374529",
                "8888999944448888", "rayathebest@mail.ru", 15);
        Customer customer4 = new Customer("Зинаида", "Вольф", "0989876512",
                "1111424482889099", "zzz@gmail.com", 3);
        Customer customer5 = new Customer("Эдуард", "Погожий", "0682587931",
                "1234567876543210", "edik1987@mail.ru", 0);
        Customer customer6 = new Customer("Альберт", "Гутернштерн",
                "0935487961", "5555999977778888", "albe@i.ua", 20);

        customer1.addProductToShoppingBasket(potato, tomato, garlic, bread);
        customer4.addProductToShoppingBasket(mineralWater, cheese, saladPepper,
                garlic, nuts);
        customer6.addProductToShoppingBasket(garlic, cheese, cucumber);
        customer2.addProductToShoppingBasket(garlic, tomato, bread,
                mineralWater);

        CustomerDAO customerDaoImpl =
                CustomerDAOCollImpl.getCustomerDAOCollImpl();
        customerDaoImpl.addToOrderProcessingList(customer1, customer4,
                customer6, customer2);
        customerDaoImpl.showAllOrdersById();
        customerDaoImpl.showAllOrdersByInvoice();
        customerDaoImpl.showAllOrdersByLastName();
        System.out.println(
                customerDaoImpl.getFromOrderProcessingListByName("Антон",
                        "Тульских"));
        customerDaoImpl.removeFromOrderProcessingList(customer1, customer4);
        customerDaoImpl.showAllOrdersById();
        customerDaoImpl.saveOrderProcessingList("ser");
        customerDaoImpl.saveOrderProcessingList("xml");
        customerDaoImpl.saveOrderProcessingList("json");
        System.out.println(customerDaoImpl.loadOrderProcessingList("ser"));
        System.out.println(customerDaoImpl.loadOrderProcessingList("xml"));
        System.out.println(customerDaoImpl.loadOrderProcessingList("json"));
    }

}

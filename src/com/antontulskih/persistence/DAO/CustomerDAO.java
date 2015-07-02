/**
 * @{NAME}
 *
 * ${DATE}
 *
 * @author Tulskih Anton
 */

package com.antontulskih.persistence.DAO;

import com.antontulskih.domain.Customer;

import java.util.List;

public interface CustomerDAO {
    //    add customers
    boolean addToOrderProcessingList(Customer... customer);
    //    delete them
    boolean removeFromOrderProcessingList(Customer... customer);
    //    get by name
    Customer getFromOrderProcessingListByName(String firstName,
                                              String lastName);
    //    get by id
    Customer getFromOrderProcessingListById(Long id);
    //    get all
    List<Customer> getAllListOfOrders();
    //    show the full list of orders by customers id
    void showAllOrdersById();
    //    show the full list of orders by customers last name
    void showAllOrdersByLastName();
    //    show the full list of orders by invoice
    void showAllOrdersByInvoice();
    //    save the list of orders in a file
    void saveOrderProcessingList(String saveType);
    //    load the list of orders from a file
    List<Customer> loadOrderProcessingList(String fileType);
}

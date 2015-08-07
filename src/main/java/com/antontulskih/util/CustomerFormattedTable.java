/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.util;

import com.antontulskih.domain.Customer;
import com.antontulskih.domain.Product;

import java.util.Set;
import java.util.TreeSet;

import static com.antontulskih.util.CustomerComparator.IdSorterComparator;
import static java.lang.System.out;

public final class CustomerFormattedTable {

    private CustomerFormattedTable() { }

    public static void printLine(){
        out.println("----------------------------------------------------------"
                + "------------------------------");
    }

    public static void printHeader() {
        out.printf("%3s \t %-10s  %-10s \t %-16s \t %8s \t %7s%n",
                "ID", "First Name", "Last Name", "Card Number", "Quantity",
                "Invoice");
    }

    public static void printCustomer(Customer c) {
        if (c != null) {
            out.printf("%3d \t %-10s  %-10s \t %-16s \t %8d \t %7.2f%n",
                    c.getId(), c.getFirstName(), c.getLastName(),
                    c.getCardNumber(), c.getQuantity(),
                    c.getInvoice());
        }
    }

    public static void printListOfCustomers(Set<Customer> set) {
        if (set.size() != 0) {
            printLine();
            printHeader();
            printLine();
            for (Customer c : set) {
                printCustomer(c);
            }
            printLine();
        }
    }

    public static void printOneCustomer(Customer c) {
        out.println("\n*** Displaying information about " + c.getFirstName()
                + " " + c.getLastName() + " ***");
        printLine();
        printHeader();
        printLine();
        printCustomer(c);
        printLine();
    }

    public static void main(String[] args) {

        Product potato = new Product("Potato", "Potato description", 5.5);
        potato.setId(1);
        Product cucumber = new Product("Cucumber", "Cucum descr", 3.75);
        cucumber.setId(2);
        Product tomato = new Product("Tomato", "Red round tomato", 4.0);
        tomato.setId(3);
        Product cheese = new Product("Cheese", "Stinky", 14.0);
        cheese.setId(4);
        Product garlic = new Product("Garlic", "Vampire resistance +3", 8.21);
        garlic.setId(5);

        Customer customer1 =
                new Customer("Anton", "Tulskih", "3215987468487541", "Anton",
                        "Qw1234");
        customer1.setId(1);
        Customer customer2 =
                new Customer("John", "Smith", "4444555511116666",
                        "John", "Qw1234");
        customer2.setId(2);
        Customer customer3 =
                new Customer("Mickey", "Mouse", "8888999944448888", "Mickey",
                        "Qw1234");
        customer3.setId(3);
        Customer customer4 =
                new Customer("Buz", "Lighter", "1111424482889099", "Buz",
                        "Qw1234");
        customer4.setId(4);

        customer1.addProductToShoppingBasket(cheese, garlic, potato, tomato);

        System.out.println(customer1.getQuantity());

        printOneCustomer(customer1);

        Set<Customer> set = new TreeSet<Customer>(new IdSorterComparator());
        set.add(customer1);
        set.add(customer2);
        set.add(customer3);
        set.add(customer4);
        printListOfCustomers(set);
    }
}

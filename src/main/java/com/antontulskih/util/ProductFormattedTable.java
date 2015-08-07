/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.util;

import com.antontulskih.domain.Product;

import java.util.Set;
import java.util.TreeSet;

import static com.antontulskih.util.ProductComparator.*;
import static java.lang.System.out;

public final class ProductFormattedTable {

    private ProductFormattedTable() { }

    public static void printLine(){
        out.println("--------------------------------------------------------"
          + "--------");
    }

    public static void printHeader() {
        out.printf("%3s \t %-10s \t %-28s \t %7s%n",
                "ID", "Name", "Description", "Price");
    }
    public static void printProduct(Product p) {
        if (p != null) {
            out.printf("%3d \t %-10s \t %-28s \t %7.2f%n",
                    p.getId(), p.getName(), p.getDescription(), p.getPrice());
        }
    }
    public static void printListOfProducts(Set<Product> set) {
        if (set.size() != 0) {
            printLine();
            printHeader();
            printLine();
            for (Product p : set) {
                printProduct(p);
            }
            printLine();
        }
    }

    public static void printOneProduct(Product p) {
        printLine();
        printHeader();
        printLine();
        printProduct(p);
        printLine();
    }

    public static void main(String... args) {
        Product potato = new Product("Potato", "Potato description", 5.5);
        potato.setId(1);
        Product cucumber = new Product("Cucumber", "Cucum descr", 3.75);
        cucumber.setId(2);
        Product tomato = new Product("Tomato", "Red round tomato", 4.0);
        tomato.setId(3);
        Product cheese = new Product("Cheese", "Stinky", 1554.0);
        cheese.setId(4);
        Product garlic = new Product("Garlic", "Vampire resistance +3", 8.21);
        garlic.setId(5);

        printOneProduct(garlic);

        Set<Product> set = new TreeSet<Product>(new IdSorterComparator());
        set.add(tomato);
        set.add(cucumber);
        set.add(cheese);
        set.add(potato);
        printListOfProducts(set);
    }
}

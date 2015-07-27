/**
 * @{NAME}
 *
 * ${DATE}
 *
 * @author Tulskih Anton
 */
package com.antontulskih.util;

import com.antontulskih.domain.Product;

import java.io.Serializable;
import java.util.Comparator;

public final class ProductComparator {

    private ProductComparator() { }

    public static class IdSorterComparator
            implements Comparator<Product>, Serializable {
        @Override
        public int compare(Product p1, Product p2) {
            return p1.getId() - p2.getId();
        }
    }

    public static class NameSorterComparator
            implements Comparator<Product>, Serializable {
        @Override
        public int compare(Product p1, Product p2) {
            if (p1.getName().compareTo(p2.getName()) == 0) {
                return Integer.compare(p1.getId(), p2.getId());
            } else {
                return p1.getName().compareTo(p2.getName());
            }
        }
    }

    public static class PriceSorterComparator
            implements Comparator<Product>, Serializable {
        @Override
        public int compare(Product p1, Product p2) {
            if (Double.compare(p1.getPrice(), p2.getPrice()) == 0) {
                return Integer.compare(p1.getId(), p2.getId());
            } else {
                return Double.compare(p1.getPrice(), p2.getPrice());
            }
        }
    }
}

/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.util;

import com.antontulskih.domain.Customer;

import java.io.Serializable;
import java.util.Comparator;

public final class CustomerComparator {

    private CustomerComparator() { }

    public static class IdSorterComparator
            implements Comparator<Customer>, Serializable {
        @Override
        public int compare(final Customer c1, final Customer c2) {
            return Integer.compare(c1.getId(), c2.getId());
        }
    }

    public static class LastNameSorterComparator
            implements Comparator<Customer>, Serializable {
        @Override
        public int compare(final Customer c1, final Customer c2) {
            if (c1.getLastName().compareTo(c2.getLastName()) == 0) {
                return Integer.compare(c1.getId(), c2.getId());
            } else {
                return c1.getLastName().compareTo(c2.getLastName());
            }
        }
    }

    public static class InvoiceSorterComparator
            implements Comparator<Customer>, Serializable {
        @Override
        public int compare(final Customer c1, final Customer c2) {
            if (Double.compare(c1.getInvoice(), c2.getInvoice()) == 0) {
                return Integer.compare(c1.getId(), c2.getId());
            } else {
                return Double.compare(c1.getInvoice(), c2.getInvoice());
            }
        }
    }
}

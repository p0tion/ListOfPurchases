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
            return c1.getId().compareTo(c2.getId());
        }
    }

    public static class LastNameSorterComparator
            implements Comparator<Customer>, Serializable {
        @Override
        public int compare(final Customer c1, final Customer c2) {
            return c1.getLastName().compareTo(c2.getLastName());
        }
    }

    public static class InvoiceSorterComparator
            implements Comparator<Customer>, Serializable {
        @Override
        public int compare(final Customer c1, final Customer c2) {
            return Double.compare(c1.getInvoice(), c2.getInvoice());
        }
    }
}

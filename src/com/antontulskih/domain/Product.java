/**
 * @author Tulskih Anton
 *         $ {DATE}.
 */

package com.antontulskih.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Product implements Serializable {

    private static Long uniqueId = 0L;
    private static List<Product> productsList = new ArrayList<Product>();
    private Long id;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private boolean isAvailable;

    public Product(final String productName,
                   final String productDescription,
                   final Double productPrice,
                   final boolean isAvailable) {
        super();
        this.id = ++uniqueId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.isAvailable = isAvailable;
        productsList.add(this);
    }

    public static void showProductList() {
        System.out.println("\n***Displaying the list of products***");
        for (Product p: productsList) {
            System.out.println(p);
        }
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(final String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(final Double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return String.format("%d  %s  %.2f", id, productName, productPrice);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }

        Product product = (Product) o;

        if (!productName.equals(product.productName)) {
            return false;
        }
        if (!productPrice.equals(product.productPrice)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = productName.hashCode();
        result = 31 * result + productPrice.hashCode();
        return result;
    }
}

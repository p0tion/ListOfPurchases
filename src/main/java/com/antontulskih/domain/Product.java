/**
 * @{NAME}
 *
 * ${DATE}
 *
 * @author Tulskih Anton
 */

package com.antontulskih.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public final class Product implements Serializable {

    @Id
    private Integer id;
    private String name;
    private String description;
    private Double price;

    public Product() { }

    public Product(final String name,
                   final String description,
                   final Double price) {
        super();
        setName(name);
        setDescription(description);
        setPrice(price);
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        if ((id == null) || (id <= 0)) {
            String errorMessage = String.format("Invalid id value: %d. Must be "
                    + "greater than 0.", id);
            throw new IllegalArgumentException(errorMessage);
        } else {
            this.id = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        if ((name == null) || (name.equals(""))) {
            throw new IllegalArgumentException("Products name is invalid.");
        } else {
            this.name = name;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        if (description == null) {
            throw new IllegalArgumentException("Product description is"
                    + " invalid.");
        } else {
            this.description = description;
        }
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        if ((price == null)
                || (price <= 0.0)) {
            throw new IllegalArgumentException("Products price is invalid.");
        } else {
            this.price = price;
        }
    }

    @Override
    public String toString() {
        return String.format("%s  %.2f", name, price);
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

        if (!name.equals(product.name)) {
            return false;
        }
        if (!price.equals(product.price)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}

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
@Table(name = "product_table")
public class Product implements Serializable {

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
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

    @Column(name = "name", unique = true)
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

    @Column(name = "description")
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

    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        if ((price == null)
                || (price <= 0.0)) {
            throw new IllegalArgumentException("Products price is invalid.");
        } else {
            this.price = Math.round(price * 100) / 100.0;
        }
    }

    @Override
    public String toString() {
        return String.format("Name - %s, price - %.2f", name,
                price);
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

        return name.equals(product.name) && price.equals(product.price);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}

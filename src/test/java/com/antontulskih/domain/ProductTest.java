package com.antontulskih.domain;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class ProductTest {

    private static Product product;

    @BeforeClass
    public static void setUp() {
        product = new Product();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetId_NullValue() {
        product.setId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetId_ZeroValue() {
        Integer zeroValue = 0;
        product.setId(zeroValue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetId_NegativeValue() {
        Integer negativeValue = -1;
        product.setId(negativeValue);
    }

    @Test
    public void testSetId_ValidValue() {
        final Integer expected = 1;
        final Integer validValue = 1;
        product.setId(validValue);
        final Integer actual = product.getId();
        assertEquals("Product ID", expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetName_NullString() throws Exception {
        product.setName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetName_EmptyString() throws Exception {
        product.setName("");
    }

    @Test
    public void testSetName_ValidString() throws Exception {
        final String validString = "Carrot";
        final String expected = "Carrot";
        product.setName(validString);
        final String actual = product.getName();
        assertEquals("Product name", expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDescription_NullString() throws Exception {
        product.setDescription(null);
    }

    @Test
    public void testSetDescription_ValidString() throws Exception {
        final String validString = "Bla bla bla";
        final String expected = "Bla bla bla";
        product.setDescription(validString);
        final String actual = product.getDescription();
        assertEquals("Product description", expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPrice_NullValue() throws Exception {
        product.setPrice(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPrice_NegativeValue() throws Exception {
        final Double negativePrice = -5.0;
        product.setPrice(negativePrice);
    }

    @Test
    public void testSetPrice_ValidValue() throws Exception {
        final Double validPrice = 25.3;
        final Double expected = 25.3;
        product.setPrice(validPrice);
        final Double actual = product.getPrice();
        assertEquals("Product price", expected, actual);
    }
}
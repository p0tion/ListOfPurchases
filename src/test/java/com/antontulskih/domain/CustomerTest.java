package com.antontulskih.domain;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class CustomerTest {

    private static Customer customer;

    @BeforeClass
    public static void setUp() {
        customer = new Customer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetId_NullValue() {
        customer.setId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetId_ZeroValue() {
        Integer zeroValue = 0;
        customer.setId(zeroValue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetId_NegativeValue() {
        Integer negativeValue = -1;
        customer.setId(negativeValue);
    }

    @Test
    public void testSetId_ValidValue() {
        final Integer expected = 1;
        final Integer validValue = 1;
        customer.setId(validValue);
        final Integer actual = customer.getId();
        assertEquals("Customer ID", expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCustomerFirstName_NullString() throws Exception {
            customer.setFirstName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCustomerFirstName_EmptyString() throws Exception {
        customer.setFirstName("");
    }

    @Test
    public void testSetCustomerFirstName_ValidString() throws Exception {
        final String validString = "Michael";
        final String expected = "Michael";
        customer.setFirstName(validString);
        final String actual = customer.getFirstName();
        assertEquals("Customer first name", expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCustomerLastName_NullString() throws Exception {
        customer.setLastName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCustomerLastName_EmptyString() throws Exception {
        customer.setLastName("");
    }

    @Test
    public void testSetCustomerLastName_ValidString() throws Exception {
        final String validString = "Jackson";
        final String expected = "Jackson";
        customer.setLastName(validString);
        final String actual = customer.getLastName();
        assertEquals("Customer first name", expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCustomerCardNumber_NullString() throws Exception {
        customer.setCardNumber(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCustomerCardNumber_EmptyString() throws Exception {
        customer.setCardNumber("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCustomerCardNumber_InvalidString() throws Exception {
        customer.setCardNumber("FOO");
    }

    @Test
    public void testSetCustomerCardNumber_ValidString() throws Exception {
        final String validString = "0000111122223333";
        final String expected = "0000111122223333";
        customer.setCardNumber(validString);
        final String actual = customer.getCardNumber();
        assertEquals("Customer card number", expected, actual);
    }

}

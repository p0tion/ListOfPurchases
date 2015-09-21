/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.persistence.Implementation.JDBC;

import com.antontulskih.domain.Customer;
import com.antontulskih.domain.Product;
import com.antontulskih.persistence.DAO.CustomerDAO;
import com.antontulskih.util.MyLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.antontulskih.util.CustomerComparator.*;

public final class CustomerDAO_Impl_JDBC implements CustomerDAO {

    static final MyLogger LOGGER = new MyLogger(CustomerDAO_Impl_JDBC.class);
    static final String SQL_EXC_MSG = "SQL Exception occurred";

    Connection c = null;
    final String url = "jdbc:mysql://localhost:3306/listofpurchases";
    final String user = "root";
    final String password = "qwerty";
    final String query = "SELECT \n"
                            + "product_table.product_id\n"
                            + "FROM customer_table, customer_product_table, "
                            + "product_table\n"
                            + "WHERE customer_table.customer_id "
                                + "= customer_product_table.customer_id && "
                            + "product_table.product_id = "
                                + "customer_product_table.product_id;";
    final String anotherQuery = "SELECT product_id FROM "
            + "customer_product_table WHERE customer_id=?;";

    @Override
    public boolean save(final Customer... customers) {
        PreparedStatement ps = null;
        ResultSet rs;
        try {
           c = DriverManager.getConnection(url, user, password);
            for (Customer customer: customers) {
                if (customer == null) {
                    throw new IllegalArgumentException(String.format("Input "
                            + "customer cannot be null: %s.", customer));
                }
                ps = c.prepareStatement("INSERT INTO customer_table"
                     + "(first_name, last_name, card_number, quantity, invoice,"
                     + " login, password) VALUES(?, ?, ?, ?, ?, ?, ?);");
                ps.setString(1, customer.getFirstName());
                ps.setString(2, customer.getLastName());
                ps.setString(3, customer.getCardNumber());
                ps.setInt(4, customer.getQuantity());
                ps.setDouble(5, customer.getInvoice());
                ps.setString(6, customer.getLogin());
                ps.setString(7, customer.getPassword());
                ps.executeUpdate();
                ps = c.prepareStatement("SELECT customer_id, first_name,"
                        + "last_name FROM customer_table WHERE first_name=?"
                        + "AND last_name=?;");
                ps.setString(1, customer.getFirstName());
                ps.setString(2, customer.getLastName());
                rs = ps.executeQuery();
                rs.next();
                LOGGER.info(String.format("*** %s %s has been saved to the "
                                + "list of customers. ID - %d ***",
                        rs.getString("first_name"), rs.getString("last_name"),
                        rs.getInt("customer_id")));
                customer.setId(rs.getInt("customer_id"));
                if (customer.getQuantity() != 0) {
                    saveShoppingBasket(customer);
                }
            }
        } catch (SQLException sqle) {
            LOGGER.error(SQL_EXC_MSG, sqle);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException sqle) {
                LOGGER.error(SQL_EXC_MSG, sqle);
            }
        }
        return true;
    }

    public boolean saveShoppingBasket(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException(String.format("Input customer "
                    + "cannot be null: %s.", customer));
        }
        PreparedStatement ps = null;
        try {
            for (Product product: customer.getShoppingBasket()) {
                ps = c.prepareStatement("INSERT INTO customer_product_table"
                        + "(customer_id, product_id)"
                        + "VALUES(?, ?);");
                ps.setInt(1, customer.getId());
                ps.setInt(2, product.getId());
                ps.executeUpdate();
            }
        } catch (SQLException sqle) {
            LOGGER.error(SQL_EXC_MSG, sqle);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException sqle) {
                LOGGER.error(SQL_EXC_MSG, sqle);
            }
        }
        return true;
    }

    @Override
    public boolean remove(final Customer... customers) {
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            c = DriverManager.getConnection(url, user, password);
            for (Customer customer: customers) {
                if (customer == null) {
                    throw new IllegalArgumentException(String.format
                            ("Input customer cannot be null: %s", customer));
                }
                Integer id = customer.getId();
                ps = c.prepareStatement("SELECT customer_id, first_name,"
                        + "last_name FROM customer_table WHERE customer_id=?;");
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (!rs.isBeforeFirst() ) {
                    throw new IllegalArgumentException(String.format("There "
                                    + "is no such customer in the list: %s.",
                            customer));
                }
                rs.next();
                if (customer.getQuantity() != 0) {
                    ps = c.prepareStatement("DELETE FROM customer_product_table"
                            + " WHERE customer_id=?;");
                    ps.setInt(1, id);
                    ps.execute();
                }
                ps = c.prepareStatement("DELETE FROM customer_table "
                        + "WHERE customer_id=?;");
                ps.setInt(1, id);
                ps.execute();
                LOGGER.info(String.format("*** %s %s has been removed from the "
                                + "list of customers. ID - %d ***",
                        rs.getString("first_name"), rs.getString("last_name"),
                        rs.getInt("customer_id")));
            }
        } catch (SQLException sqle) {
            LOGGER.error(SQL_EXC_MSG, sqle);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException sqle) {
                LOGGER.error(SQL_EXC_MSG, sqle);
            }
        }
        return true;
    }

    @Override
    public boolean removeByIds(final Integer... ids) {
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            c = DriverManager.getConnection(url, user, password);
            for (Integer i: ids) {
                if (i < 1) {
                    throw new IllegalArgumentException("ID must be greater "
                            + "than 0.");
                }
                ps = c.prepareStatement("SELECT customer_id, first_name, "
                        + "last_name, quantity FROM customer_table "
                        + "WHERE customer_id=?;");
                ps.setInt(1, i);
                rs = ps.executeQuery();
                if (!rs.isBeforeFirst() ) {
                    throw new IllegalArgumentException(String.format("There is no "
                                    + "customer with such ID: %d.", i));
                }
                rs.next();
                LOGGER.info(String.format("%n*** %s %s has been removed from "
                                + "the list of customers. ID - %d ***",
                        rs.getString("first_name"), rs.getString("last_name"),
                        rs.getInt("customer_id")));
                if (rs.getInt("quantity") != 0) {
                    ps = c.prepareStatement("DELETE FROM "
                            + "customer_product_table WHERE customer_id=?;");
                    ps.setInt(1, i);
                    ps.executeUpdate();
                }
                ps = c.prepareStatement("DELETE FROM customer_table "
                        + "WHERE customer_id=?;");
                ps.setInt(1, i);
                ps.executeUpdate();
            }
        } catch (SQLException sqle) {
            LOGGER.error(SQL_EXC_MSG, sqle);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException sqle) {
                LOGGER.error(SQL_EXC_MSG, sqle);
            }
        }
        return true;
    }

    @Override
    public boolean removeAll() {
        Statement st = null;
        try {
            LOGGER.info("*** Removing all customers from the list of "
                    + "customers ***");
            c = DriverManager.getConnection(url, user, password);
            st = c.createStatement();
            st.execute("DELETE FROM customer_product_table");
            st.execute("DELETE FROM customer_table");
        } catch (SQLException sqle) {
            LOGGER.error(SQL_EXC_MSG, sqle);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException sqle) {
                LOGGER.error(SQL_EXC_MSG, sqle);
            }
        }
        return true;
    }

    @Override
    public Customer getByName(final String firstName, final String lastName) {
        if (firstName == null || lastName == null
                || firstName.equals("") || lastName.equals("")) {
            throw new IllegalArgumentException(String.format("First and last "
                    + "name cannot be null or blank. First name: %s, last "
                    + "name: %s.", firstName, lastName ));
        }
        PreparedStatement ps = null;
        ResultSet rs;
        Customer customer = new Customer();
        try {
            c = DriverManager.getConnection(url, user, password);
            ps = c.prepareStatement("SELECT * FROM customer_table WHERE "
                    + "first_name=? AND last_name=?;");
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            rs = ps.executeQuery();
            if (!rs.isBeforeFirst() ) {
                throw new IllegalArgumentException(String.format("There is no "
                        + "customer with such name: %s %s",
                        firstName, lastName));
            }
            rs.next();
            LOGGER.info(String.format("*** Getting %s %s from the list of "
                            + "customers ***", rs.getString("first_name"),
                    rs.getString("last_name")));
            customer.setId(rs.getInt("customer_id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setCardNumber(rs.getString("card_number"));
            customer.setQuantity(0);
            customer.setInvoice(0.0);
            customer.setLogin(rs.getString("login"));
            customer.setPassword(rs.getString("password"));
            if (rs.getInt("quantity") != 0) {
                ProductDAO_Impl_JDBC productDAOImp = new ProductDAO_Impl_JDBC();
                List<Integer> productIdsList = new ArrayList<Integer>();
                ps = c.prepareStatement(anotherQuery);
                ps.setInt(1, customer.getId());
                ResultSet rs2 = ps.executeQuery();
                while (rs2.next()) {
                    productIdsList.add(rs2.getInt("product_id"));
                }
                customer.addProductToShoppingBasket(
                        productDAOImp.getByIds(productIdsList.toArray(new
                                Integer[productIdsList.size()]))
                );
            }
        } catch (SQLException sqle) {
            LOGGER.error(SQL_EXC_MSG, sqle);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException sqle) {
                LOGGER.error(SQL_EXC_MSG, sqle);
            }
        }
        return customer;
    }

    @Override
    public Customer getByLoginAndPassword(final String login,
                                          final String password) {
        if (login == null || password == null
                || login.equals("") || password.equals("")) {
            throw new IllegalArgumentException(String.format("login and "
                    + "password cannot be null or blank. "
                    + "Login: %s, password: %s", login, password));
        }
        PreparedStatement ps = null;
        ResultSet rs;
        Customer customer = new Customer();
        try {
            c = DriverManager.getConnection(url, user, password);
            ps = c.prepareStatement("SELECT * FROM customer_table WHERE "
                    + "login=? AND password=?;");
            ps.setString(1, login);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (!rs.isBeforeFirst() ) {
                throw new IllegalArgumentException(String.format("There is no "
                        + "customer with such login and password.%n"
                        + "Login: %s, password: %s", login, password));
            }
            rs.next();
            LOGGER.info(String.format("*** Getting %s %s from the list of "
                            + "customers ***", rs.getString("first_name"),
                    rs.getString("last_name")));
            customer.setId(rs.getInt("customer_id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setCardNumber(rs.getString("card_number"));
            customer.setQuantity(0);
            customer.setInvoice(0.0);
            customer.setLogin(rs.getString("login"));
            customer.setPassword(rs.getString("password"));
            if (rs.getInt("quantity") != 0) {
                ProductDAO_Impl_JDBC productDAOImp = new ProductDAO_Impl_JDBC();
                List<Integer> productIdsList = new ArrayList<Integer>();
                ps = c.prepareStatement(anotherQuery);
                ps.setInt(1, customer.getId());
                ResultSet rs2 = ps.executeQuery();
                while (rs2.next()) {
                    productIdsList.add(rs2.getInt("product_id"));
                }
                customer.addProductToShoppingBasket(
                        productDAOImp.getByIds(productIdsList.toArray(new
                                Integer[productIdsList.size()]))
                );
            }
        } catch (SQLException sqle) {
            LOGGER.error(SQL_EXC_MSG, sqle);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException sqle) {
                LOGGER.error(SQL_EXC_MSG, sqle);
            }
        }
        return customer;
    }

    @Override
    public Customer getByLogin(String login) {
        if (login == null || login.equals("")) {
            throw new IllegalArgumentException(String.format("login cannot be "
                    + "null or blank.%n"
                    + "Login: %s.", login));
        }
        PreparedStatement ps = null;
        ResultSet rs;
        Customer customer = new Customer();
        try {
            c = DriverManager.getConnection(url, user, password);
            ps = c.prepareStatement("SELECT * FROM customer_table WHERE "
                    + "login=?;");
            ps.setString(1, login);
            rs = ps.executeQuery();
            if (!rs.isBeforeFirst() ) {
                throw new IllegalArgumentException(String.format("There is no "
                        + "customer with such login.%n"
                        + "Login: %s.", login));
            }
            rs.next();
            LOGGER.info(String.format("*** Getting %s %s from the list of "
                            + "customers ***", rs.getString("first_name"),
                    rs.getString("last_name")));
            customer.setId(rs.getInt("customer_id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setCardNumber(rs.getString("card_number"));
            customer.setQuantity(0);
            customer.setInvoice(0.0);
            customer.setLogin(rs.getString("login"));
            customer.setPassword(rs.getString("password"));
            if (rs.getInt("quantity") != 0) {
                ProductDAO_Impl_JDBC productDAOImp = new ProductDAO_Impl_JDBC();
                List<Integer> productIdsList = new ArrayList<Integer>();
                ps = c.prepareStatement(anotherQuery);
                ps.setInt(1, customer.getId());
                ResultSet rs2 = ps.executeQuery();
                while (rs2.next()) {
                    productIdsList.add(rs2.getInt("product_id"));
                }
                customer.addProductToShoppingBasket(
                        productDAOImp.getByIds(productIdsList.toArray(new
                                Integer[productIdsList.size()]))
                );
            }
        } catch (SQLException sqle) {
            LOGGER.error(SQL_EXC_MSG, sqle);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException sqle) {
                LOGGER.error(SQL_EXC_MSG, sqle);
            }
        }
        return customer;
    }

    @Override
    public Customer getByCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.equals("")) {
            throw new IllegalArgumentException(String.format("card number "
                    + "cannot be null or blank. "
                    + "Card number: %s.", cardNumber));
        }
        PreparedStatement ps = null;
        ResultSet rs;
        Customer customer = new Customer();
        try {
            c = DriverManager.getConnection(url, user, password);
            ps = c.prepareStatement("SELECT * FROM customer_table WHERE "
                    + "card_number=?;");
            ps.setString(1, cardNumber);
            rs = ps.executeQuery();
            if (!rs.isBeforeFirst() ) {
                throw new IllegalArgumentException(String.format("There is no "
                        + "customer with such card number.%n"
                        + "Card number: %s", cardNumber));
            }
            rs.next();
            LOGGER.info(String.format("*** Getting %s %s from the list of "
                            + "customers ***", rs.getString("first_name"),
                    rs.getString("last_name")));
            customer.setId(rs.getInt("customer_id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setCardNumber(rs.getString("card_number"));
            customer.setQuantity(0);
            customer.setInvoice(0.0);
            customer.setLogin(rs.getString("login"));
            customer.setPassword(rs.getString("password"));
            if (rs.getInt("quantity") != 0) {
                ProductDAO_Impl_JDBC productDAOImp = new ProductDAO_Impl_JDBC();
                List<Integer> productIdsList = new ArrayList<Integer>();
                ps = c.prepareStatement(anotherQuery);
                ps.setInt(1, customer.getId());
                ResultSet rs2 = ps.executeQuery();
                while (rs2.next()) {
                    productIdsList.add(rs2.getInt("product_id"));
                }
                customer.addProductToShoppingBasket(
                        productDAOImp.getByIds(productIdsList.toArray(new
                                Integer[productIdsList.size()]))
                );
            }
        } catch (SQLException sqle) {
            LOGGER.error(SQL_EXC_MSG, sqle);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException sqle) {
                LOGGER.error(SQL_EXC_MSG, sqle);
            }
        }
        return customer;
    }

    @Override
    public Set<Customer> getByIds(final Integer... ids) {
        Set<Customer> set = new TreeSet<Customer>(new IdSorterComparator());
        for (Integer id: ids) {
            set.add(getById(id));
        }
        return set;
    }

    @Override
    public Customer getById(final Integer id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException(String.format("ID cannot be "
                    + "null and should be greater than 0. Your ID: %d.", id));
        }
        PreparedStatement ps = null;
        ResultSet rs;
        Customer customer = new Customer();
        try {
            c = DriverManager.getConnection(url, user, password);
            ps = c.prepareStatement("SELECT * FROM customer_table WHERE "
                    + "customer_id=?;");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (!rs.isBeforeFirst() ) {
                throw new IllegalArgumentException("There is no customer "
                        + "with such ID: " + id);
            }
            rs.next();
            LOGGER.info(String.format("*** Getting %s %s from the list of "
                            + "customers ***", rs.getString("first_name"),
                    rs.getString("last_name")));
            customer.setId(rs.getInt("customer_id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setCardNumber(rs.getString("card_number"));
            customer.setQuantity(0);
            customer.setInvoice(0.0);
            customer.setLogin(rs.getString("login"));
            customer.setPassword(rs.getString("password"));
            if (rs.getInt("quantity") != 0) {
                ProductDAO_Impl_JDBC productDAOImp = new ProductDAO_Impl_JDBC();
                List<Integer> productIdsList = new ArrayList<Integer>();
                ps = c.prepareStatement(anotherQuery);
                ps.setInt(1, customer.getId());
                ResultSet rs2 = ps.executeQuery();
                while (rs2.next()) {
                    productIdsList.add(rs2.getInt("product_id"));
                }
                customer.addProductToShoppingBasket(
                        productDAOImp.getByIds(productIdsList.toArray(new
                                Integer[productIdsList.size()]))
                );
            }
        } catch (SQLException sqle) {
            LOGGER.error(SQL_EXC_MSG, sqle);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException sqle) {
                LOGGER.error(SQL_EXC_MSG, sqle);
            }
        }
        return customer;
    }

    @Override
    public Set<Customer> getAllSortedById() {
        Set<Customer> set = new TreeSet<Customer>(new IdSorterComparator());
        PreparedStatement ps = null;
        Statement st;
        ResultSet rs;
        Customer customer;
        try {
            LOGGER.info("*** Getting all customers from the list ordered "
                    + "by ID ***");
            c = DriverManager.getConnection(url, user, password);
            st = c.createStatement();
            rs = st.executeQuery("SELECT * FROM customer_table;");
            while (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setCardNumber(rs.getString("card_number"));
                customer.setQuantity(0);
                customer.setInvoice(0.0);
                customer.setLogin(rs.getString("login"));
                customer.setPassword(rs.getString("password"));
                if (rs.getInt("quantity") != 0) {
                    ProductDAO_Impl_JDBC productDAOImp =
                            new ProductDAO_Impl_JDBC();
                    List<Integer> productIdsList = new ArrayList<Integer>();
                    ps = c.prepareStatement(anotherQuery);
                    ps.setInt(1, customer.getId());
                    ResultSet rs2 = ps.executeQuery();
                    while (rs2.next()) {
                        productIdsList.add(rs2.getInt("product_id"));
                    }
                    customer.addProductToShoppingBasket(
                            productDAOImp.getByIds(productIdsList.toArray(
                                    new Integer[productIdsList.size()]))
                    );
                }
                set.add(customer);
            }
        } catch (SQLException sqle) {
            LOGGER.error(SQL_EXC_MSG, sqle);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException sqle) {
                LOGGER.error(SQL_EXC_MSG, sqle);
            }
        }
        return set;
    }

    @Override
    public Set<Customer> getAllSortedByLastName() {
        Set<Customer> set = new TreeSet<Customer>(new LastNameSorterComparator());
        PreparedStatement ps = null;
        Statement st;
        ResultSet rs;
        Customer customer;
        try {
            LOGGER.info("*** Getting all customers from the list ordered "
                    + "by last name ***");
            c = DriverManager.getConnection(url, user, password);
            st = c.createStatement();
            rs = st.executeQuery("SELECT * FROM customer_table;");
            while (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setCardNumber(rs.getString("card_number"));
                customer.setQuantity(0);
                customer.setInvoice(0.0);
                customer.setLogin(rs.getString("login"));
                customer.setPassword(rs.getString("password"));
                if (rs.getInt("quantity") != 0) {
                    ProductDAO_Impl_JDBC productDAOImp =
                            new ProductDAO_Impl_JDBC();
                    List<Integer> productIdsList = new ArrayList<Integer>();
                    ps = c.prepareStatement(anotherQuery);
                    ps.setInt(1, customer.getId());
                    ResultSet rs2 = ps.executeQuery();
                    while (rs2.next()) {
                        productIdsList.add(rs2.getInt("product_id"));
                    }
                    customer.addProductToShoppingBasket(
                            productDAOImp.getByIds(productIdsList.toArray(
                                    new Integer[productIdsList.size()]))
                    );
                }
                set.add(customer);
            }
        } catch (SQLException sqle) {
            LOGGER.error(SQL_EXC_MSG, sqle);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException sqle) {
                LOGGER.error(SQL_EXC_MSG, sqle);
            }
        }
        return set;
    }

    @Override
    public Set<Customer> getAllSortedByInvoice() {
        Set<Customer> set = new TreeSet<Customer>(new InvoiceSorterComparator());
        PreparedStatement ps = null;
        Statement st;
        ResultSet rs;
        Customer customer;
        try {
            LOGGER.info("*** Getting all customers from the list ordered "
                    + "by invoice ***");
            c = DriverManager.getConnection(url, user, password);
            st = c.createStatement();
            rs = st.executeQuery("SELECT * FROM customer_table;");
            while (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setCardNumber(rs.getString("card_number"));
                customer.setQuantity(0);
                customer.setInvoice(0.0);
                customer.setLogin(rs.getString("login"));
                customer.setPassword(rs.getString("password"));
                if (rs.getInt("quantity") != 0) {
                    ProductDAO_Impl_JDBC productDAOImp =
                            new ProductDAO_Impl_JDBC();
                    List<Integer> productIdsList = new ArrayList<Integer>();
                    ps = c.prepareStatement(anotherQuery);
                    ps.setInt(1, customer.getId());
                    ResultSet rs2 = ps.executeQuery();
                    while (rs2.next()) {
                        productIdsList.add(rs2.getInt("product_id"));
                    }
                    customer.addProductToShoppingBasket(
                            productDAOImp.getByIds(productIdsList.toArray(
                                    new Integer[productIdsList.size()]))
                    );
                }
                set.add(customer);
            }
        } catch (SQLException sqle) {
            LOGGER.error(SQL_EXC_MSG, sqle);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException sqle) {
                LOGGER.error(SQL_EXC_MSG, sqle);
            }
        }
        return set;
    }

    @Override
    public boolean update(Customer... customers) {
        PreparedStatement ps = null;
        try {
            c = DriverManager.getConnection(url, user, password);
            for (Customer customer: customers) {
                if (customer == null) {
                    throw new IllegalArgumentException(String.format("Input "
                            + "customer cannot be null: %s.", customer));
                }
                if (customer.getId() > 0) {
                    ps = c.prepareStatement("UPDATE customer_table SET "
                            + "first_name=?, last_name=?, card_number=?, "
                            + "quantity=?, invoice=?, login=?, password=? "
                            + "WHERE customer_id=?;");
                    ps.setString(1, customer.getFirstName());
                    ps.setString(2, customer.getLastName());
                    ps.setString(3, customer.getCardNumber());
                    ps.setInt(4, customer.getQuantity());
                    ps.setDouble(5, customer.getInvoice());
                    ps.setString(6, customer.getLogin());
                    ps.setString(7, customer.getPassword());
                    ps.setInt(8, customer.getId());
                    ps.executeUpdate();
                    LOGGER.info(String.format("*** Updating %s %s ***",
                            customer.getFirstName(), customer.getLastName()));
                    ps = c.prepareStatement("DELETE FROM "
                            + "customer_product_table WHERE customer_id=?;");
                    ps.setInt(1, customer.getId());
                    ps.executeUpdate();
                    if (customer.getQuantity() != 0) {
                        for (Product product: customer.getShoppingBasket()) {
                            ps = c.prepareStatement("INSERT INTO "
                                    + "customer_product_table(customer_id, "
                                    + "product_id) VALUES(?, ?);");
                            ps.setInt(1, customer.getId());
                            ps.setInt(2, product.getId());
                            ps.executeUpdate();
                        }
                    }
                } else {
                    throw new IllegalArgumentException("There is no customer "
                            + "in the list with such ID - " + customer.getId());
                }
            }
        } catch (SQLException sqle) {
            LOGGER.error(SQL_EXC_MSG, sqle);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException sqle) {
                LOGGER.error(SQL_EXC_MSG, sqle);
            }
        }
        return true;
    }
}

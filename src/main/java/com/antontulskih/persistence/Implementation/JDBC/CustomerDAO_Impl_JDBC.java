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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.antontulskih.util.CustomerComparator.*;
import static java.lang.System.out;

public final class CustomerDAO_Impl_JDBC implements CustomerDAO {

    Connection c = null;
    final String url = "jdbc:mysql://localhost:3306/listofpurchases";
    final String user = "root";
    final String password = "qwerty";
    final String query = "SELECT \n"
                            + "product_table.product_id\n"
                            + "FROM customer_table, customer_product_table, "
                            + "product_table\n"
                            + "where customer_table.customer_id "
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
                ps = c.prepareStatement("INSERT INTO customer_table"
                     + "(first_name, last_name, card_number, quantity, invoice)"
                     + "VALUES(?, ?, ?, ?, ?);");
                ps.setString(1, customer.getFirstName());
                ps.setString(2, customer.getLastName());
                ps.setString(3, customer.getCardNumber());
                ps.setInt(4, customer.getQuantity());
                ps.setDouble(5, customer.getInvoice());
                ps.executeUpdate();
                ps = c.prepareStatement("SELECT customer_id, first_name,"
                        + "last_name FROM customer_table WHERE first_name=?"
                        + "AND last_name=?;");
                ps.setString(1, customer.getFirstName());
                ps.setString(2, customer.getLastName());
                rs = ps.executeQuery();
                rs.next();
                System.out.println("*** " + rs.getString("first_name")
                        + " "
                        + rs.getString("last_name")
                        + " has been saved to the list of customers. ID - "
                        + rs.getInt("customer_id") + " ***");
                customer.setId(rs.getInt("customer_id"));
                if (customer.getQuantity() != 0) {
                    saveShoppingBasket(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean saveShoppingBasket(Customer customer) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
                ps = c.prepareStatement("SELECT customer_id, first_name,"
                        + "last_name FROM customer_table WHERE first_name=?"
                        + "AND last_name=?;");
                ps.setString(1, customer.getFirstName());
                ps.setString(2, customer.getLastName());
                rs = ps.executeQuery();
                rs.next();
                if (customer.getQuantity() != 0) {
                    ps = c.prepareStatement("DELETE FROM customer_product_table"
                            + " WHERE customer_id=?;");
                    ps.setInt(1, customer.getId());
                    ps.execute();
                }
                ps = c.prepareStatement("DELETE FROM customer_table "
                        + "WHERE first_name=? AND last_name=?;");
                ps.setString(1, customer.getFirstName());
                ps.setString(2, customer.getLastName());
                ps.executeUpdate();
                System.out.println("*** " + rs.getString("first_name")
                        + " "
                        + rs.getString("last_name")
                        + " has been removed from the list of customers. ID - "
                        + rs.getInt("customer_id") + " ***");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
                ps = c.prepareStatement("SELECT customer_id, first_name, "
                        + "last_name, quantity FROM customer_table "
                        + "WHERE customer_id=?;");
                ps.setInt(1, i);
                rs = ps.executeQuery();
                rs.next();
                if (rs.getInt("quantity") != 0) {
                    ps = c.prepareStatement("DELETE * FROM "
                            + "customer_product_table WHERE customer_id=?;");
                    ps.setInt(1, i);
                    ps.executeUpdate();
                }
                ps = c.prepareStatement("DELETE FROM customer_table "
                        + "WHERE customer_id=?;");
                ps.setInt(1, i);
                ps.executeUpdate();
                System.out.println("*** " + rs.getString("first_name") + " "
                       + rs.getString("last_name") + " has been removed "
                       + " from the list of customers. ID - "
                       + rs.getInt("customer_id") + " ***");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean removeAll() {
        Statement st = null;
        try {
            out.println("*** Removing all customers from the list of "
                    + "customers ***");
            c = DriverManager.getConnection(url, user, password);
            st = c.createStatement();
            st.execute("DELETE FROM customer_product_table");
            st.execute("DELETE FROM customer_table");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public Customer getByName(final String firstName, final String lastName) {
        PreparedStatement ps = null;
        ResultSet rs;
        Customer customer = new Customer();
        try {
            c = DriverManager.getConnection(url, user, password);
            ps = c.prepareStatement("SELECT * FROM customer_table WHERE "
                    + "first_name=? and last_name=?;");
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            rs = ps.executeQuery();
            rs.next();
            out.println("*** Getting " + rs.getString("first_name")
                    + " " + rs.getString("last_name")
                    + " from the list of customers ***");
            customer.setId(rs.getInt("customer_id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setCardNumber(rs.getString("card_number"));
            customer.setQuantity(0);
            customer.setInvoice(0.0);
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customer;
    }

    @Override
    public Set<Customer> getByIds(final Integer... ids) {
        Set<Customer> set = new TreeSet<Customer>(new IdSorterComparator());
        PreparedStatement ps = null;
        ResultSet rs;
        Customer customer;
        try {
            c = DriverManager.getConnection(url, user, password);
            for (Integer id: ids) {
                customer = new Customer();
                ps = c.prepareStatement("SELECT * FROM customer_table WHERE "
                        + "customer_id=?;");
                ps.setInt(1, id);
                rs = ps.executeQuery();
                rs.next();
                out.println("*** Getting " + rs.getString("first_name")
                        + " " + rs.getString("last_name")
                        + " from the list of customers ***");
                customer.setId(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setCardNumber(rs.getString("card_number"));
                customer.setQuantity(0);
                customer.setInvoice(0.0);
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
                            productDAOImp.getByIds(productIdsList.toArray(new
                                    Integer[productIdsList.size()]))
                    );
                }
                set.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return set;
    }

    @Override
    public Customer getById(final Integer id) {
        PreparedStatement ps = null;
        ResultSet rs;
        Customer customer = new Customer();
        try {
            c = DriverManager.getConnection(url, user, password);
            ps = c.prepareStatement("SELECT * FROM customer_table WHERE "
                    + "customer_id=?;");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();
            out.println("*** Getting " + rs.getString("first_name")
                    + " " + rs.getString("last_name")
                    + " from the list of customers ***");
            customer.setId(rs.getInt("customer_id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setCardNumber(rs.getString("card_number"));
            customer.setQuantity(0);
            customer.setInvoice(0.0);
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
            out.println("\n*** Getting all customers from the list ordered "
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
            out.println("\n*** Getting all customers from the list ordered "
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
            out.println("\n*** Getting all customers from the list ordered "
                    + "be invoice***");
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
                if (customer.getId() > 0) {
                    ps = c.prepareStatement("UPDATE customer_table SET "
                            + "first_name=?, last_name=?, card_number=?, "
                            + "quantity=?, invoice=? WHERE customer_id=?;");
                    ps.setString(1, customer.getFirstName());
                    ps.setString(2, customer.getLastName());
                    ps.setString(3, customer.getCardNumber());
                    ps.setInt(4, customer.getQuantity());
                    ps.setDouble(5, customer.getInvoice());
                    ps.setInt(6, customer.getId());
                    ps.executeUpdate();
                    out.println("*** Updating " + customer.getFirstName()
                            + " " + customer.getLastName() + " ***");
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
                    throw new IllegalArgumentException("There is no product in "
                            + "the list with such ID - " + customer.getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}

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
import com.antontulskih.persistence.DAO.ProductDAO;

import java.sql.*;
import java.util.Set;
import java.util.TreeSet;

import static com.antontulskih.util.ProductComparator.*;
import static java.lang.System.out;

public final class ProductDAO_Impl_JDBC implements ProductDAO {

    Connection c = null;
    final String url = "jdbc:mysql://localhost:3306/listofpurchases";
    final String user = "root";
    final String password = "qwerty";


    @Override
    public boolean save(final Product... products) {
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            c = DriverManager.getConnection(url, user, password);
            for (Product product: products) {
                ps = c.prepareStatement("INSERT INTO product_table" +
                        "(name, description, price)"
                        + "VALUES(?, ?, ?);");
                ps.setString(1, product.getName());
                ps.setString(2, product.getDescription());
                ps.setDouble(3, product.getPrice());
                ps.executeUpdate();
                ps = c.prepareStatement("SELECT product_id, name "
                        + "FROM product_table WHERE name=?;");
                ps.setString(1, product.getName());
                rs = ps.executeQuery();
                rs.next();
                out.printf("%n*** %s has been saved to the list of products. "
                                + "ID - %d ***",
                        rs.getString("name"), rs.getInt("product_id"));
                product.setId(rs.getInt("product_id"));
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
    public Set<Product> getAllSortedById() {
        Set<Product> set = new TreeSet<Product>(new IdSorterComparator());
        Statement st = null;
        ResultSet rs;
        Product product;
        try {
            c = DriverManager.getConnection(url, user, password);
            out.println("\n*** Getting all products from the list ordered "
                    + "by ID ***");
            st = c.createStatement();
            rs = st.executeQuery("SELECT * FROM product_table;");
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                set.add(product);
            }
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
        return set;
    }

    @Override
    public Set<Product> getAllSortedByName() {
        Set<Product> set = new TreeSet<Product>(new NameSorterComparator());
        Statement st = null;
        ResultSet rs;
        Product product;
        try {
            c = DriverManager.getConnection(url, user, password);
            out.println("\n*** Getting all products from the list ordered "
                    + "by name ***");
            st = c.createStatement();
            rs = st.executeQuery("SELECT * FROM product_table;");
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                set.add(product);
            }
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
        return set;
    }

    @Override
    public Set<Product> getAllSortedByPrice() {
        Set<Product> set = new TreeSet<Product>(new PriceSorterComparator());
        Statement st = null;
        ResultSet rs;
        Product product;
        try {
            c = DriverManager.getConnection(url, user, password);
            out.println("\n*** Getting all products from the list ordered "
                    + "by price ***");
            st = c.createStatement();
            rs = st.executeQuery("SELECT * FROM product_table;");
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                set.add(product);
            }
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
        return set;
    }

    @Override
    public Set<Product> getByIds(final Integer... ids) {
        Set<Product> set = new TreeSet<Product>(new IdSorterComparator());
        for (Integer id: ids) {
            set.add(getById(id));
        }
        return set;
    }

    @Override
    public Product getById(final Integer id) {
        PreparedStatement ps = null;
        ResultSet rs;
        Product product = new Product();
        try {
            c = DriverManager.getConnection(url, user, password);
            ps = c.prepareStatement("SELECT * FROM product_table WHERE "
                    + "product_id=?;");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();
            out.printf("%n*** Getting %s from the list of products ***",
                    rs.getString("name"));
            product.setId(rs.getInt("product_id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
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
        return product;
    }

    @Override
    public Set<Product> getByNames(final String... names) {
        Set<Product> set = new TreeSet<Product>(new IdSorterComparator());
        for (String name: names) {
            set.add(getByName(name));
        }
        return set;
    }

    @Override
    public Product getByName(final String name) {
        PreparedStatement ps = null;
        ResultSet rs;
        Product product = new Product();
        try {
            c = DriverManager.getConnection(url, user, password);
            ps = c.prepareStatement("SELECT * FROM product_table WHERE "
                    + "name=?;");
            ps.setString(1, name);
            rs = ps.executeQuery();
            rs.next();
            out.printf("%n*** Getting %s from the list of products ***",
                    rs.getString("name"));
            product.setId(rs.getInt("product_id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
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
        return product;
    }

    @Override
    public boolean update(final Product... products) {
        PreparedStatement ps = null;
        try {
            c = DriverManager.getConnection(url, user, password);
            for (Product product: products) {
                if (product.getId() > 0) {
                    ps = c.prepareStatement("UPDATE product_table SET "
                            + " name=?, description=?, price=? WHERE "
                            + "product_id=?;");
                    ps.setString(1, product.getName());
                    ps.setString(2, product.getDescription());
                    ps.setDouble(3, product.getPrice());
                    ps.setInt(4, product.getId());
                    ps.executeUpdate();
                    out.printf("%n*** Updating %s ***", product.getName());
                } else {
                    throw new IllegalArgumentException("There is no product in "
                            + "the list with such ID - " + product.getId());
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

    @Override
    public boolean remove(final Product... products) {
        PreparedStatement ps = null;
        try {
            c = DriverManager.getConnection(url, user, password);
            for (Product product: products) {
                Integer id = product.getId();
                String name = product.getName();
                ps = c.prepareStatement("DELETE FROM customer_product_table "
                        + "WHERE product_id=?;");
                ps.setInt(1, id);
                ps.execute();
                ps = c.prepareStatement("DELETE FROM product_table WHERE "
                        + "product_id=?;");
                ps.setInt(1, id);
                ps.execute();
                out.printf("%n*** %s has been removed from the list of "
                        + "products. ID - %d ***", name, id);
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
            for (Integer id: ids) {
                ps = c.prepareStatement("SELECT name FROM product_table WHERE "
                        + "product_id=?");
                ps.setInt(1, id);
                rs = ps.executeQuery();
                rs.next();
                ps = c.prepareStatement("DELETE FROM customer_product_table "
                        + "WHERE product_id=?;");
                ps.setInt(1, id);
                ps.execute();
                ps = c.prepareStatement("DELETE FROM product_table WHERE "
                        + "product_id=?;");
                ps.setInt(1, id);
                ps.execute();
                out.printf("%n*** %s has been removed from the list of "
                        + "products. ID - %d ***", rs.getString("name"), id);
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
        CustomerDAO_Impl_JDBC customerDAOImpl = new CustomerDAO_Impl_JDBC();
        try {
            out.printf("%n*** Removing all products from the list of "
                    + "products ***");
            c = DriverManager.getConnection(url, user, password);
            Set<Customer> customerList = customerDAOImpl.getAllSortedById();
            for (Customer c: customerList) {
                c.clearShoppingBasket();
                customerDAOImpl.update(c);
            }
            st = c.createStatement();
            st.execute("DELETE FROM customer_product_table");
            st.execute("DELETE FROM product_table");
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
}

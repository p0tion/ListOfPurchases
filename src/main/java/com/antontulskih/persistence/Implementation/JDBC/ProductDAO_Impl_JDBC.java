/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.persistence.Implementation.JDBC;

import com.antontulskih.domain.Product;
import com.antontulskih.persistence.DAO.ProductDAO;

import java.sql.*;
import java.util.Set;
import java.util.TreeSet;

import static com.antontulskih.util.ProductComparator.IdSorterComparator;
import static com.antontulskih.util.ProductFormattedTable.*;
import static java.lang.System.out;

public final class ProductDAO_Impl_JDBC implements ProductDAO {

    Connection c = null;
    final String url = "jdbc:mysql://localhost:3306/listofpurchases";
    final String user = "root";
    final String password = "qwerty";


    @Override
    public boolean save(Product... products) {
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
                out.println("*** " + rs.getString("name")
                        + " has been saved to the list of products. ID - "
                        + rs.getInt("product_id") + " ***");
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
    public Set<Product> getAll() {
        Set<Product> set = new TreeSet<Product>(new IdSorterComparator());
        Statement st = null;
        ResultSet rs;
        Product product;
        try {
            c = DriverManager.getConnection(url, user, password);
            out.println("\n*** Getting all products from the list ***");
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
    public Set<Product> getById(Integer... ids) {
        Set<Product> set = new TreeSet<Product>(new IdSorterComparator());
        PreparedStatement ps = null;
        ResultSet rs;
        Product product;
        try {
            c = DriverManager.getConnection(url, user, password);
            for (Integer id: ids) {
                product = new Product();
                ps = c.prepareStatement("SELECT * FROM product_table WHERE "
                        + "product_id=?;");
                ps.setInt(1, id);
                rs = ps.executeQuery();
                rs.next();
                out.println("*** Getting " + rs.getString("name")
                        + " from the list of products ***");
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
    public Product getById(Integer id) {
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
            out.println("*** Getting " + rs.getString("name")
                    + " from the list of products ***");
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
    public Set<Product> getByName(String... names) {
        Set<Product> set = new TreeSet<Product>(new IdSorterComparator());
        PreparedStatement ps = null;
        ResultSet rs;
        Product product;
        try {
            c = DriverManager.getConnection(url, user, password);
            for (String name: names) {
                product = new Product();
                ps = c.prepareStatement("SELECT * FROM product_table WHERE "
                        + "name=?;");
                ps.setString(1, name);
                rs = ps.executeQuery();
                rs.next();
                out.println("*** Getting " + rs.getString("name")
                        + " from the list of products ***");
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
    public Product getByName(String name) {
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
            out.println("*** Getting " + rs.getString("name")
                    + " from the list of products ***");
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
    public boolean update(Product... products) {
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
                    out.println("*** Updating " + product.getName() + " ***");
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
    public boolean remove(Product... products) {
        PreparedStatement ps = null;
        try {
            c = DriverManager.getConnection(url, user, password);
            for (Product product: products) {
                ps = c.prepareStatement("DELETE FROM product_table WHERE "
                        + "product_id=?;");
                ps.setInt(1, product.getId());
                ps.execute();
                out.println("*** " + product.getName() + " has been removed "
                        + "from the list of products. ID - "
                        + product.getId() + " ***");
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
    public boolean removeById(Integer... ids) {
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
                ps = c.prepareStatement("DELETE FROM product_table WHERE "
                        + "product_id=?;");
                ps.setInt(1, id);
                ps.execute();
                out.println("*** " + rs.getString("name") + " has been "
                        + "removed from the list of products. ID - "
                        + id + " ***");
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
    public void showAllById() {
        Statement st = null;
        ResultSet rs;
        Product product = new Product();
        try {
            c = DriverManager.getConnection(url, user, password);
            out.println("\n*** Displaying the list of products sorted "
                    + "by ID ***");
            st = c.createStatement();
            rs = st.executeQuery("SELECT * FROM product_table ORDER BY "
                    + "product_id;");
            printLine();
            printHeader();
            printLine();
            while (rs.next()) {
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                printProduct(product);
            }
            printLine();
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
    }

    @Override
    public void showAllByName() {
        Statement st = null;
        ResultSet rs;
        Product product = new Product();
        try {
            c = DriverManager.getConnection(url, user, password);
            out.println("\n*** Displaying the list of products sorted "
                    + "by name ***");
            st = c.createStatement();
            rs = st.executeQuery("SELECT * FROM product_table ORDER BY "
                    + "name;");
            printLine();
            printHeader();
            printLine();
            while (rs.next()) {
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                printProduct(product);
            }
            printLine();
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
    }

    @Override
    public void showAllByPrice() {
        Statement st = null;
        ResultSet rs;
        Product product = new Product();
        try {
            c = DriverManager.getConnection(url, user, password);
            out.println("\n*** Displaying the list of products sorted "
                    + "by price ***");
            st = c.createStatement();
            rs = st.executeQuery("SELECT * FROM product_table ORDER BY "
                    + "price;");
            printLine();
            printHeader();
            printLine();
            while (rs.next()) {
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                printProduct(product);
            }
            printLine();
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
    }
}

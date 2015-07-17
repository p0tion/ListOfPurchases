/**
* @{NAME}
*
* ${DATE}
*
* @author Tulskih Anton
*/

package com.antontulskih.persistence.Implementation.JDBC;

import com.antontulskih.domain.Customer;
import com.antontulskih.persistence.DAO.CustomerDAO;

import java.sql.*;
import java.util.Set;

public final class CustomerDAO_Impl_JDBC implements CustomerDAO {

    //todo do without static
    private static Connection c = null;

    String url = "jdbc:mysql://localhost:3306/test";
    String user = "root";
    String password = "qwerty";

    @Override
    public boolean save(final Customer... customer) {
        Statement st = null;
        PreparedStatement ps = null;

        try {
            c = DriverManager.getConnection(url, user, password);
            for (Customer cus : customer) {
                ps = c.prepareStatement("INSERT INTO customertable" +
                        "(firstName, lastName, cardNumber, quantity, invoice)"
                        + "VALUES (?, ?, ?, ?, ?);");
                ps.setString(1, cus.getFirstName());
                ps.setString(2, cus.getLastName());
                ps.setString(3, cus.getCardNumber());
                ps.setInt(4, cus.getQuantity());
                ps.setDouble(5, cus.getInvoice());
                ps.executeUpdate();
                st = c.createStatement();
                System.out.println("\n*** " + cus.getFirstName()
                        + " "
                        + cus.getLastName()
                        + " has been added to the list of customers ***");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
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
    public boolean remove(final Customer... customer) {
        return false;
    }

    @Override
    public boolean removeById(final Integer... id) {
        return false;
    }

    @Override
    public Customer getByName(final String firstName, final String lastName) {
        return null;
    }

    @Override
    public Set<Customer> getById(final Integer... ids) {
        return null;
    }

    @Override
    public Customer getById(final Integer id) {
        return null;
    }

    @Override
    public boolean update(Customer... items) {
        return false;
    }

    @Override
    public void showAllById() {

    }

    @Override
    public void showAllByLastName() {

    }

    @Override
    public void showAllByInvoice() {

    }
}
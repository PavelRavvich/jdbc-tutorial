package ru.javavision;

import ru.javavision.jdbc.PhoneDAO;
import ru.javavision.jdbc.PhoneDAOImpl;

import java.sql.SQLException;

/**
 * Author : Pavel Ravvich.
 * Created : 02/11/2017.
 */
public class Application {
    public static void main(String[] args) {

        PhoneDAO phoneDAO = null;

        try {
            phoneDAO = new PhoneDAOImpl("postgres", "1", "jdbc:postgresql://localhost:5432/phones_magazine");




        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert phoneDAO != null;
            phoneDAO.closeConnection();
        }
    }
}

package ru.javavision;

import ru.javavision.jdbc.PhoneDAO;
import ru.javavision.jdbc.PhoneDAOImpl;
import ru.javavision.service.PhoneService;
import ru.javavision.service.PhoneServiceImpl;

import java.sql.SQLException;
import java.util.SortedSet;

/**
 * Author : Pavel Ravvich.
 * Created : 02/11/2017.
 */
public class Application {
    public static void main(String[] args) {

        PhoneDAO phoneDAO = null;

        try {
            phoneDAO = new PhoneDAOImpl("postgres", "1", "jdbc:postgresql://localhost:5432/phones_magazine");
            final PhoneServiceImpl phoneService = new PhoneServiceImpl(phoneDAO);
            final SortedSet<PhoneService.Statistic> wholeStat = phoneService.getWholeStat();
            wholeStat.forEach(System.out::println);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert phoneDAO != null;
            phoneDAO.closeConnection();
        }
    }
}

package ru.javavision;

import ru.javavision.jdbc.PhoneDAO;
import ru.javavision.jdbc.PhoneDAOImpl;
import ru.javavision.jdbc.StatisticDAO;
import ru.javavision.jdbc.StatisticDAOImpl;
import ru.javavision.model.PhoneModel;
import ru.javavision.model.Statistic;
import ru.javavision.service.PhoneService;
import ru.javavision.service.PhoneServiceImpl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Comparator;
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
//            final PhoneServiceImpl phoneService = new PhoneServiceImpl(phoneDAO);
//            final SortedSet<Statistic> wholeStat = phoneService.getStatLastYear();
//            wholeStat.forEach(System.out::println);

            StatisticDAO dao =  new StatisticDAOImpl("postgres", "1", "jdbc:postgresql://localhost:5432/phones_magazine");

            final PhoneModel model = new PhoneModel();
            model.setName("samsung");

            dao.getStat(
                    Comparator.comparing(Statistic::getRevenue),
                    new Timestamp(System.currentTimeMillis() - 31536000000L),
                    new Timestamp(System.currentTimeMillis()),
                    model
                    ).forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert phoneDAO != null;
            phoneDAO.closeConnection();
        }
    }

}

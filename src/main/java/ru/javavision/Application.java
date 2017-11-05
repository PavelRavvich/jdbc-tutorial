package ru.javavision;

import ru.javavision.jdbc.PhoneDAO;
import ru.javavision.jdbc.PhoneDAOImpl;
import ru.javavision.jdbc.StatisticRepository;
import ru.javavision.jdbc.StatisticRepositoryImpl;
import ru.javavision.model.PhoneModel;
import ru.javavision.model.Statistic;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Author : Pavel Ravvich.
 * Created : 02/11/2017.
 */
public class Application {
//    public static void main(String[] args) {
//
//        PhoneDAO phoneDAO = null;
//
//        try {
////            phoneDAO = new PhoneDAOImpl("postgres", "1", "jdbc:postgresql://localhost:5432/phones_magazine");
////
////            StatisticRepository dao =  new StatisticRepositoryImpl("postgres", "1", "jdbc:postgresql://localhost:5432/phones_magazine");
////
////            final PhoneModel model = new PhoneModel();
////            model.setName("samsung");
////            List<PhoneModel> models = new ArrayList<>();
////            models.add(model);
////            dao.getStat(
////                    models,
////                    new Statistic.TimeRange(
////                            new Timestamp(System.currentTimeMillis() - 31536000000L),
////                            new Timestamp(System.currentTimeMillis())),
////                    Comparator.comparing(Statistic::getRevenue)
////                    ).forEach(System.out::println);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            assert phoneDAO != null;
//            phoneDAO.closeConnection();
//        }
//    }

}

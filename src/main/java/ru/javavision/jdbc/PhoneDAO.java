package ru.javavision.jdbc;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

/**
 * Author : Pavel Ravvich.
 * Created : 03/11/2017.
 */
public interface PhoneDAO {

    Set<String> getAllModels();

    void closeConnection();

    int addModel(String mark);

    int getModelIdByName(String model);

    void addSale(Phone phone);

    BigInteger getRevenue(Timestamp from, Timestamp to);

    BigInteger getRevenue(String model, Timestamp from, Timestamp to);

    Map<String, BigDecimal> getMarkSumLess(BigDecimal sum, Timestamp from, Timestamp to);

    /**
     * Default method Java 8 only.
     */
    default void starter() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Анотация @Data из библиотеки lombok добавляет геттеры и сеттеры ко всем полям класса.
     * Не забудте устоновить плагин lombok для IDE.
     * IntelliJ IDEA -> Preferences -> Plugins ->  в поиске : lombok -> Install.
     */
    @Data
    class Phone {

        private int id;

        private BigDecimal prise;

        private Timestamp saleDate;

        private PhoneModel phoneModel;

        private int ownerId;
    }

    @Data
    class PhoneModel {

        private int id;

        private String name;
    }
}

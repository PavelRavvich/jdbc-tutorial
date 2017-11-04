package ru.javavision.jdbc;

import com.sun.istack.internal.NotNull;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author : Pavel Ravvich.
 * Created : 03/11/2017.
 */
public interface PhoneDAO {

    int addModel(Phone phone);

    void addSale(Phone phone);

    BigInteger getSaleSum(Timestamp from, Timestamp to);

    List<String> geSaleSum(Set<String> models, Timestamp from, Timestamp to);

    BigInteger geSaleSum(String model, Timestamp from, Timestamp to);

    List<String> getMarkSumMore(BigInteger sum, Timestamp from, Timestamp to);

    /**
     * Java 8 only.
     */
    default void starter() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    class Phone {

        private int id;

        private BigInteger prise;

        private Timestamp saleDate;

        public Phone(int id, BigInteger prise, Timestamp saleDate) {
            this.id = id;
            this.prise = prise;
            this.saleDate = saleDate;
        }

        public Phone() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public BigInteger getPrise() {
            return prise;
        }

        public void setPrise(BigInteger prise) {
            this.prise = prise;
        }

        public Timestamp getSaleDate() {
            return saleDate;
        }

        public void setSaleDate(Timestamp saleDate) {
            this.saleDate = saleDate;
        }
    }
}

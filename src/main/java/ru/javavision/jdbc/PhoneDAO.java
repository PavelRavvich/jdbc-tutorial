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

    int addModel(String mark);

    int getModelIdByName(String model);

    void addSale(Phone phone);

    BigInteger getRevenue(Timestamp from, Timestamp to);

    BigInteger getRevenue(String model, Timestamp from, Timestamp to);

    Map<String, BigInteger> getMarkSumMore(BigInteger sum, Timestamp from, Timestamp to);

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

        private PhoneModel phoneModel;

        private int ownerId;

        public int getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(int ownerId) {
            this.ownerId = ownerId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public PhoneModel getPhoneModel() {
            return phoneModel;
        }

        public void setPhoneModel(PhoneModel name) {
            this.phoneModel = name;
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

    class PhoneModel {

        @NotNull
        private int id;

        @NotNull
        private String name;

        public PhoneModel(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public PhoneModel() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

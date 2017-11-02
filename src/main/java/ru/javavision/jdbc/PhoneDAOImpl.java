package ru.javavision.jdbc;

import com.sun.istack.internal.NotNull;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

/**
 * Author : Pavel Ravvich.
 * Created : 03/11/2017.
 */
public class PhoneDAOImpl implements PhoneDAO {

    private @NotNull final Connection connection;

    public PhoneDAOImpl(@NotNull final Connection connection) {
        starter();
        this.connection = connection;
    }

    @Override
    public BigInteger getSaleSum(@NotNull final Timestamp from, @NotNull final Timestamp to) {
        return null;
    }

    @Override
    public List<String> getAllMarks() {
        return null;
    }

    @Override
    public List<String> getMarkSumLess(@NotNull final BigInteger sum) {
        return null;
    }

    @Override
    public List<String> getMarkSumMore(@NotNull final BigInteger sum) {
        return null;
    }
}

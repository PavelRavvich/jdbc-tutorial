package ru.javavision.jdbc;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

/**
 * Author : Pavel Ravvich.
 * Created : 03/11/2017.
 */
public class PhoneDAOImpl implements PhoneDAO {


    @Override
    public BigInteger getSaleSum(Timestamp from, Timestamp to) {
        return null;
    }

    @Override
    public List<String> getAllMarks() {
        return null;
    }

    @Override
    public List<String> getMarkSumLess(BigInteger sum) {
        return null;
    }

    @Override
    public List<String> getMarkSumMore(BigInteger sum) {
        return null;
    }
}

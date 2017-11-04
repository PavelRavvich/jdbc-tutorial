package ru.javavision.service;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import ru.javavision.jdbc.PhoneDAO;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

/**
 * Author : Pavel Ravvich.
 * Created : 04/11/2017.
 * <p>
 * PhoneServiceImpl
 */
public class PhoneServiceImpl implements PhoneService {

    @NotNull
    private final PhoneDAO phoneDAO;

    public PhoneServiceImpl(PhoneDAO phoneDAO) {
        this.phoneDAO = phoneDAO;
    }

    /**
     * Get set revenues for all model by all time. Sorted in descending order of revenues.
     */
    @Override
    public SortedSet<Statistic> getWholeStat() {
        final SortedSet<Statistic> result = new TreeSet<>((o1, o2) -> -(o1.getRevenue().compareTo(o2.getRevenue())));

        final Timestamp from = new Timestamp(System.currentTimeMillis() - 10000000);
        final Timestamp to = new Timestamp(System.currentTimeMillis());
        final BigInteger revenue = new BigInteger(String.valueOf(Integer.MAX_VALUE));

        final Set<String> allModels = phoneDAO.getAllModels();
        final Map<String, BigInteger> statistics = phoneDAO.getMarkSumLess(revenue, from, to);
        allModels.forEach(model -> {
            final BigInteger saleSum = statistics.get(model);
            if (saleSum != null) {
                result.add(new Statistic(model, saleSum));
            } else {
                result.add(new Statistic(model, new BigInteger("0")));
            }
        });
        return result;
    }
}

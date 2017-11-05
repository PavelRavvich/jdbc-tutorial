package ru.javavision.service;

import com.sun.istack.internal.NotNull;
import ru.javavision.jdbc.PhoneDAO;
import ru.javavision.model.Statistic;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
     * 31536000000L - Year in milliseconds.
     */
    @Override
    public SortedSet<Statistic> getStatLastYear() {
        final SortedSet<Statistic> result = new TreeSet<>((o1, o2) -> -(o1.getRevenue().compareTo(o2.getRevenue())));

        final Statistic.TimeRange range = new Statistic.TimeRange(
                new Timestamp(System.currentTimeMillis() - 31536000000L), new Timestamp(System.currentTimeMillis()));
        final BigDecimal revenue = BigDecimal.valueOf(Long.MAX_VALUE);

        final Set<String> allModels = phoneDAO.getAllModels();
        final Map<String, BigDecimal> statistics = phoneDAO.getMarkSumLess(revenue, range.getFrom(), range.getTo());
        allModels.forEach(model -> {
            final BigDecimal saleSum = statistics.get(model);
            if (saleSum != null) {
                result.add(new Statistic(range, model, saleSum));
            } else {
                result.add(new Statistic(range, model, new BigDecimal("0")));
            }
        });
        return result;
    }
}

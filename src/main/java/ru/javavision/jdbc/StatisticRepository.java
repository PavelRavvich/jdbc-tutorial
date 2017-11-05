package ru.javavision.jdbc;

import com.sun.istack.internal.NotNull;
import ru.javavision.model.PhoneModel;
import ru.javavision.model.Statistic;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public interface StatisticRepository {

    List<Statistic> getStat(List<PhoneModel> models, Statistic.TimeRange range, Comparator<Statistic> comp);

    List<Statistic> getStatRevenueLess(BigDecimal threshold, Statistic.TimeRange range, Comparator<Statistic> comp);

    List<Statistic> getStatRevenueMore(BigDecimal threshold, Statistic.TimeRange range, Comparator<Statistic> comp);

    default String modelWildcards(final @NotNull int models) {
        if (models == 0) {
            return "";
        } else if (models == 1) {
            return "AND m.name = (?) ";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(" AND (m.name = (?) ");
            for (int i = 0; i < models - 1; i++) {
                sb.append(" OR m.name = (?) ");
            }
            sb.append(") ");
            return sb.toString();
        }
    }
}

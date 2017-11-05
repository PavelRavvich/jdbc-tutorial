package ru.javavision.jdbc;

import ru.javavision.model.PhoneModel;
import ru.javavision.model.Statistic;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

public interface StatisticDAO {

    SortedSet<Statistic> getStat(Comparator<Statistic> comp, Timestamp from, Timestamp to, PhoneModel... models);

    default String modelWildcards(int models) {
        return IntStream.range(0, models).mapToObj(i -> " AND m.name = (?) ").collect(Collectors.joining());
    }
}

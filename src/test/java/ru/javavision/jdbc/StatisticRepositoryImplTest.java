package ru.javavision.jdbc;

import com.sun.istack.internal.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.javavision.model.PhoneModel;
import ru.javavision.model.Statistic;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StatisticRepositoryImplTest {

    @NotNull
    private StatisticRepository<Statistic, Statistic.TimeRange> repository;

    @NotNull
    private Connection connection;

    @NotNull
    private final Statistic.TimeRange range = new Statistic.TimeRange(
            new Timestamp(System.currentTimeMillis() - 31536000000L),
            new Timestamp(System.currentTimeMillis()));

    private final Comparator<Statistic> comparator = Comparator.comparing(Statistic::getRevenue);

    @NotNull
    private PhoneModel samsung;

    @NotNull
    private PhoneModel iphone;

    @Before
    public void before() {
        initPhoneModels();
        try {
            String user = "postgres";
            String password = "1";
            String url = "jdbc:postgresql://localhost:5432/phones_magazine";
            connection = DriverManager.getConnection(url, user, password);
            repository = new StatisticRepositoryImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initPhoneModels() {
        samsung = new PhoneModel();
        samsung.setName("samsung");
        iphone = new PhoneModel();
        iphone.setName("iphone");
    }

    @After
    public void after() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see ru.javavision.jdbc.StatisticRepository#getStat(List, Statistic.TimeRange, Comparator).
     */
    @Test
    public void whenModelIsNotSelectedThenReturnAllActiveModels() {
        final List<Statistic> result = repository.getStat(emptyList(), range, comparator);
        assertThat(result.size(), is(3));
    }

    /**
     * @see ru.javavision.jdbc.StatisticRepository#getStat(List, Statistic.TimeRange, Comparator).
     */
    @Test
    public void whenSelectStatisticBySinglePhoneModelFor1YearThenReturnStatistic() {
        final List<Statistic> result = repository.getStat(Collections.singletonList(samsung), range, comparator);
        assertThat(result.get(0).getRevenue(), is(new BigDecimal("122000")));
    }

    /**
     * @see ru.javavision.jdbc.StatisticRepository#getStat(List, Statistic.TimeRange, Comparator).
     */
    @Test
    public void whenSelectStatisticBy2PhoneModelFor1YearThenReturnStatistic() {
        final List<Statistic> result = repository.getStat(newArrayList(iphone, samsung), range, comparator);
        assertThat(result.get(0).getRevenue(), is(new BigDecimal("90000")));
        assertThat(result.get(1).getRevenue(), is(new BigDecimal("122000")));
    }

    /**
     * @see ru.javavision.jdbc.StatisticRepository#getStat(List, Statistic.TimeRange, Comparator).
     */
    @Test
    public void whenSelectStatisticBy2PhoneModelFor1YearWithInvertComparatorThenReturnStatisticDescendingRevenue() {
        final Comparator<Statistic> descendingComparator = (o1, o2) -> o2.getRevenue().compareTo(o1.getRevenue());
        final List<Statistic> result = repository.getStat(newArrayList(iphone, samsung), range, descendingComparator);
        assertThat(result.get(0).getRevenue(), is(new BigDecimal("122000")));
        assertThat(result.get(1).getRevenue(), is(new BigDecimal("90000")));
    }

    /**
     * @see ru.javavision.jdbc.StatisticRepository#getStatRevenueLess(BigDecimal, Statistic.TimeRange, Comparator) .
     */
    @Test
    public void whenSendRevenuesThresholdThenReturnStatisticForPhoneModelsWhichHaveRevenuesLessThreshold() {
        final BigDecimal threshold = new BigDecimal("100000");
        final List<Statistic> statistics = repository.getStatRevenueLess(threshold, range, comparator);
        assertThat(statistics.size(), is(2));
        assertThat(statistics.get(0).getRevenue(), is(new BigDecimal("65000")));
        assertThat(statistics.get(1).getRevenue(), is(new BigDecimal("90000")));
    }

    /**
     * @see ru.javavision.jdbc.StatisticRepository#getStatRevenueMore(BigDecimal, Statistic.TimeRange, Comparator).
     */
    @Test
    public void whenSendRevenuesThresholdThenReturnStatisticForPhoneModelsWhichHaveRevenuesMoreThreshold() {
        final BigDecimal threshold = new BigDecimal("90000");
        final List<Statistic> statistics = repository.getStatRevenueMore(threshold, range, comparator);
        assertThat(statistics.size(), is(2));
    }
}
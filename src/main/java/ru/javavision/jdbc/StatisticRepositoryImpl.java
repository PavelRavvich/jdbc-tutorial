package ru.javavision.jdbc;

import com.sun.istack.internal.NotNull;
import ru.javavision.model.PhoneModel;
import ru.javavision.model.Statistic;


import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

/**
 * Author : Pavel Ravvich.
 * Created : 05/11/2017.
 */
public class StatisticRepositoryImpl implements StatisticRepository<Statistic, Statistic.TimeRange> {

    /**
     * Connection to database.
     */
    @NotNull
    private final Connection connection;

    public StatisticRepositoryImpl(@NotNull final Connection connection) throws SQLException {
        this.connection = connection;
    }

    /**
     * Get statistic by sales.
     *
     * @param models for select.
     * @param range  time for select.
     * @param comp   for result sorting
     * @return statistic for decremented models.
     */
    @Override
    public List<Statistic> getStat(@NotNull final List<PhoneModel> models,
                                   @NotNull final Statistic.TimeRange range,
                                   @NotNull final Comparator<Statistic> comp) {

        final SortedSet<Statistic> result = new TreeSet<>(comp);

        final String sql = StatSQL.GET_STAT.QUERY.replace("%models%", modelWildcards(models.size()));

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setTimestamp(1, range.getFrom());
            statement.setTimestamp(2, range.getTo());
            int parameterIndex = 3;
            for (PhoneModel model : models) {
                statement.setString(parameterIndex++, model.getName());
            }

            final ResultSet set = statement.executeQuery();

            while (set.next()) {
                final String modelName = set.getString(1);
                final BigDecimal revenue = set.getBigDecimal(2);
                result.add(new Statistic(range, modelName, revenue));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(result);
    }

    /**
     * Get statistic for models which revenues less threshold.
     *
     * @param threshold the lower sum sale threshold for select.
     * @param range     of time for select.
     * @param comp      for result sorting
     * @return statistics of with revenues less threshold.
     */
    @Override
    public List<Statistic> getStatRevenueLess(@NotNull final BigDecimal threshold,
                                              @NotNull final Statistic.TimeRange range,
                                              @NotNull final Comparator<Statistic> comp) {

        final SortedSet<Statistic> statistics = new TreeSet<>(comp);

        try (PreparedStatement statement = connection.prepareStatement(StatSQL.GET_STAT_REVENUE_LESS.QUERY)) {

            execute(threshold, range, statement, statistics);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(statistics);
    }

    /**
     * Get statistic for models which revenues more threshold.
     *
     * @param threshold the more sum sale threshold for select.
     * @param range     of time for select.
     * @param comp      for result sorting
     * @return statistics of with revenues more threshold.
     */
    @Override
    public List<Statistic> getStatRevenueMore(@NotNull final BigDecimal threshold,
                                              @NotNull final Statistic.TimeRange range,
                                              @NotNull final Comparator<Statistic> comp) {

        final SortedSet<Statistic> statistics = new TreeSet<>(comp);

        try (PreparedStatement statement = connection.prepareStatement(StatSQL.GET_STAT_REVENUE_MORE.QUERY)) {

            execute(threshold, range, statement, statistics);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(statistics);
    }

    /**
     * Set wildcards and fill result obj.
     *
     * @param threshold for select.
     * @param range     for select.
     * @param statement for query.
     * @param result    for aggregation result of query.
     */
    private void execute(@NotNull final BigDecimal threshold,
                         @NotNull final Statistic.TimeRange range,
                         @NotNull final PreparedStatement statement,
                         @NotNull final SortedSet<Statistic> result) throws SQLException {

        statement.setTimestamp(1, range.getFrom());
        statement.setTimestamp(2, range.getTo());
        statement.setBigDecimal(3, threshold);

        final ResultSet set = statement.executeQuery();

        while (set.next()) {
            final String modelName = set.getString(1);
            final BigDecimal revenue = set.getBigDecimal(2);
            result.add(new Statistic(range, modelName, revenue));
        }
    }

    /**
     * SQL queries.
     */
    enum StatSQL {
        /**
         * Get statistic by sales with var args of models.
         * For fill %models%:
         *
         * @see ru.javavision.jdbc.StatisticRepository#modelWildcards(int).
         */
        GET_STAT("SELECT " +
                "  m.name, " +
                "  sum(p.price) AS cost " +
                "FROM (" +
                "       SELECT * " +
                "       FROM phones_sale" +
                "       WHERE date BETWEEN (?) AND (?) " +
                "     ) AS p " +
                "  INNER JOIN phone_models AS m " +
                "    ON p.model_id = m.id %models% " +
                "GROUP BY m.name " +
                "ORDER BY cost DESC;"),

        /**
         * Get statistic for models which revenues less threshold.
         */
        GET_STAT_REVENUE_LESS("SELECT " +
                "  m.name, " +
                "  sum(p.price) AS cost " +
                "FROM ( " +
                "       SELECT * " +
                "       FROM phones_sale " +
                "       WHERE date BETWEEN (?) AND (?) " +
                "     ) AS p " +
                "  INNER JOIN phone_models AS m " +
                "    ON p.model_id = m.id " +
                "GROUP BY m.name " +
                "HAVING sum(p.price) <= (?) " +
                "ORDER BY cost DESC;"),
        /**
         * Get statistic for models which revenues more threshold.
         */
        GET_STAT_REVENUE_MORE("SELECT " +
                "  m.name, " +
                "  sum(p.price) AS cost " +
                "FROM ( " +
                "       SELECT * " +
                "       FROM phones_sale " +
                "       WHERE date BETWEEN (?) AND (?) " +
                "     ) AS p " +
                "  INNER JOIN phone_models AS m " +
                "    ON p.model_id = m.id " +
                "GROUP BY m.name " +
                "HAVING sum(p.price) >= (?) " +
                "ORDER BY cost DESC;");

        String QUERY;

        StatSQL(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

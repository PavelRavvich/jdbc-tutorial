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
 * <p>
 * StatisticDAOImpl
 */
public class StatisticDAOImpl implements StatisticDAO {

    @NotNull
    private final Connection connection;

    public StatisticDAOImpl(@NotNull final String user, @NotNull final String password, @NotNull final String url) throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
    }

    @Override
    public SortedSet<Statistic> getStat(Comparator<Statistic> comp, Timestamp from, Timestamp to, PhoneModel... models) {
        final SortedSet<Statistic> result = new TreeSet<>(comp);

        String sql = StatSQL.GET_STAT.v.replace("%models%", modelWildcards(models.length));
        if (models.length == 0) {
            sql = StatSQL.GET_STAT.v.replace("%models%", "");
        }

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setTimestamp(1, from);
            statement.setTimestamp(2, to);
            int pos = 3;
            for (PhoneModel model : models) {
                statement.setString(pos++, model.getName());
            }

            final ResultSet set = statement.executeQuery();

            while (set.next()) {
                final String modelName = set.getString(1);
                final BigDecimal revenue = set.getBigDecimal(2);
                result.add(new Statistic(from, to, modelName, revenue));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    enum StatSQL {
        GET_STAT("SELECT\n" +
                "  m.name,\n" +
                "  sum(p.price) AS cost\n" +
                "FROM (\n" +
                "       SELECT *\n" +
                "       FROM phones_sale\n" +
                "       WHERE date BETWEEN (?) AND (?)\n" +
                "     ) AS p\n" +
                "  INNER JOIN phone_models AS m\n" +
                "    ON p.model_id = m.id %models%\n" +
                "GROUP BY m.name\n" +
                "ORDER BY cost DESC;");

        String v;

        StatSQL(String v) {
            this.v = v;
        }
    }
}

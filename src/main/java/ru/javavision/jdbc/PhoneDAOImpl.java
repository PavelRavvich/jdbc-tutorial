package ru.javavision.jdbc;

import com.sun.istack.internal.NotNull;
import lombok.Getter;

import java.math.BigInteger;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Author : Pavel Ravvich.
 * Created : 03/11/2017.
 */
public class PhoneDAOImpl implements PhoneDAO {

    @Getter
    @NotNull
    private final Connection connection;

    public PhoneDAOImpl(@NotNull final String user,
                        @NotNull final String password,
                        @NotNull final String url) throws SQLException {
        starter();
        this.connection = DriverManager.getConnection(url, user, password);
    }

    /**
     * Add phone model into table 'models'.
     *
     * @param model for addition.
     * @return generated id of new model or if model already exist return -1.
     */
    @Override
    public int addModel(@NotNull final String model) {

        int result = -1;

        try (PreparedStatement statement = connection.prepareStatement(SQL.ADD_MODEL.v)) {

            statement.setString(1, model);

            final ResultSet set = statement.executeQuery();
            if (set.next()) {
                result = set.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Get model id by name of model.
     *
     * @param name for select.
     * @return id which corresponding to model name, or if model name is not exist return -1.
     */
    public int getModelIdByName(@NotNull final String name) {

        int result = -1;

        try (PreparedStatement statement = connection.prepareStatement(SQL.GET_MODEL_ID.v)) {

            statement.setString(1, name);

            final ResultSet set = statement.executeQuery();
            if (set.next()) {
                result = set.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Add sale event to phones table.
     *
     * @param phone which will be sale.
     */
    @Override
    public void addSale(@NotNull final Phone phone) {

        if (getModelIdByName(phone.getPhoneModel().getName()) == -1) {
            throw new IllegalStateException("Invalid phone model.");
        }

        try (PreparedStatement statement = connection.prepareStatement(SQL.ADD_SALE.v)) {

            statement.setInt(1, phone.getPhoneModel().getId());
            statement.setObject(2, phone.getPrise());
            statement.setInt(3, phone.getOwnerId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get revenue in period of time.
     *
     * @param from start period.
     * @param to   end period.
     * @return whole revenue by condition.
     */
    @Override
    public BigInteger getRevenue(@NotNull final Timestamp from, @NotNull final Timestamp to) {

        BigInteger result = new BigInteger("0");

        try (PreparedStatement statement = connection.prepareStatement(SQL.GET_REVENUE_PERIOD.v)) {

            statement.setTimestamp(1, from);
            statement.setTimestamp(2, to);

            final ResultSet set = statement.executeQuery();

            if (set.next()) {
                result = new BigInteger(String.valueOf(set.getLong(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Get revenue for phone model in period of time.
     *
     * @param model for select.
     * @param from start period.
     * @param to end period.
     * @return whole revenue by condition.
     */
    @Override
    public BigInteger getRevenue(@NotNull final String model, @NotNull final Timestamp from, @NotNull final Timestamp to) {

        BigInteger result = new BigInteger("0");

        try (PreparedStatement statement = connection.prepareStatement(SQL.GET_REVENUE_PERIOD_MODEL.v)) {

            statement.setString(1, model);
            statement.setTimestamp(2, from);
            statement.setTimestamp(3, to);

            final ResultSet set = statement.executeQuery();

            if (set.next()) {
                result = new BigInteger(String.valueOf(set.getLong(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * Get map of models to revenues for time period and which have revenues less sum param.
     *
     * @param sum lower threshold of entry by revenues.
     * @param from lower threshold of entry by time.
     * @param to upper threshold of entry by time.
     * @return map: < name_model (String) , revenue_by_time_period (BigInteger) >.
     */
    @Override
    public Map<String, BigInteger> getMarkSumLess(@NotNull final BigInteger sum, @NotNull final Timestamp from, @NotNull final Timestamp to) {

        final Map<String, BigInteger> result = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL.GET_MODEL_REVENUE_BY_PERIOD_MIN_REVENUE.v)) {

            statement.setTimestamp(1, from);
            statement.setTimestamp(2, to);
            statement.setLong(3, new Long(String.valueOf(sum)));

            final ResultSet set = statement.executeQuery();

            while (set.next()) {
                final String modelName = set.getString(1);
                final BigInteger revenue = new BigInteger(String.valueOf(set.getInt(2)));
                result.put(modelName, revenue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * SQL queries.
     */
    private enum SQL {
        ADD_MODEL("INSERT INTO models (id, name) VALUES (DEFAULT, (?)) RETURNING id"),
        GET_MODEL_ID("SELECT id FROM models WHERE name = (?)"),
        ADD_SALE("INSERT INTO phones (id, model_id, price, date, user_id) VALUES (DEFAULT, (?), (?), now(), (?)) RETURNING id"),
        GET_REVENUE_PERIOD("SELECT sum(p.price) FROM phones AS p WHERE p.date >= (?) AND p.date <= (?)"),
        GET_REVENUE_PERIOD_MODEL("SELECT sum(p.price) FROM phones AS p WHERE m.name = (?) AND p.date >= (?) AND p.date <= (?)"),
        GET_MODEL_REVENUE_BY_PERIOD_MIN_REVENUE("select m.name, p.price from phones p inner join models m on p.model_id = m.id " +
                "where p.date between (?) and (?) group by m.id, m.name having sum(p.price) < (?) order by sum(p.price) desc;");

        String v;

        SQL(String v) {
            this.v = v;
        }
    }
}

package ru.javavision.jdbc;

import com.sun.istack.internal.NotNull;
import ru.javavision.model.PhoneModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author : Pavel Ravvich.
 * Created : 06/11/2017.
 */
public class PhoneModelDAO implements DAO<PhoneModel, String> {

    /**
     * Connection of database.
     */
    @NotNull
    private final Connection connection;

    /**
     * Init database connection.
     *
     * @param connection of database.
     */
    public PhoneModelDAO(@NotNull final Connection connection) {
        this.connection = connection;
    }

    /**
     * Check exist PhoneModel by name.
     *
     * @param name for select.
     * @return true if exist. False if does not exist.
     */
    private boolean isExist(@NotNull final String name) {
        return read(name).getId() != -1;
    }

    /**
     * Select PhoneModel by name.
     *
     * @param name for select.
     * @return return valid entity if she exist. If entity does not exist return empty PhoneModel with id = -1.
     */
    @Override
    public PhoneModel read(@NotNull final String name) {

        final PhoneModel result = new PhoneModel();
        result.setName(name);

        try (PreparedStatement statement = connection.prepareStatement(ModelPhoneSQL.GET.QUERY)) {

            statement.setString(1, name);

            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result.setId(Integer.parseInt(rs.getString("id")));
            } else {
                result.setName("entity not exist in phone_models");
                result.setId(-1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Update PhoneModel' name by id.
     *
     * @param model with selected id.
     * @return updated entity.
     */
    @Override
    public boolean update(@NotNull final PhoneModel model) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(ModelPhoneSQL.UPDATE.QUERY)) {
            statement.setString(1, model.getName());
            statement.setInt(2, model.getId());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Delete PhoneModel by name.
     *
     * @param model for delete.
     * @return true if PhoneModel was deleted. False if PhoneModel not exist.
     */
    @Override
    public boolean delete(@NotNull final PhoneModel model) {
        if (!isExist(model.getName())) return false;

        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(ModelPhoneSQL.DELETE.QUERY)) {
            statement.setString(1, model.getName());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Create PhoneModel in database.
     *
     * @param model for create.
     * @return false if PhoneModel already exist. If creating success true.
     */
    @Override
    public boolean create(@NotNull final PhoneModel model) {
        if (isExist(model.getName())) return false;

        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(ModelPhoneSQL.ADD.QUERY)) {
            statement.setString(1, model.getName());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * SQL queries for phone_models table.
     */
    enum ModelPhoneSQL {
        GET("SELECT id FROM phone_models WHERE name = (?)"),
        DELETE("DELETE FROM phone_models WHERE name = (?) RETURNING id"),
        ADD("INSERT INTO phone_models (id, name) VALUES (DEFAULT, (?)) RETURNING id;"),
        UPDATE("UPDATE phone_models SET name = (?) WHERE id = (?) RETURNING id");

        String QUERY;

        ModelPhoneSQL(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

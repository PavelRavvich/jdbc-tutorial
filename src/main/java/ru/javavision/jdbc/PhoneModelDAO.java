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
 * <p>
 * PhoneModelDAO
 */
public class PhoneModelDAO implements DAO<PhoneModel, String> {

    @NotNull
    private final Connection connection;

    public PhoneModelDAO(@NotNull final Connection connection) {
        this.connection = connection;
    }

    private boolean isExist(@NotNull final String name) {
        return get(name).getId() != -1;
    }

    @Override
    public PhoneModel get(@NotNull final String name) {

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



    @Override
    public boolean add(@NotNull final PhoneModel model) {
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

package ru.javavision;

import java.sql.*;

/**
 * Author : Pavel Ravvich.
 * Created : 02/11/2017.
 */
public class Application {
    public static void main(String[] args) throws SQLException {
        final String user = "postgres";
        final String password = "1";
        final String url = "jdbc:postgresql://localhost:5432/phones_magazine";

        final Connection connection = DriverManager.getConnection(url, user, password);


        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = (?)")) {
            statement.setInt(1, 1);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String byName = "login: " + resultSet.getString("login");
                String byIndex = "password" + resultSet.getString(3);
                System.out.println(byName);
                System.out.println(byIndex);
            }
        } finally {
            connection.close();
        }
    }
}

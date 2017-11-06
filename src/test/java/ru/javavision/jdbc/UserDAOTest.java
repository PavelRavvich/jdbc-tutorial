package ru.javavision.jdbc;

import com.sun.istack.internal.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.javavision.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserDAOTest {

    @NotNull
    private DAO<User, String> dao;

    @NotNull
    private Connection connection;

    @Before
    public void before() {
        try {
            String user = "postgres";
            String password = "1";
            String url = "jdbc:postgresql://localhost:5432/phones_magazine";
            connection = DriverManager.getConnection(url, user, password);
            dao = new UserDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
     * @see ru.javavision.jdbc.UserDAO#get(Object).
     */
    @Test
    public void whenGetUserWhichExistThenReturnUser() {
        final User user = dao.get("admin");
        final User expected = new User();
        expected.setLogin("admin");
        expected.setPassword("123");
        expected.setRole(new User.Role(1, "admin"));
        assertThat(user, is(expected));
    }

    /**
     * @see ru.javavision.jdbc.UserDAO#get(Object).
     */
    @Test
    public void whenUserIsNotExistThenReturnEmptyUserObj() {
        final User user = dao.get("xxx");
        assertThat(user.getId(), is(-1));
    }

    /**
     * @see ru.javavision.jdbc.UserDAO#add(Object).
     */
    @Test
    public void whenAddUserWhichNotExistThenReturnUser() {
        final User user = new User(0, "test", "test", new User.Role(1, "admin"));
        final boolean result = dao.add(user);
        assertThat(result, is(true));
        //Clear test data.
        dao.delete(user);
    }

    @Test
    public void whenUserWhichExistDeletedThenReturnTrue() {
        final User user = new User(0, "test", "test", new User.Role(1, "admin"));
        dao.add(user);
        final User state = dao.get("test");
        boolean before = state.getId() != -1;
        user.setId(state.getId());

        final boolean after = dao.delete(user);
        assertTrue(before);
        assertTrue(after);
    }

    @Test
    public void whenUserNotExistThenReturnFalse() {
        assertFalse(dao.delete(new User(0, "test", "test", new User.Role(1, "admin"))));
    }

    //tensorflow
}
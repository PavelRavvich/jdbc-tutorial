package ru.javavision.jdbc;

import com.sun.istack.internal.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.javavision.model.PhoneModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PhoneModelDAOTest {

    @NotNull
    private DAO<PhoneModel, String> dao;

    @NotNull
    private Connection connection;

    @NotNull
    private final PhoneModel model = new PhoneModel();

    @Before
    public void before() {
        model.setName("test");
        try {
            String user = "postgres";
            String password = "1";
            String url = "jdbc:postgresql://localhost:5432/phones_magazine";
            connection = DriverManager.getConnection(url, user, password);
            dao = new PhoneModelDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void after() {
        //Clean test PhoneModel.
        dao.delete(model);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see ru.javavision.jdbc.PhoneModelDAO#get(Object).
     */
    @Test
    public void whenModelIsExistThenReturnPhoneModelWithId() {
        final PhoneModel result = dao.get("samsung");
        assertThat(result, is(new PhoneModel(1, "samsung")));
    }

    /**
     * @see ru.javavision.jdbc.PhoneModelDAO#add(Object).
     */
    @Test
    public void whenAddNewPhoneModelThenPhoneModelAddedAndReturnFalse() {
        final boolean result = dao.add(model);
        assertThat(result, is(true));
    }

    /**
     * @see ru.javavision.jdbc.PhoneModelDAO#add(Object).
     */
    @Test
    public void whenAddPhoneModelWhichAlreadyExistThenReturnFalse() {
        dao.add(model);
        final boolean result = dao.add(model);
        assertThat(result, is(false));
    }

    /**
     * @see ru.javavision.jdbc.PhoneModelDAO#delete(Object) .
     */
    @Test
    public void whenDeletePhoneModelWhichExistThenReturnTrue() {
        dao.add(model);
        final boolean result = dao.delete(model);
        assertThat(result, is(true));
    }

    /**
     * @see ru.javavision.jdbc.PhoneModelDAO#delete(Object) .
     */
    @Test
    public void whenDeletePhoneModelWhichNotExistThenReturnFalse() {
        final boolean result = dao.delete(model);
        assertThat(result, is(false));
    }

    @Test
    public void whenUpdateSuccessThenModelNameSetNewValue() {
        dao.add(model);
        model.setName("updated");
        model.setId(dao.get("test").getId());
        final boolean result = dao.update(model);
        assertThat(result, is(true));
        assertThat(dao.get("updated").getName(), is("updated"));
    }

    @Test
    public void whenTryUpdatePhoneModelWhichNotExistThenReturnFalse() {
        final boolean result = dao.update(new PhoneModel(Integer.MAX_VALUE, "xxx"));
        assertThat(result, is(false));
    }
}
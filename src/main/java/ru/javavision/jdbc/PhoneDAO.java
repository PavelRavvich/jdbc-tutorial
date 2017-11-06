package ru.javavision.jdbc;

import ru.javavision.model.Phone;

/**
 * Author : Pavel Ravvich.
 * Created : 03/11/2017.
 */
public interface PhoneDAO {

    Phone get(String modelName);

    boolean add(Phone phone);

    boolean update(Phone phone);

    boolean delete(Phone phone);

    /**
     * Default method Java 8 only.
     */
    default void starter() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

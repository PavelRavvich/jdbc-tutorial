package ru.javavision.jdbc;

import com.sun.istack.internal.NotNull;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author : Pavel Ravvich.
 * Created : 03/11/2017.
 */
public class PhoneDAOImpl implements PhoneDAO {

    @NotNull
    private final Connection connection;

    public PhoneDAOImpl(@NotNull final Connection connection) {
        starter();
        this.connection = connection;
    }

    /*
    INSERT INTO models (id, name) VALUES (DEFAULT, 'digma') RETURNING id;
     */
    @Override
    public int addModel(Phone phone) {
        return 0;
    }

    /*
    INSERT INTO phones (id, model_id, prise, date, user_id) VALUES (DEFAULT, 1, 58000, now(), 2);
     */
    @Override
    public void addSale(Phone phone) {

    }

    /*
    Получение суммы продаж всех моделей в определенный промежуток времени.
    SELECT sum(p.prise) FROM phones AS p
     LEFT JOIN models AS m ON m.id = p.model_id
    WHERE p.date >= '2017-11-02 23:21:31.59098' AND p.date <= '2017-12-05 23:21:31.59098';
     */
    @Override
    public BigInteger getSaleSum(@NotNull final Timestamp from, @NotNull final Timestamp to) {
        return null;
    }

    /*
    Получение суммы выручки за модель за промежуток времени

    SELECT * FROM models;

    SELECT sum(p.prise) FROM phones AS p LEFT JOIN models AS m ON p.model_id = m.id
    WHERE m.name = 'samsung'
          AND p.date >= '2017-11-02 23:21:31.59098'
          AND p.date <= '2017-12-05 23:21:31.59098';
     */
    @Override
    public BigInteger geSaleSum(@NotNull final String model,
                                @NotNull final Timestamp from,
                                @NotNull final Timestamp to) {
        return null;
    }


    /*
    Получение детальной статистике по ряду моделей за промежуток времени
     */
    @Override
    public List<String> geSaleSum(@NotNull final Set<String> models,
                                  @NotNull final Timestamp from,
                                  @NotNull final Timestamp to) {
        return null;
    }


    /*
    Получить модели продажи которых больше указанной суммы за промежуток времени.
     */
    @Override
    public List<String> getMarkSumMore(@NotNull final BigInteger sum,
                                       @NotNull final Timestamp from,
                                       @NotNull final Timestamp to) {
        return null;
    }
}

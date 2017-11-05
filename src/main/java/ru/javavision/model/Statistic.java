package ru.javavision.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Author : Pavel Ravvich.
 * Created : 05/11/2017.
 * <p>
 * Statistic
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Statistic {

    private TimeRange range;

    private String model;

    private BigDecimal revenue;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class TimeRange {

        private Timestamp from;

        private Timestamp to;
    }

}

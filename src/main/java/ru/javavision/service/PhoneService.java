package ru.javavision.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.math.BigInteger;
import java.util.List;
import java.util.SortedSet;

public interface PhoneService {
    SortedSet<Statistic> getWholeStat();

    @Data
    @AllArgsConstructor
    @ToString
    class Statistic {

        private String model;

        private BigInteger revenue;

    }
}

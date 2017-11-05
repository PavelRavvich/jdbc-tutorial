package ru.javavision.service;

import ru.javavision.model.Statistic;

import java.util.SortedSet;

public interface PhoneService {
    SortedSet<Statistic> getStatLastYear();
}

package ru.javavision.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Анотация @Data из библиотеки lombok добавляет геттеры и сеттеры ко всем полям класса.
 * Не забудте устоновить плагин lombok для IDE.
 * IntelliJ IDEA -> Preferences -> Plugins ->  в поиске : lombok -> Install.
 */
@Data
public class Phone {

    private int id;

    private BigDecimal prise;

    private Timestamp saleDate;

    private PhoneModel phoneModel;

    private int ownerId;
}

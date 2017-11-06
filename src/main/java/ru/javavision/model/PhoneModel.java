package ru.javavision.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Author : Pavel Ravvich.
 * Created : 05/11/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PhoneModel {

    private int id;

    private String name;
}

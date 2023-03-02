package ru.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    public Courier getDefault() {
        return new Courier("sashasasha", "sasha", "Sasha");
    }

    public Courier getCourierRandom() {
        return new Courier(RandomStringUtils.randomAlphanumeric(5), RandomStringUtils.randomAlphanumeric(4), RandomStringUtils.randomAlphanumeric(5));
    }
}

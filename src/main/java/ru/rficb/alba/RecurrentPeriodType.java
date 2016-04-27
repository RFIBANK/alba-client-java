package ru.rficb.alba;

/**
 * Период через который происходит очередное списание
 */
public enum RecurrentPeriodType {
    BY_REQUEST("byrequest")
    ;

    private final String text;


    RecurrentPeriodType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

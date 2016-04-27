package ru.rficb.alba;

/**
 * Тип рекуррента
 */
public enum RecurrentType {
    FIRST("first"),
    NEXT("next");

    private final String text;


    RecurrentType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

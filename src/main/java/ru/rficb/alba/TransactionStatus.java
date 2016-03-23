package ru.rficb.alba;

/**
 * Статус транзакции
 */
public enum TransactionStatus {
    OPEN("open"),
    ERROR("error"),
    PAYED("payed"),
    SUCCESS("success"),
    ;

    private final String text;

    TransactionStatus(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

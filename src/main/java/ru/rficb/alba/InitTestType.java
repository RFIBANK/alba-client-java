package ru.rficb.alba;

/**
 * Тип тестирования при нициации транзакции
 */
public enum InitTestType {
    NONE("0"),
    OK("ok"),
    OPERATOR_CANCEL("operator_cancel"),
    ;
    private final String text;


    InitTestType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

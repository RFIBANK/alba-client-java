package ru.rficb.alba;

/**
 * Коды ошибок
 */
public enum AlbaErrorCode {
    TYPE("type"),
    AUTH("auth"),
    DATA("data"),
    COMMON("common"),
    UNIQUE("unique");

    private final String text;

    AlbaErrorCode(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static AlbaErrorCode fromString(String text) {
        if (text != null) {
            for (AlbaErrorCode code : AlbaErrorCode.values()) {
                if (text.equalsIgnoreCase(code.text)) {
                    return code;
                }
            }
        }
        return COMMON;
    }
}

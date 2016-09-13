package ru.rficb.alba;

/**
 * Профиль подключения
 */
public class ConnectionProfile {
    protected String baseUrl;
    protected String cardTokenUrl;
    protected String cardTokenTestUrl;

    public ConnectionProfile(String baseUrl, String cardTokenUrl, String cardTokenTestUrl) {
        this.baseUrl = baseUrl;
        this.cardTokenUrl = cardTokenUrl;
        this.cardTokenTestUrl = cardTokenTestUrl;
    }

    static public ConnectionProfile first() {
        return new ConnectionProfile(
                "https://partner.rficb.ru/",
                "https://secure.rficb.ru/cardtoken/",
                "https://test.rficb.ru/cardtoken/"
        );
    }

    static public ConnectionProfile second() {
        return new ConnectionProfile(
                "https://partner.rficb.ru/",
                "https://secure.rfibank.ru/cardtoken/",
                "https://test.rficb.ru/cardtoken/"
        );
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getCardTokenUrl() {
        return cardTokenUrl;
    }

    public void setCardTokenUrl(String cardTokenUrl) {
        this.cardTokenUrl = cardTokenUrl;
    }

    public String getCardTokenTestUrl() {
        return cardTokenTestUrl;
    }

    public void setCardTokenTestUrl(String cardTokenTestUrl) {
        this.cardTokenTestUrl = cardTokenTestUrl;
    }
}

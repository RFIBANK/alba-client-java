package ru.rficb.alba;

/**
 * Параметры запроса на получение токена карточних данных
 */
public class CardTokenRequest {
    private int serviceId;
    private String card;
    private int expMonth;
    private int expYear;
    private String cvc;
    private String cardHolder;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int expMonth) {
        this.expMonth = expMonth;
    }

    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardTokenRequest(int serviceId, String card, int expMonth, int expYear, String cvc) {
        this.serviceId = serviceId;
        this.card = card;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.cvc = cvc;
    }

    public CardTokenRequest(int serviceId, String card, int expMonth, int expYear, String cvc, String cardHolder) {
        this.serviceId = serviceId;
        this.card = card;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.cvc = cvc;
        this.cardHolder = cardHolder;
    }


}

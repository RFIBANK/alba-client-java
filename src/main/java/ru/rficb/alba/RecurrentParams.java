package ru.rficb.alba;

/**
 * Параметры для рекуррентного платежа
 */
public class RecurrentParams {
    RecurrentType type;
    String comment;
    String url;
    String orderId;
    RecurrentPeriodType period;

    public RecurrentType getType() {
        return type;
    }

    public void setType(RecurrentType type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public RecurrentPeriodType getPeriod() {
        return period;
    }

    public void setPeriod(RecurrentPeriodType period) {
        this.period = period;
    }

    private RecurrentParams(RecurrentType type, String comment, String url, String orderId, RecurrentPeriodType period) {
        this.type = type;
        this.comment = comment;
        this.url = url;
        this.orderId = orderId;
        this.period = period;
    }

    public static RecurrentParams first(String url, String comment) {
        return new RecurrentParams(RecurrentType.FIRST, comment, url, null, RecurrentPeriodType.BY_REQUEST);
    }

    public static RecurrentParams next(String orderId) {
        return new RecurrentParams(RecurrentType.NEXT, null, null, orderId, null);
    }

}

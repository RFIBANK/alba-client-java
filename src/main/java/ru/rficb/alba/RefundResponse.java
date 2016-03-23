package ru.rficb.alba;


/**
 * Ответ на инициацию платежа
 */
public class RefundResponse {
    private int transactionId;
    private int paybackId;

    public int getTransactionId() {
        return transactionId;
    }

    public int getPaybackId() {
        return paybackId;
    }

    public RefundResponse(int transactionId, int paybackId) {
        this.transactionId = transactionId;
        this.paybackId = paybackId;
    }

}

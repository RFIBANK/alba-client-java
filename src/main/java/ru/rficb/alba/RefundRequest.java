package ru.rficb.alba;

import java.math.BigDecimal;

/**
 * Параметры запроса на возврат
 */
public class RefundRequest {

    private int transactionId;
    private BigDecimal amount;
    private String reason;
    private boolean test;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public RefundRequest(int transactionId) {
        setTransactionId(transactionId);
        setTest(false);
    }

    public RefundRequest(int transactionId, BigDecimal amount, String reason, boolean test) {
        setTransactionId(transactionId);
        setAmount(amount);
        setReason(reason);
        setTest(test);
    }

    public static RefundRequestBuilder builder(int transactionId) {
        return new RefundRequestBuilder(transactionId);
    }

    public static final class RefundRequestBuilder {
        private int transactionId;
        private BigDecimal amount;
        private String reason;
        private boolean test;

        RefundRequestBuilder(int transactionId) {
            setTransactionId(transactionId);
        }

        public int getTransactionId() {
            return transactionId;
        }

        public RefundRequestBuilder setTransactionId(int transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public RefundRequestBuilder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public String getReason() {
            return reason;
        }

        public RefundRequestBuilder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public boolean isTest() {
            return test;
        }

        public RefundRequestBuilder setTest(boolean test) {
            this.test = test;
            return this;
        }

        public RefundRequest build() {
            return new RefundRequest(
                    getTransactionId(),
                    getAmount(),
                    getReason(),
                    isTest()
            );
        }
    }
}

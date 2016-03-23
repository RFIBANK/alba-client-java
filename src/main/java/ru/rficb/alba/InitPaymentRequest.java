package ru.rficb.alba;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Параметры запроса ни инициацию платежа
 */
public class InitPaymentRequest {

    private String paymentType;
    private String key;
    private String secret;
    private BigDecimal cost;
    private String name;
    private String email;
    private String phone;
    private String orderId;
    private CommissionMode commissionMode;
    private boolean backgound = true;

    public InitPaymentRequest() {

    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isBackgound() {
        return backgound;
    }

    public void setBackgound(boolean backgound) {
        this.backgound = backgound;
    }

    public CommissionMode getCommissionMode() {
        return commissionMode;
    }

    public void setCommissionMode(CommissionMode commissionMode) {
        this.commissionMode = commissionMode;
    }

    public InitPaymentRequest(String paymentType, String key, String secret, BigDecimal cost,
                              String name, String email, String phone, String orderId,
                              CommissionMode commissionMode) {
        this.paymentType = paymentType;
        this.key = key;
        this.secret = secret;
        this.cost = cost;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.orderId = orderId;
        this.commissionMode = commissionMode;
    }

    public InitPaymentRequestBuilder builder() {
        return new InitPaymentRequestBuilder();
    }

    public Map<String, String> getParams() {

        Map<String, String> params = new HashMap<String, String>();

        params.put("payment_type", paymentType);
        params.put("version", "2.1");
        if (key != null) {
            params.put("key", key);
        }

        if (commissionMode != null) {
            params.put("commission", commissionMode.toString());
        }
        params.put("cost", cost.toString());
        params.put("name", name);
        params.put("email", email);
        params.put("phone_number", phone);
        params.put("background", backgound?"1":"0");
        if (orderId != null) {
            params.put("order_id", orderId);
        }

        return params;
    }

    public static final class InitPaymentRequestBuilder {
        private String paymentType;
        private String key;
        private String secret;
        private BigDecimal cost;
        private String name;
        private String email;
        private String phone;
        private String orderId;
        private CommissionMode commissionMode;

        public InitPaymentRequestBuilder() {

        }

        public InitPaymentRequestBuilder setPaymentType(String paymentType) {
            this.paymentType = paymentType;
            return this;
        }

        public InitPaymentRequestBuilder setKey(String key) {
            this.key = key;
            return this;
        }

        public InitPaymentRequestBuilder setCost(BigDecimal cost) {
            this.cost = cost;
            return this;
        }

        public InitPaymentRequestBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public InitPaymentRequestBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public InitPaymentRequestBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public InitPaymentRequestBuilder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public InitPaymentRequestBuilder setsecret(String secret) {
            this.secret = secret;
            return this;
        }

        public CommissionMode getCommissionMode() {
            return commissionMode;
        }

        public InitPaymentRequestBuilder setCommissionMode(CommissionMode commissionMode) {
            this.commissionMode = commissionMode;
            return this;
        }

        public InitPaymentRequest build() {
            return new InitPaymentRequest(
                    paymentType,
                    key,
                    secret,
                    cost,
                    name,
                    email,
                    phone,
                    orderId,
                    commissionMode
            );
        }
    }

}

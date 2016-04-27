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
    private boolean background = true;
    private InitTestType test;
    private String cardToken;
    private RecurrentParams recurrentParams;

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

    public boolean isBackground() {
        return background;
    }

    public void setBackground(boolean background) {
        this.background = background;
    }

    public CommissionMode getCommissionMode() {
        return commissionMode;
    }

    public void setCommissionMode(CommissionMode commissionMode) {
        this.commissionMode = commissionMode;
    }

    public InitTestType isTest() {
        return test;
    }

    public void setTest(InitTestType test) {
        this.test = test;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public RecurrentParams getRecurrentParams() {
        return recurrentParams;
    }

    public void setRecurrentParams(RecurrentParams recurrentParams) {
        this.recurrentParams = recurrentParams;
    }

    public InitPaymentRequest(String paymentType, String key, String secret, BigDecimal cost,
                              String name, String email, String phone, String orderId,
                              CommissionMode commissionMode, InitTestType test, String cardToken,
                              RecurrentParams recurrentParams) {
        this.paymentType = paymentType;
        this.key = key;
        this.secret = secret;
        this.cost = cost;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.orderId = orderId;
        this.commissionMode = commissionMode;
        this.test = test;
        this.cardToken = cardToken;
        this.recurrentParams = recurrentParams;
    }

    public static InitPaymentRequestBuilder builder() {
        return new InitPaymentRequestBuilder();
    }

    public Map<String, String> getParams() throws AlbaFatalError {

        Map<String, String> params = new HashMap<>();

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
        if (email != null) {
            params.put("email", email);
        }
        if (phone != null) {
            params.put("phone_number", phone);
        }
        params.put("background", background ?"1":"0");
        if (orderId != null) {
            params.put("order_id", orderId);
        }

        if (cardToken != null) {
            params.put("card_token", cardToken);
        }

        if (recurrentParams != null) {
            if (recurrentParams.type == RecurrentType.FIRST) {
                params.put("recurrent_type", "first");
                params.put("recurrent_comment", recurrentParams.getComment());
                params.put("recurrent_url", recurrentParams.getUrl());
                params.put("recurrent_period", recurrentParams.getPeriod().toString());
            } else {
                if (!background) {
                    throw new AlbaFatalError("When recurrent_type is \"next\" then background should be \"1\"");
                } else {
                    params.put("recurrent_type", "next");
                    params.put("recurrent_order_id", recurrentParams.getOrderId());
                }
            }
        }

        params.put("test", test.toString());

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
        private InitTestType test;
        private String cardToken;
        private RecurrentParams recurrentParams;

        public InitPaymentRequestBuilder() {
            test = InitTestType.NONE;
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

        public InitPaymentRequestBuilder setSecret(String secret) {
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

        public InitTestType isTest() {
            return test;
        }

        public InitPaymentRequestBuilder setTest(InitTestType test) {
            this.test = test;
            return this;
        }

        public String getCardToken() {
            return cardToken;
        }

        public RecurrentParams getRecurrentParams() {
            return recurrentParams;
        }

        public InitPaymentRequestBuilder setRecurrentParams(RecurrentParams recurrentParams) {
            this.recurrentParams = recurrentParams;
            return this;
        }

        public InitPaymentRequestBuilder setCardToken(String cardToken) {
            this.cardToken = cardToken;
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
                    commissionMode,
                    test,
                    cardToken,
                    recurrentParams
            );
        }
    }

}

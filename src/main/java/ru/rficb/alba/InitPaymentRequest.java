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
    private String comment;
    private CommissionMode commissionMode;
    private boolean background = true;
    private InitTestType test;
    private String cardToken;
    private RecurrentParams recurrentParams;
    private InvoiceData invoiceData;
    private ExtendedSet extendedSet;

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

    

    public void setBackground(boolean background) {
        this.background = background;
    }
    
    public boolean isBackground() {
        return background;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public InvoiceData getInvoiceData() {
        return invoiceData;
    }

    public void setInvoiceData(InvoiceData invoiceData) {
        this.invoiceData = invoiceData;
    }
    
    public ExtendedSet getExtendedSet() {
    	return extendedSet;
    }
    
    public void setExtendedSet(ExtendedSet extendedSet) {
    	this.extendedSet = extendedSet;
    }

    public InitPaymentRequest(String paymentType, String key, String secret, BigDecimal cost,
                              String name, String email, String phone, String orderId, String comment,
                              CommissionMode commissionMode, InitTestType test, String cardToken,
                              RecurrentParams recurrentParams, InvoiceData invoiceData, ExtendedSet extendedSet) {
        this.paymentType = paymentType;
        this.key = key;
        this.secret = secret;
        this.cost = cost;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.orderId = orderId;
        this.comment = comment;
        this.commissionMode = commissionMode;
        this.test = test;
        this.cardToken = cardToken;
        this.recurrentParams = recurrentParams;
        this.invoiceData = invoiceData;
        this.extendedSet = extendedSet;
    }

    public static InitPaymentRequestBuilder builder() {
        return new InitPaymentRequestBuilder();
    }

    public Map<String, String> getParams() throws AlbaFatalError {

        Map<String, String> params = new HashMap<>();
        
        if (paymentType != null) {
            params.put("payment_type", paymentType);
        }
        
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

        if (comment != null) {
            params.put("comment", comment);
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

        if (invoiceData != null) {
            params.put("invoice_data", invoiceData.getParams().toString());
        }
        
        if (extendedSet != null) {
        	if (extendedSet.getTransferType().equals("bank")) {
        		params.put("transfer_type", "bank");
        		params.put("payer_name", extendedSet.getPayerName());
        		params.put("recipient_name", extendedSet.getRecipientName());
        		params.put("recipient_inn", extendedSet.getRecipientInn());
        		params.put("recipient_account", extendedSet.getRecipientAccount());
        		params.put("recipient_bank_name", extendedSet.getRecipientBankName());
        		params.put("recipient_bank_id", extendedSet.getRecipientBankId());
        		params.put("recipient_bank_correspondent_account", extendedSet.getRecipientBankCorrespondentAccount());
        	} else {
        		params.put("101_payer_type", extendedSet.getPayerType101());
        		params.put("103_kpp", extendedSet.getKpp103());
        		params.put("104_kbk", extendedSet.getKbk104());
        		params.put("105_okato", extendedSet.getOkato105());
        		params.put("106_payment_reason", extendedSet.getPaymentReason106());
        		params.put("107_tax_period", extendedSet.getTaxPeriod107());
        		params.put("108_tax_doc_num", extendedSet.getTaxDocNum108());
        		params.put("109_tax_doc_date", extendedSet.getTaxDocDate109());
        		params.put("110_payment_type", extendedSet.getPaymentType110());
        		params.put("22_kod", extendedSet.getKod22());
        		params.put("emp_system_id", extendedSet.getEmpSystemId());
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
        private String comment;
        private CommissionMode commissionMode;
        private InitTestType test;
        private String cardToken;
        private boolean background;
        private RecurrentParams recurrentParams;
        private InvoiceData invoiceData;
        private ExtendedSet extendedSet;

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

        public InitPaymentRequestBuilder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public InitPaymentRequestBuilder setSecret(String secret) {
            this.secret = secret;
            return this;
        }

        public InitPaymentRequestBuilder setCommissionMode(CommissionMode commissionMode) {
            this.commissionMode = commissionMode;
            return this;
        }

        public InitPaymentRequestBuilder setTest(InitTestType test) {
            this.test = test;
            return this;
        }

        public InitPaymentRequestBuilder setRecurrentParams(RecurrentParams recurrentParams) {
            this.recurrentParams = recurrentParams;
            return this;
        }

        public InitPaymentRequestBuilder setCardToken(String cardToken) {
            this.cardToken = cardToken;
            return this;
        }

        public InitPaymentRequestBuilder setInvoiceData(InvoiceData invoiceData) {
            this.invoiceData = invoiceData;
            return this;
        }

        public InitPaymentRequestBuilder setBackground(boolean background) {
            this.background = background;
            return this;
        }
        
        public InitPaymentRequestBuilder setExtendedSet (ExtendedSet extendedSet) {
        	this.extendedSet = extendedSet;
        	return this;
        }

        public CommissionMode getCommissionMode() {
            return commissionMode;
        }

        public String getCardToken() {
            return cardToken;
        }

        public RecurrentParams getRecurrentParams() {
            return recurrentParams;
        }

        public InvoiceData getInvoiceData() {
            return invoiceData;
        }
        
        public ExtendedSet getExtendedSet() {
        	return extendedSet;
        }
        
        public InitTestType isTest() {
            return test;
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
                    comment,
                    commissionMode,
                    test,
                    cardToken,
                    recurrentParams,
                    invoiceData,
                    extendedSet
            );
        }
    }

}

package ru.rficb.alba;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

/**
 * Параметры запроса ни инициацию платежа
 */
@Builder()
@Getter
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
    
    @Builder.Default
    private boolean background = true;
    
    @Builder.Default
    private InitTestType test = InitTestType.NONE;
    
    private String cardToken;
    private RecurrentParams recurrentParams;
    private InvoiceData invoiceData;
    private TransferTypeBank transferTypeBank;
    private TransferTypeBankGov transferTypeBankGov;

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
        
        if (transferTypeBank != null) {
        	params.put("transfer_type", transferTypeBank.getTransferType().toString());
    		params.put("payer_name", transferTypeBank.getPayerName());
    		params.put("recipient_name", transferTypeBank.getRecipientName());
    		params.put("recipient_inn", transferTypeBank.getRecipientInn());
    		params.put("recipient_account", transferTypeBank.getRecipientAccount());
    		params.put("recipient_bank_name", transferTypeBank.getRecipientBankName());
    		params.put("recipient_bank_id", transferTypeBank.getRecipientBankId());
    		params.put("recipient_bank_correspondent_account", transferTypeBank.getRecipientBankCorrespondentAccount());
        }
        
        if (transferTypeBankGov != null) {
        	params.put("transfer_type", transferTypeBankGov.getTransferType().toString());
        	params.put("101_payer_type", transferTypeBankGov.getPayerType101());
        	params.put("103_kpp", transferTypeBankGov.getKpp103());
        	params.put("104_kbk", transferTypeBankGov.getKbk104());
        	params.put("105_okato", transferTypeBankGov.getOkato105());
        	params.put("106_payment_reason", transferTypeBankGov.getPaymentReason106());
        	params.put("107_tax_period", transferTypeBankGov.getTaxPeriod107());
        	params.put("108_tax_doc_num", transferTypeBankGov.getTaxDocNum108());
        	params.put("109_tax_doc_date", transferTypeBankGov.getTaxDocDate109());
        	params.put("110_payment_type", transferTypeBankGov.getPaymentType110());
        	params.put("22_kod", transferTypeBankGov.getKod22());
        	params.put("emp_system_id", transferTypeBankGov.getEmpSystemId());
        	
        }
        
        params.put("test", test.toString());
        return params;
    }

}

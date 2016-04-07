package ru.rficb.alba;

import org.json.JSONException;
import org.json.JSONObject;


public class TransactionDetails {
    private TransactionStatus status;
    private int transactionId;
    private String serviceName;
    private int serviceId;
    private String help;

    public TransactionDetails(JSONObject jsonObject) throws AlbaTemporaryError {
        try {
            transactionId = jsonObject.getInt("tid");
        } catch (JSONException e) {
            throw new AlbaTemporaryError("Answer does't contain a transaction tid");
        }
        try {
            status = TransactionStatus.valueOf(jsonObject.getString("transaction_status").toUpperCase());
        } catch (JSONException e) {
            throw new AlbaTemporaryError("Answer does't contain a transaction_status");
        }
        try {
            serviceName = jsonObject.getString("service");
        } catch (JSONException e) {
            throw new AlbaTemporaryError("Answer does't contain a service");
        }
        try {
            serviceId = jsonObject.getInt("service_id");
        } catch (JSONException e) {
            throw new AlbaTemporaryError("Answer does't contain a transaction service_id");
        }
        try {
            help = jsonObject.getString("help");
        } catch (JSONException e) {
            help = null;
        }
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getHelp() {
        return help;
    }
}
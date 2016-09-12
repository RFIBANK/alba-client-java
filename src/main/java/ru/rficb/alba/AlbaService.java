package ru.rficb.alba;

import java.io.*;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AlbaService {
    protected ConnectionProfile connectionProfile;

    private Integer serviceId;
    private String key;
    private String secret;
    private Logger logger;
    private RestRequester requester;
    private static final Map<String, String> initPaymentRequired;
    static {
        Map<String, String> aMap = new HashMap<>();
        aMap.put("payment_type", "paymentType");
        aMap.put("cost", "cost");
        aMap.put("name", "name");
        initPaymentRequired = Collections.unmodifiableMap(aMap);
    }

    public AlbaService() {
        this.key = null;
        this.logger = Logger.getLogger(AlbaService.class.getName());
        this.requester = new RestRequester(this.logger);
        this.connectionProfile = ConnectionProfile.first();
    }

    public AlbaService(int serviceId, String secret) {
        this();
        setServiceId(serviceId);
        setSecret(secret);
    }

    public AlbaService(String key) {
        this();
        setKey(key);
    }

    public void setConnectionProfile(ConnectionProfile connectionProfile) {
        this.connectionProfile = connectionProfile;
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

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public int getPendingRetries() {
        return requester.getPendingRetries();
    }

    public void setPendingRetries(int pendingRetries) {
        requester.setPendingRetries(pendingRetries);
    }

    private String sign(String method, String url, Map<String, String> params, String secret) throws AlbaFatalError {
        try {
            return AlbaSigner.sign(method, url, params, secret);
        } catch (URISyntaxException e) {
            throw new AlbaFatalError("Can't sign request: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new AlbaFatalError("Can't sign request: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new AlbaFatalError("Can't sign request: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new AlbaFatalError("Can't sign request: " + e.getMessage());
        }
    }

    private static <T> String implode(String glue, List<T> list) {

        if (list == null || list.isEmpty()) {
            return "";
        }

        Iterator<T> iterator = list.iterator();

        StringBuilder sb = new StringBuilder();
        sb.append(iterator.next());

        while (iterator.hasNext()) {
            sb.append(glue).append(iterator.next());
        }

        return sb.toString();
    }

    /**
     * Вызывает исключения в случае неуспешного ответа в jsonObject
     *
     * @param jsonObject
     * @throws AlbaTemporaryError, AlbaFatalError
     * */
    private void throwForError(JSONObject jsonObject) throws AlbaTemporaryError, AlbaFatalError {
        try {
            if (!jsonObject.get("status").equals("success")) {
                String code;
                String msg;

                if (jsonObject.has("code")) {
                    code = jsonObject.getString("code");
                } else {
                    code = "unknown";
                }

                if (jsonObject.has("msg")) {
                    msg = jsonObject.getString("msg");
                } else {
                    msg = jsonObject.getString("message");
                }

                throw new AlbaFatalError(msg, AlbaErrorCode.fromString(code));
            }
        } catch (JSONException e) {
            throw new AlbaTemporaryError(e.toString());
        }
    }

    /**
     * Получение списка доступных способов оплаты для сервиса
     * */
    public Set<String> paymentTypes() throws AlbaFatalError, AlbaTemporaryError {

        if (secret == null) {
            throw new AlbaFatalError("The parameter secret is required");
        }

        String url = connectionProfile.getBaseUrl() + "alba/pay_types/";
        HashMap<String, String> params = new HashMap<>();
        params.put("service_id", serviceId.toString());
        params.put("version", "2.0");
        params.put("check", sign("GET", url, params, secret));

        try {
            JSONObject result = requester.getRequest(url, params);
            throwForError(result);

            JSONArray data = result.getJSONArray("types");
            Set<String> paymentTypes = new HashSet<>();

            for(int i = 0; i < data.length(); i++) {
                try {
                    paymentTypes.add(data.get(i).toString());
                } catch (JSONException e) {
                    throw new AlbaTemporaryError("Can't parse response: " + e.getMessage());
                }
            }

            return paymentTypes;
        } catch (IOException e) {
            throw new AlbaTemporaryError("Can't send request: " + e.getMessage());
        }
    }

    /**
     * Иницировать платеж
     *
     * @param request параметры платежа
     * @return данные о транзакакции
     * */
    public InitPaymentResponse initPayment(InitPaymentRequest request)
            throws AlbaTemporaryError, AlbaFatalError {
        Map<String, String> params = request.getParams();
        String url = connectionProfile.getBaseUrl() + "alba/input";

        if (request.getKey() == null) {
            if (this.key != null) {
                params.put("key", this.key);
            } else {
                String secret = request.getSecret();

                if (secret == null) {
                    secret = this.getSecret();
                }

                if (secret == null) {
                    throw new AlbaFatalError("The parameter key or secret is required");
                }

                Integer serviceId = getServiceId();
                if (serviceId == null) {
                    throw new AlbaFatalError("The parameter serviceId is required");
                }

                params.put("service_id", serviceId.toString());
                params.put("check", sign("POST", url, params, secret));
            }
        }

        for (String key: initPaymentRequired.keySet()) {
            if (!params.containsKey(key) || params.get(key) == null) {
                throw new AlbaFatalError("The parameter " + initPaymentRequired.get(key) + " is required");
            }
        }

        try {
            JSONObject result = requester.postRequest(url, params);
            throwForError(result);
            return new InitPaymentResponse(result);
        } catch (IOException e) {
            throw new AlbaTemporaryError(e.getMessage());
        }
    }

    /**
     * Получение статуса транзакции
     *
     * @param sessionKey сессионный ключ
     * @return информация о транзакции
     * @throws AlbaTemporaryError, AlbaFatalError
     * */
    public TransactionDetails transactionDetails(String sessionKey) throws AlbaTemporaryError, AlbaFatalError {

        Map<String, String> params = new HashMap<>();
        params.put("session_key", sessionKey);
        params.put("version", "2.1");

        try {
            JSONObject result = requester.postRequest(connectionProfile.getBaseUrl() + "alba/details/", params);
            throwForError(result);
            return new TransactionDetails(result);
        } catch (IOException e) {
            throw new AlbaTemporaryError(e.getMessage());
        }
    }

    /**
     * Запрос на проведение возврата
     */
    public RefundResponse refund(RefundRequest request) throws AlbaFatalError, AlbaTemporaryError {

        if (secret == null) {
            throw new AlbaFatalError("The parameter secret is required");
        }
        String url = connectionProfile.getBaseUrl() + "alba/refund/";
        Map<String, String> params = new HashMap<>();
        params.put("tid", String.valueOf(request.getTransactionId()));
        params.put("version", "2.0");

        if (request.getAmount() != null) {
            params.put("amount", request.getAmount().toString());
        }

        if (request.getReason() != null) {
            params.put("reason", request.getReason().toString());
        }

        if (request.isTest()) {
            params.put("test", "1");
        }

        params.put("check", sign("POST", url, params, secret));

        try {
            JSONObject result = requester.postRequest(url, params);
            throwForError(result);

            try {
                return new RefundResponse(request.getTransactionId(), result.getInt("payback_id"));
            } catch (JSONException e) {
                throw new AlbaTemporaryError("The response does't contain a payback_id");
            }

        } catch (IOException e) {
            throw new AlbaTemporaryError("Can't refund: " + e.getMessage());
        }

    }

    /**
     * Создание токена для оплаты
     */
    public CardTokenResponse createCardToken(CardTokenRequest request, boolean test) throws AlbaTemporaryError, AlbaFatalError {
        Map<String, String> params = new HashMap<>();

        params.put("service_id", String.valueOf(request.getServiceId()));
        params.put("card", request.getCard());
        String month = String.valueOf(request.getExpMonth());
        if (month.length() == 1) {
            month = "0" + month;
        }
        params.put("exp_month", month);
        params.put("exp_year", String.valueOf(request.getExpYear()));
        params.put("cvc", request.getCvc());
        if (request.getCardHolder() != null) {
            params.put("card_holder", request.getCardHolder());
        }

        JSONObject result;
        try {
            String url = test?connectionProfile.getCardTokenTestUrl():connectionProfile.getCardTokenUrl();
            result = requester.postRequest(url + "create", params);
            return new CardTokenResponse(result);

        } catch (IOException e) {
            throw new AlbaTemporaryError("Can't create card token: " + e.getMessage());
        }
    }


}
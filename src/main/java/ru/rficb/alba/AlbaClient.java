package ru.rficb.alba;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;


public class AlbaClient {
	protected String baseUrl = "https://partner.rficb.ru/";
	private String secureKey;
	
	public AlbaClient(String key)
	{
		this.secureKey = key;	
	}
	
	private JSONObject postRequest(String url, Map<String, String> params) throws IOException 
	{
		StringBuilder sb = new StringBuilder();
		
		for (Entry<String, String> entry : params.entrySet()) {
   		    if (sb.length() > 0) {
   		    	sb.append("&");
            }

			sb.append(String.format("%s=%s", entry.getKey(),entry.getValue()));	            
		}
		String urlParameters = sb.toString();
		
		byte[] postData = urlParameters.getBytes(Charset.forName( "UTF-8" ));
		int postDataLength = postData.length;
		URL urlInstance = new URL(url);
		
		URLConnection con = null;
		con = urlInstance.openConnection();

		con.setDoOutput(true);
		con.setDoInput(true);
		
		if (con instanceof HttpsURLConnection) {
			((HttpsURLConnection) con).setInstanceFollowRedirects(false);
			((HttpsURLConnection) con).setRequestMethod("POST");						
		} else {
			((HttpURLConnection) con).setInstanceFollowRedirects(false);
			((HttpURLConnection) con).setRequestMethod("POST");			
		}
		
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		con.setRequestProperty("charset", "utf-8");
		con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
		con.setUseCaches( false );
		try( DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
		   wr.write( postData );
		}
		
		java.io.InputStream ins = con.getInputStream();
        InputStreamReader isr = new InputStreamReader(ins);
        BufferedReader in = new BufferedReader(isr);


        StringBuilder builder = new StringBuilder();		
		
		String line;
		while ((line = in.readLine()) != null) {
			builder.append(line);
		}
				
		return new JSONObject(builder.toString());
	}
	
	/**
	 * Вызывает исключения в случае неуспешного ответа в jsonObject
	 * 
	 * @param jsonObject 
	 * @throws AlbaTemporaryError, AlbaFatalError 
	 * */
	protected void throwWhenError(JSONObject jsonObject) throws AlbaTemporaryError, AlbaFatalError {
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
				String error = String.format("[%s] %s", code, msg);
				throw new AlbaFatalError(error);
			}
		} catch (JSONException e) {
			throw new AlbaTemporaryError(e.toString());
		}
	}
	
	/**
	 * Иницировать платеж
	 * 
	 * @param payType способ оплаты
	 * @param name название платежа
	 * @param email email клиента (допустимо null если это позволяет способ оплаты)
	 * @param phone номер телефона (допустимо null если это позволяет способ оплаты)
	 * @param orderId уникальный для сервиса номер заказа (в случае отсутсвия укажите 0)
	 * @param cost стоисмость услуги в рублях, которую должен оплатить клиент
	 * @return данные о транзакакции	
	 * */	
	public InitPaymentAnswer initPayment(String payType, float cost, String name, String email, String phone, String orderId) throws AlbaTemporaryError, AlbaFatalError {		
		Map<String, String> params = new HashMap<String, String>();		
		
		params.put("payment_type", payType);
		params.put("version", "2.1");			
		params.put("key", this.secureKey);
		params.put("cost", Float.toString(cost));
		params.put("name", name);
		params.put("email", email);
		params.put("phone_number", phone);
		params.put("background", "1");
		
		if (orderId != null) {
			params.put("orderId", orderId);
		}
		 
		try {
			JSONObject result = postRequest(baseUrl + "alba/input/", params);
			throwWhenError(result);
			return new InitPaymentAnswer(result);
		} catch (IOException e) {			
			throw new AlbaTemporaryError(e.getMessage());
		}
    }
	
	/**
	 * Получение статуса транзакции
	 * 
	 * @param sessionKey - сессионный ключ
	 * @return информация о транзакции
	 * @throws AlbaTemporaryError, AlbaFatalError 
	 * */
	public TransactionDetails transactionDetails(String sessionKey) throws AlbaTemporaryError, AlbaFatalError {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("session_key", sessionKey);
		params.put("version", "2.1");
		
		try {
			JSONObject result = postRequest(baseUrl + "alba/details/", params);
			throwWhenError(result);
			return new TransactionDetails(result);
		} catch (IOException e) {
			throw new AlbaTemporaryError(e.getMessage());
		}
	} 	
}
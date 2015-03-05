package ru.rficb.alba;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Данные, получаемые в ответе на запрос инициации платежа
 * */
public class InitPaymentAnswer {
	private int transactionId;
	private String terminalCode;
	private String sessionKey;
	private String help;
	
	public InitPaymentAnswer(JSONObject jsonObject) throws AlbaTemporaryError {
		try {
			this.transactionId = jsonObject.getInt("tid");
		} catch (JSONException e) {
			throw new AlbaTemporaryError("Answer does't contain a transaction tid");			
		}
		try {
			this.terminalCode = jsonObject.getString("terminal_code");
		} catch (JSONException e) {
			this.terminalCode = null;
		}	
		try {
			this.help = jsonObject.getString("help");
			if (this.help.equals("false")) {
				this.help = null;	
			} 
		} catch (JSONException e) {
			this.help = null;
		}		
		try {
			this.sessionKey = jsonObject.getString("session_key");
		} catch (JSONException e) {
			this.sessionKey = null;
		}	
	}
	
	public int getTransactionId() {
		return transactionId;
	}
	
	public String getTerminalCode() {
		return terminalCode;
	}
	
	public String getHelp() {
		return help;
	}

	public String getSessionKey() {
		return sessionKey;
	}
}

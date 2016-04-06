package ru.rficb.alba;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Данные, получаемые в ответе на запрос инициации платежа
 * */
public class InitPaymentResponse {
	private int transactionId;
	private String terminalCode;
	private String sessionKey;
	private String help;
	private Card3ds card3ds;

	public InitPaymentResponse(JSONObject jsonObject) throws AlbaTemporaryError {
		try {
			this.transactionId = jsonObject.getInt("tid");
		} catch (JSONException e) {
			throw new AlbaTemporaryError("The response does't contain a transaction tid");
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

        try {
            JSONObject data = jsonObject.getJSONObject("3ds");
            card3ds = new Card3ds(
                    data.getString("ACSUrl"),
                    data.getString("MD"),
                    data.getString("PaReq")
            );
        } catch (JSONException e) {
            this.card3ds = null;
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

    public Card3ds getCard3ds() {return card3ds; }
}

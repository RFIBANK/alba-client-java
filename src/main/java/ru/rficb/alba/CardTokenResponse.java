package ru.rficb.alba;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Ответ на запрос получения токена
 */
public class CardTokenResponse {
    private String token;
    public HashMap<String, List<String>> errors;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<String> getCardErrors() {
        return errors.get("card");
    }

    public List<String> getCardHolderErrors() {
        return errors.get("holder");
    }

    public List<String> getExpMonthErrors() {
        return errors.get("exp_month");
    }

    public List<String> getExpYearErrors() {
        return errors.get("exp_year");
    }

    public List<String> getCvcErrors() {
        return errors.get("cvc");
    }


    public CardTokenResponse(JSONObject jsonObject) {
        this.errors = new HashMap<>();

        if (jsonObject.get("status").equals("success")) {
            setToken(jsonObject.get("token").toString());
        } else {
            if (jsonObject.has("errors") && jsonObject.getJSONObject("errors") != null) {
                JSONObject jsonErrors = jsonObject.getJSONObject("errors");

                Iterator<?> keys = jsonErrors.keys();

                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    List<String> Error;

                    JSONArray errorsArray = jsonErrors.getJSONArray(key);

                    for(int i = 0; i < errorsArray.length(); i++) {
                        String error = errorsArray.getString(i);

                        if (!this.errors.containsKey(key)) {
                            this.errors.put(key, new LinkedList<String>());
                        }
                        this.errors.get(key).add(error);
                    }

                }
            }
        }
    }
}

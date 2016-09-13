package ru.rficb.alba;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.net.URI;
import java.net.URISyntaxException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Подпись версии 2.0+
 */
public class AlbaSigner {

    public static String sign(String method, String url, Map<String, String> params, String secretKey)
            throws URISyntaxException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, AlbaFatalError {

        URI uri = new URI(url);

        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();
        for (String key: keys) {
            if (sb.length() > 0) {
                sb.append("&");
            }

            sb.append(String.format(
                    "%s=%s",
                    key,
                    URLEncoder.encode(params.get(key), "UTF-8")
                            .replaceAll("[+]", "%20")
            ));
        }
        String urlParameters = sb.toString();
        String data = method.toUpperCase() + "\n" +
                uri.getHost() + "\n" +
                uri.getPath() + "\n" +
                urlParameters;

        Mac hmacInstance = Mac.getInstance("HmacSHA256");
        Charset charSet = Charset.forName("UTF-8");
        SecretKeySpec keySpec = new javax.crypto.spec.SecretKeySpec(charSet.encode(secretKey).array(), "HmacSHA256");
        hmacInstance.init(keySpec);

        return Base64Encoder.getInstance().encode(hmacInstance.doFinal(data.getBytes("UTF-8")));
    }
}

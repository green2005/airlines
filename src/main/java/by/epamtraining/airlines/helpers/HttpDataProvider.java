package by.epamtraining.airlines.helpers;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import java.io.IOException;

public class HttpDataProvider {

    private HttpDataProvider() {
    }

    public static String getJSONStringFromUrl(String urlStr) throws IOException {
        if (urlStr == null || urlStr.isEmpty()) throw new IllegalArgumentException("urlStr cannot be null");
        Request request = new Request.Builder()
                .url(urlStr)
                 .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        return  response.body().string();
    }

}

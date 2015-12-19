package com.example.alex.dstuapp.network;


import android.content.Context;
import android.util.Log;

import com.example.alex.dstuapp.App;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class RequestManager {
    public static final String BASE_SERVER_URL = "http://ec.donstu.ru/site/ci/";

    public static final String COOKIES = "PHPSESSION";
    public static final String TEMPLATE = "template";
    public static final String TEMPLATE_VALUE = "smartphoneApi";

    private static final long CONNECTION_TIMEOUT = 60;
    private static final long READ_TIMEOUT = 60;

    public static final String LOG_TAG = RequestManager.class.getSimpleName();

    private static RequestManager instance;
    private Retrofit retrofit;
    private final Context context;
    private ServerMethods service;

    private RequestManager() {
        context = App.getInstance();
        initRetrofit();
    }

    public static synchronized RequestManager getInstance() {
        if (instance == null) {
            instance = new RequestManager();
        }
        return instance;
    }

    private void initRetrofit() {
        //HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        // set your desired log level
        //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Add the interceptor to OkHttpClient
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        client.setReadTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(context),
                CookiePolicy.ACCEPT_ALL);
        client.setCookieHandler(cookieManager);

        //client.interceptors().add(interceptor);
        client.interceptors().add(new LoggingInterceptor());
        /*client.interceptors().add(chain -> {
            Request original = chain.request();

            String cookies = Prefs.getCookies();
            if (TextUtils.isEmpty(cookies) || !Prefs.isLoggedIn()) {
                cookies = CookiesUtils.generate();
                Prefs.saveCookies(cookies);
            }

            Log.d(LOG_TAG, "cookies = " + cookies);

            Request request = original.newBuilder()
                    .header(COOKIES, cookies)
                    .header(TEMPLATE, TEMPLATE_VALUE)
                    .build();

            return chain.proceed(request);
        });*/

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        service = retrofit.create(ServerMethods.class);
    }

    public ServerMethods getServiceMethods() {
        return service;
    }

    public static class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            String requestLog = String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers());
            //YLog.d(String.format("Sending request %s on %s%n%s",
            //        request.url(), chain.connection(), request.headers()));
            if(request.method().compareToIgnoreCase("post")==0){
                requestLog ="\n"+requestLog+"\n"+bodyToString(request);
            }
            Log.d("TAG","request"+"\n"+requestLog);

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            String responseLog = String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers());

            String bodyString = response.body().string();

            Log.d("TAG","response"+"\n"+responseLog+"\n"+bodyString);

            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();
            //return response;
        }
    }

    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

}

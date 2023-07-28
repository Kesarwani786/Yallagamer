package com.augurs.yallagamers.UserInterface.payment.PaymentGatewayRequest;

import com.augurs.yallagamers.api_module.AppConstants;
import com.google.gson.GsonBuilder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentGatewayClient {
    private static final TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }
    };
    private static final SSLContext trustAllSslContext;
    static {
        try {
            trustAllSslContext = SSLContext.getInstance("SSL");
            trustAllSslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }
    private static final SSLSocketFactory trustAllSslSocketFactory = trustAllSslContext.getSocketFactory();
    private static OkHttpClient trustAllSslClient(OkHttpClient client) {
        OkHttpClient.Builder builder = client.newBuilder();
        builder.sslSocketFactory(trustAllSslSocketFactory, (X509TrustManager) trustAllCerts[0]);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        return builder.build();
    }


    //    orderData
    public static Retrofit getAccountClient() {
        String DOMAIN_URL = "https://api-gateway.sandbox.ngenius-payments.com/";
//        String DOMAIN_URL = "http://www.test.abzercommerce.com/PHP/slim.php/";
        Retrofit retrofit_login;
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(AppConstants.DEFAULT_CONNECT_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
                .writeTimeout(AppConstants.DEFAULT_WRITE_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
                .readTimeout(AppConstants.DEFAULT_READ_TIMEOUT_IN_SEC, TimeUnit.SECONDS);
        okHttpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        });

        retrofit_login = new Retrofit.Builder()
                .baseUrl(DOMAIN_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .client(trustAllSslClient(okHttpClient.build()))
                .build();

        return retrofit_login;
    }






}



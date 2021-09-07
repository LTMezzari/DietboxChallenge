package mezzari.torres.lucas.dietbox_challenge.network;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import javax.inject.Singleton;

import mezzari.torres.lucas.dietbox_challenge.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
@Singleton
public final class NetworkImpl implements INetwork {

    private final String baseUrl;
    private Retrofit retrofitInstance;

    public NetworkImpl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    @NotNull
    public <T> T build(@NotNull Class<T> jClass) {
        return getRetrofit().create(jClass);
    }

    private Retrofit createRetrofitInstance() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create());
        return builder.build();
    }

    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(createRequestInterceptor());
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(createLoggingInterceptor());
        }
        return builder.build();
    }

    private HttpLoggingInterceptor createLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    private Interceptor createRequestInterceptor() {
        return chain -> {
            Request.Builder requestBuilder = chain.request().newBuilder();
            requestBuilder.url(
                    chain
                            .request()
                            .url()
                            .newBuilder()
                            .addQueryParameter("api_key", BuildConfig.API_KEY)
                            .addQueryParameter("language", Locale.getDefault().toLanguageTag())
                            .build()
            );
            return chain.proceed(requestBuilder.build());
        };
    }

    private Retrofit getRetrofit() {
        if (retrofitInstance == null) {
            retrofitInstance = createRetrofitInstance();
        }
        return retrofitInstance;
    }
}

package koneksi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitClient {

    public static final String BASE_URL = ;


    public static final String SUCCESSFUL_RESPONSE = "success";

    public static RetrofitEndPoint getInstance(){
        return getConnetion().create(RetrofitEndPoint.class);

    }

    private static URI getConnetion() {
        return null;
    }

    public static Retrofit getConnection(){
        logApp.info(RetrofitClient.class,logTag).RETROFIT_CONNECTION,"create connection to" + BASE_URL);
        Gson gson = new GsonBuilder().setLenient().create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .build();


        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(Gson))
                .client(client)
                .build();


    }
}

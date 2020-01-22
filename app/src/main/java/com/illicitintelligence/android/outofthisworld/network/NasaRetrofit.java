package com.illicitintelligence.android.outofthisworld.network;

import android.util.Log;

import com.illicitintelligence.android.outofthisworld.model.Collection;
import com.illicitintelligence.android.outofthisworld.model.Example;
import com.illicitintelligence.android.outofthisworld.util.Constants;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NasaRetrofit {

    ///////////create the service interface
    private NasaInterface nasaInterface;

    public NasaRetrofit(){
        nasaInterface = createInterface(createRetrofit());
    }

    private Retrofit createRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                //.client(createHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient createHttpClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String message) {
                Log.d("TAG_X", "log: "+message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return new OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

    private NasaInterface createInterface(Retrofit retrofit){
        return retrofit.create(NasaInterface.class);
    }



    //////searching for a string
    public Call<Example> search(String query, int pageNum){
        return nasaInterface.searchNasa(query, pageNum);
    }
}

package com.illicitintelligence.android.outofthisworld.network;

import com.illicitintelligence.android.outofthisworld.model.Collection;
import com.illicitintelligence.android.outofthisworld.model.Example;
import com.illicitintelligence.android.outofthisworld.util.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NasaInterface {


    @GET(Constants.PATH_URL)
    Call<Example> searchNasa(@Query("q") String query,
                             @Query("page") int pageNum);


}
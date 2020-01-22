package com.illicitintelligence.android.outofthisworld.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.illicitintelligence.android.outofthisworld.model.Collection;
import com.illicitintelligence.android.outofthisworld.model.Datum;
import com.illicitintelligence.android.outofthisworld.model.Example;
import com.illicitintelligence.android.outofthisworld.model.Item;
import com.illicitintelligence.android.outofthisworld.network.NasaRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NasaViewModel extends ViewModel {


    private final String TAG = "TAG_X";
    private MutableLiveData<List<Item>> liveData = new MutableLiveData<>();
    private NasaRetrofit retrofit = new NasaRetrofit();

    public void search(String query, final int pageNum) {
        Log.d(TAG, "search: " + query);
        Call<Example> collectionCall = retrofit.search(query, pageNum);
        collectionCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(@NonNull Call<Example> call, @NonNull Response<Example> response) {
                if (response.body().getCollection().getItems() == null) {
                    //todo tell that nothing was returned
                } else if (pageNum == 1) {
                    Log.d(TAG, "onResponse: " + pageNum);
                    liveData.setValue(response.body().getCollection().getItems());
                } else {
                    List<Item> data = liveData.getValue();
                    for (int i = 0; i < response.body().getCollection().getItems().get(0).getData().size(); i++) {
                        data.add(response.body().getCollection().getItems().get(i));
                    }
                    liveData.setValue(data);
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d("TAG_X", "onFailure: " + t.getMessage());
            }
        });
    }


    public MutableLiveData<List<Item>> getLiveData() {
        return liveData;
    }

}

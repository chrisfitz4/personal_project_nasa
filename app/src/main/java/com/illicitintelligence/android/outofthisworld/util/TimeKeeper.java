package com.illicitintelligence.android.outofthisworld.util;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class TimeKeeper extends IntentService {


    public TimeKeeper() {
        super("My Time Keeper");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Random random = new Random();
            int n = random.nextInt(30);
            n+=10;
            for(int i = 0; i<15; i++) {
                Thread.sleep(1000);
            }
            sendBroadcast(new Intent(Constants.START_ANIMATION));
        } catch (InterruptedException e) {
            Log.d("TAG_X", "onHandleIntent: "+e.getMessage());
        }
    }

}

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
            int n = random.nextInt(5);
            n+=10;
            for(int i = 0; i<n; i++) {
                Thread.sleep(2400);
                //sendBroadcast(new Intent(Constants.START_ANIMATION_THREE));
            }
            sendBroadcast(new Intent(Constants.START_ANIMATION));
        } catch (InterruptedException e) {
            Log.d("TAG_X", "onHandleIntent: "+e.getMessage());
        }
    }

}

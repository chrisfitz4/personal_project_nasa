package com.illicitintelligence.android.outofthisworld.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.illicitintelligence.android.outofthisworld.R;
import com.illicitintelligence.android.outofthisworld.adapter.RVAdapter;
import com.illicitintelligence.android.outofthisworld.model.Collection;
import com.illicitintelligence.android.outofthisworld.model.Datum;
import com.illicitintelligence.android.outofthisworld.model.Item;
import com.illicitintelligence.android.outofthisworld.util.Constants;
import com.illicitintelligence.android.outofthisworld.util.TimeKeeper;
import com.illicitintelligence.android.outofthisworld.viewmodel.NasaViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "TAG_X";
    NasaViewModel viewModel;
    PopupMenu menu;
    Observer<List<Item>> searchObserver;
    RVAdapter adapter;
    List<Item> items = null;
    AnimatedVectorDrawable animatedVectorDrawable;

    @BindView(R.id.menu_selector)
    ImageView imageView;
    @BindView(R.id.rv_results)
    RecyclerView recyclerView;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.no_results)
    TextView noResultsTV;
    @BindView(R.id.searched_text)
    TextView searchedText;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Constants.START_ANIMATION)){
                animatedVectorDrawable.start();
                startService(new Intent(getBaseContext(),TimeKeeper.class));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(NasaViewModel.class);

        setUpEverything();
    }


    private void setUpEverything(){
        setUpObserver();
        setUpMenu();

        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
    }

    private void setUpMenu(){
        //set up menu
        menu = new PopupMenu(this,imageView);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.search_selection,menu.getMenu());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().equals("")) {
                    menu.show();
                }else{
                    viewModel.search(editText.getText().toString(),1);
                    searchedText.setText(editText.getText());
                    editText.setText("");
                    editText.setVisibility(View.INVISIBLE);
                    hideKeyboard(MainActivity.this);
                }
            }
        });
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle().equals(getString(R.string.custom_search))){
                    editText.setVisibility(View.VISIBLE);
                }else if(Build.VERSION.SDK_INT>=26) {
                    Log.d(TAG, "onMenuItemClick: " + item.getContentDescription());
                    viewModel.search(item.getContentDescription().toString(),1);
                    searchedText.setText(item.getTitle());
                }else{
                    viewModel.search(item.getTitle().toString(),1);
                    searchedText.setText(item.getTitle());
                }
                return true;
            }
        });
        animatedVectorDrawable = (AnimatedVectorDrawable) imageView.getBackground();
        //animatedVectorDrawable.start();
        startService(new Intent(this,TimeKeeper.class));
        registerReceiver(broadcastReceiver, new IntentFilter(Constants.START_ANIMATION));
    }
////////close the keyboard after typing
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setUpObserver(){
        searchObserver = new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> data) {
                Log.d(TAG, "onChanged: "+data.toString());
                items = data;
                adapter = new RVAdapter(items,getBaseContext());
                recyclerView.setAdapter(adapter);
                if(data.size()==0){
                    noResultsTV.setVisibility(View.VISIBLE);
                }else{
                    noResultsTV.setVisibility(View.INVISIBLE);
                }
            }
        };
        viewModel.getLiveData().observe(this,searchObserver);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.getLiveData().removeObserver(searchObserver);
        unregisterReceiver(broadcastReceiver);
    }
}

package com.example.weathertest.mvp;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.weathertest.R;
import com.example.weathertest.mvp.Adapter.Cities;
import com.example.weathertest.mvp.Adapter.ClickListener;
import com.example.weathertest.mvp.Adapter.RvAdapter;
import com.example.weathertest.mvp.DB.MySQLiteHelper;
import com.example.weathertest.mvp.api.MessagesApi;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MainContract.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MainActivity";

    private MainContract.Presenter mPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private  MenuItem searchItem;
    private EditText et;
    private static RecyclerView recview;
    private static RvAdapter adapter;
    private static List<Cities> states = new ArrayList();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recview = findViewById(R.id.resview);
        resolveDependencies();
        mSwipeRefreshLayout =  findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        Log.d(TAG, "onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
        mPresenter.subscribe();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
        mPresenter.unsubscribe(isFinishing());
    }

    private void setInitialData() {

         adapter = new RvAdapter(states, new ClickListener() {
            @Override public void onPositionClicked(int position) {
                // callback performed on click
            }

            @Override public void onLongClicked(int position) {
                // callback performed on click
            }


        });
        recview.setAdapter(adapter);

    }

    @Override
    public void onError(@NonNull Throwable throwable) {

        MySQLiteHelper db = new MySQLiteHelper(getApplicationContext());

        Log.d(TAG, "db.getAll_1");
        states.clear();
        int count = db.getAllCount();
        if (count!=0) {
        for (int i = 0; i < count; i++) {

            Cities citi = db.getCities(i);

            states.add(new Cities(citi.getCity(), citi.getLogo(),citi.getTemp(), citi.getTemp2(), citi.getDesc(),  citi.getPressure(), citi.getHumidity(), citi.getWind(), i));
        }
        setInitialData();
        }
    }

    @Override
    public void showText(@NonNull List<com.example.weathertest.mvp.pojo.List> cities) {
        states.clear();



                    for (int i = 0; i < cities.size(); i++) {
                        Log.d(TAG, "db.getAll_6"+cities.get(i).getMain().getTemp());
                        states.add(new Cities(cities.get(i).getName(), cities.get(i).getWeather().get(0).getIcon(),cities.get(i).getMain().getTemp(),  cities.get(i).getMain().getFeelsLike(), cities.get(i).getWeather().get(0).getDescription(),  cities.get(i).getMain().getPressure(), cities.get(i).getMain().getHumidity(), cities.get(i).getWind().getSpeed(),i));
                    }



        mPresenter.onButtonWasClicked2(cities,MainActivity.this);
        Log.d(TAG, "db.getAll_3");


        setInitialData();
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onButtonWasClicked();
        Log.d(TAG, "onResume()");

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");

    }
    private void resolveDependencies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MessagesApi messagesApi = retrofit.create(MessagesApi.class);
        mPresenter = new MainPresenter(this, new MainRepository(messagesApi));
    }

    @Override
    public void onRefresh() {
        mPresenter.onButtonWasClicked();
    }
    @Override
    public void loadProgres(){
        mSwipeRefreshLayout.setRefreshing(true);
    }
    @Override
    public void cancelProgres(){
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void setInitialData3(String name) {

        states.clear();
        MySQLiteHelper db = new MySQLiteHelper(getApplicationContext());
        int count = db.getAllCount();
        if (count!=0) {

        for (int i = 0; i < count; i++) {
            Cities citi = db.getCities(i);
            if (Pattern.compile(Pattern.quote(name), Pattern.CASE_INSENSITIVE).matcher(citi.getCity()).find()) {

                states.add(new Cities(citi.getCity(), citi.getLogo(),citi.getTemp(), citi.getTemp2(), citi.getDesc(),  citi.getPressure(), citi.getHumidity(), citi.getWind(), i));
            }
        }
        }
        setInitialData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if(null!=searchManager ) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setIconifiedByDefault(false);

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {

                et.setText("");
                onResume();
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                  Log.d(TAG, "search_query " + query);

                setInitialData3(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                 Log.d(TAG, "search_query "+newText);
                setInitialData3(newText);
                return false;
            }

        });


        int searchCloseButtonId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_close_btn", null, null);
        int searchEdTextId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_src_text", null, null);

        ImageView closeButton =  searchView.findViewById(searchCloseButtonId);

        et =  searchView.findViewById(searchEdTextId);

        et.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {

                 et.setText("");

                setInitialData();

            }
        });

        closeButton.setOnClickListener(v -> {

            searchItem.collapseActionView();
            et.setText("");
            onResume();
        });


        return true;
    }
}
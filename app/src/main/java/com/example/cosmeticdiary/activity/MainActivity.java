package com.example.cosmeticdiary.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.adapter.WritingListAdapter;
import com.example.cosmeticdiary.dialog.DialogCheckLogout;
import com.example.cosmeticdiary.model.LoginModel;
import com.example.cosmeticdiary.model.ProfileModel;
import com.example.cosmeticdiary.model.SearchResultModel;
import com.example.cosmeticdiary.model.SearchWritingModel;
import com.example.cosmeticdiary.util.GpsTracker;
import com.example.cosmeticdiary.util.MySharedPreferences;
import com.example.cosmeticdiary.util.retrofit.RetrofitHelper;
import com.example.cosmeticdiary.util.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    WritingListAdapter writingListAdapter;
    List<SearchWritingModel> dataInfo;
    SearchResultModel dataList;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    DialogCheckLogout dialogCheckLogout;
    CalendarView calendarView;
    ProfileModel profileModel;
    ActionBarDrawerToggle alarmToggle;
    TextView tv_date, tv_empty, tv_maxtemp, tv_mintemp, tv_humadity, tv_dust;
    int pressedTime = 0;
    String selectDate, deafaultDate;
    double latitude, longitude;
    String slatitude, slongitude;
    // header??? ?????? ????????? ????????????
    NavigationView navigationView;
    View header;

    //Menu
    MenuItem menuItem;
    Switch drawerSwitch;
//    int alarm;

    RetrofitService retrofitService;
    //?????? ??????
    double humidity = 0, maxTemp = 0, minTemp = 0;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        navigationView.setCheckedItem(R.id.menu_alarm);
        navigationView.getMenu().performIdentifierAction(R.id.menu_alarm, 0);
        if (alarmToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectDate = (Calendar.getInstance().get(Calendar.YEAR)) + "-"
                + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "-"
                + (Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        deafaultDate = (Calendar.getInstance().get(Calendar.MONTH) + 1) + "??? "
                + (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "???");
        navigationView = findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);

        FloatingActionButton fabPlus = findViewById(R.id.fab_plus);
        FloatingActionButton fabSearch = findViewById(R.id.fab_search);
        calendarView = findViewById(R.id.calendarView);
        tv_date = findViewById(R.id.tv_date);
        tv_maxtemp = header.findViewById(R.id.tv_maxtemp);
        tv_mintemp = header.findViewById(R.id.tv_mintemp);
        tv_humadity = header.findViewById(R.id.tv_humidity);
        tv_dust = header.findViewById(R.id.tv_dust);

        Button btneditprofile = header.findViewById(R.id.btn_editprofile);

        recyclerView = findViewById(R.id.rv_main);
        tv_empty = findViewById(R.id.tv_empty);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        menuItem = navigationView.getMenu().findItem(R.id.menu_alarm);
        drawerSwitch = (Switch) menuItem.getActionView().findViewById(R.id.drawer_switch);

        GpsTracker gpsTracker = new GpsTracker(MainActivity.this);
        latitude = gpsTracker.getLatitude(); // ??????
        longitude = gpsTracker.getLongitude(); //??????
        slatitude = Double.toString(latitude);
        slongitude = Double.toString(longitude);
        Log.d("??????,??????", slatitude + "  " + slongitude);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    if (checkLocationServicesStatus()) {
                        checkRunTimePermission();
                    } else {
                        showDialogForLocationServiceSetting();
                    }

                    retrofitService = RetrofitHelper.getWeatherRetrofit().create(RetrofitService.class);
                    Call<JsonObject> call = retrofitService.getWeather(slatitude, slongitude, "54b1ec64882548adfec17e7ea7afca02");

                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.body() != null) {
                                String jsonObj = response.body().toString();
                                try {
                                    JSONObject jsonObject = new JSONObject(jsonObj);
                                    maxTemp = Math.round((Double.parseDouble(jsonObject.getJSONObject("main").getString("temp_max")) - 273.15) * 10) / 10.0;
                                    minTemp = Math.round((Double.parseDouble(jsonObject.getJSONObject("main").getString("temp_min")) - 273.15) * 10) / 10.0;
                                    humidity = Double.parseDouble(jsonObject.getJSONObject("main").getString("humidity"));

                                    Log.d("????????????", jsonObj);
                                    Log.d("????????????", "??????" + maxTemp + "??? ?????? " + minTemp + "??? ??????  " + humidity + "%");

                                    tv_maxtemp.setText(maxTemp + "??C");
                                    tv_mintemp.setText(minTemp + "??C");
                                    tv_humadity.setText(humidity + "%");

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            } else {
                                Log.d("????????????", "??????" + maxTemp);
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.d("ssss", t.getMessage());
                        }
                    });
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }
        }.execute();

        // user?????? ????????????
        searchProfile();

        this.InitializeLayout();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switchScreen(item.getItemId());

                DrawerLayout drawer = findViewById(R.id.drawerLayout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        btneditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                intent.putExtra("name", profileModel.getName());
                if (!TextUtils.isEmpty(profileModel.getGender())) {
                    intent.putExtra("gender", profileModel.getGender());
                }
                if (!TextUtils.isEmpty(profileModel.getAge())) {
                    intent.putExtra("age", profileModel.getAge());
                }
//                intent.putExtra("image", profileModel.getImage());
                if (!TextUtils.isEmpty(profileModel.getSkintype())) {
                    intent.putExtra("skintype", profileModel.getSkintype());
                }
                if (!TextUtils.isEmpty(profileModel.getAllergy())) {
                    intent.putExtra("allergy", profileModel.getAllergy());
                }
                ;
//                setResult(RESULT_OK, intent);
                startActivity(intent);

                finish();
            }
        });

        fabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, WritingActivity.class);
                intent.putExtra("writing", 1000);
                intent.putExtra("dateMain", selectDate);
//                Log.d("??????",selectDate);
                startActivity(intent);
            }
        });
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchWritingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // user?????? ????????????
        searchProfile();

        //????????? ?????? ??? ?????? ?????????
        tv_date.setText(deafaultDate);

        searchCalender(selectDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                tv_date.setText(String.format("%d??? %d???", month + 1, dayOfMonth));
                deafaultDate = String.format("%d??? %d???", month + 1, dayOfMonth);

                selectDate = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                //????????????(????????? ?????? ????????? ????????????
                searchCalender(selectDate);
            }
        });
    }

    private void searchCalender(String date) {
        retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);

        Call<SearchResultModel> call = retrofitService.getSearchCalender(
                MySharedPreferences.getUserId(MainActivity.this), date);

        call.enqueue(new Callback<SearchResultModel>() {
            @Override
            public void onResponse(Call<SearchResultModel> call, Response<SearchResultModel> response) {
                Log.d("?????? ??????", response.message());
                dataList = response.body();

                if (response.body() != null) {
                    dataInfo = dataList.calender_results;
                    if (response.body().getCode().equals("200")) {
                        writingListAdapter = new WritingListAdapter(getApplicationContext(), dataInfo);
                        recyclerView.setAdapter(writingListAdapter);
                        tv_empty.setVisibility(View.GONE);
                    } else {
                        writingListAdapter = new WritingListAdapter(getApplicationContext(), dataInfo);
                        recyclerView.setAdapter(writingListAdapter);
                        tv_empty.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchResultModel> call, Throwable t) {
                Log.d("ssss", t.getMessage());
            }
        });
    }

    //user?????? ????????????
    private void searchProfile() {
        retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);

        Call<ProfileModel> call = retrofitService.getSearchProfile(MySharedPreferences.getUserId(this).toString());

        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        Log.d("?????? ??????", response.message());
                        profileModel = response.body();

                        List<ProfileModel> profileList;

                        if (profileModel.getAlarm() == 1) {
                            drawerSwitch.setChecked(true);
                        } else drawerSwitch.setChecked(false);

                        TextView tv_profilename = header.findViewById(R.id.tv_profilename);
                        TextView tv_skintype = header.findViewById(R.id.tv_skintype);
                        TextView tv_allergy = header.findViewById(R.id.tv_allergy);

                        tv_profilename.setText(profileModel.getName());
                        tv_skintype.setText(profileModel.getSkintype());
                        tv_allergy.setText(profileModel.getAllergy());
                    } else if (response.code() == 404) {
                        Toast.makeText(MainActivity.this, "????????? ????????? ??????????????????", Toast.LENGTH_SHORT).show();
                        Log.d("ssss", response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Log.d("ssss", t.getMessage());
            }
        });
    }

    // ?????? ????????? ????????? ????????????
    private void setAlarm(int onOff) {
        retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);

        Call<LoginModel> call = retrofitService.SetAlarm(MySharedPreferences.getUserId(MainActivity.this), onOff);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    Log.d("?????? ??????", response.message());
                    LoginModel loginModel = response.body();

                    if (loginModel.getCode().equals("200")) {
                        Log.v("code", loginModel.getCode());
                    } else
                        Log.d("ssss", response.message());
                    finish();
                } else if (response.code() == 404) {
                    Toast.makeText(MainActivity.this, "????????? ????????? ??????????????????", Toast.LENGTH_SHORT).show();
                    Log.d("ssss", response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d("ssss", t.getMessage());
            }

        });
    }

    // ?????? ?????? ?????????
    private void switchScreen(int id) {
        switch (id) {
            case R.id.menu_alarm:
                drawerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            setAlarm(1);
                            Toast.makeText(MainActivity.this, "??????????????? ????????????", Toast.LENGTH_SHORT).show();

                        } else {
                            setAlarm(0);
                            Toast.makeText(MainActivity.this, "??????????????? ????????????", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            case R.id.menu_logout:
                dialogCheckLogout = new DialogCheckLogout(MainActivity.this, dialogListener);
                dialogCheckLogout.show();
                break;
        }
    }

    public void InitializeLayout() {
        //toolBar??? ?????? App Bar ??????
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //App Bar??? ?????? ?????? Drawer??? Open ?????? ?????? Icon ??????
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Cosmetic Diary");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);

        DrawerLayout drawLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        alarmToggle = new ActionBarDrawerToggle(this, drawLayout,
                toolbar, R.string.open, R.string.closed);

        drawLayout.addDrawerListener(alarmToggle);
    }

    //??????????????????
    private View.OnClickListener dialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_ok:
                    // ???????????? ??????
                    MySharedPreferences.clearUser(getApplicationContext());

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                case R.id.tv_cancel:
                    dialogCheckLogout.dismiss();
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (pressedTime == 0) {
            Toast.makeText(MainActivity.this, " ??? ??? ??? ????????? ???????????????.", Toast.LENGTH_LONG).show();
            pressedTime = (int) System.currentTimeMillis();
        } else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if (seconds > 2000) {
                Toast.makeText(MainActivity.this, " ??? ??? ??? ????????? ???????????????.", Toast.LENGTH_LONG).show();
                pressedTime = 0;
            } else {
                super.onBackPressed();
//                finish(); // app ?????? ?????????
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // ?????? ????????? PERMISSIONS_REQUEST_CODE ??????, ????????? ????????? ???????????? ??????????????????

            boolean check_result = true;

            // ?????? ???????????? ??????????????? ???????????????.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {
                //?????? ?????? ????????? ??? ??????
            } else {
                // ????????? ???????????? ????????? ?????? ????????? ??? ?????? ????????? ??????????????? ?????? ???????????????.2 ?????? ????????? ????????????.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(MainActivity.this, "???????????? ?????????????????????. ?????? ?????? ???????????? ???????????? ??????????????????.", Toast.LENGTH_LONG).show();
                    finish();


                } else {
                    Toast.makeText(MainActivity.this, "???????????? ?????????????????????. ??????(??? ??????)?????? ???????????? ???????????? ?????????. ", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    void checkRunTimePermission() {
        //????????? ????????? ??????
        // 1. ?????? ???????????? ????????? ????????? ???????????????.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. ?????? ???????????? ????????? ?????????
            // ( ??????????????? 6.0 ?????? ????????? ????????? ???????????? ???????????? ????????? ?????? ????????? ?????? ???????????????.)


            // 3.  ?????? ?????? ????????? ??? ??????


        } else {  //2. ????????? ????????? ????????? ?????? ????????? ????????? ????????? ???????????????. 2?????? ??????(3-1, 4-1)??? ????????????.

            // 3-1. ???????????? ????????? ????????? ??? ?????? ?????? ????????????
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. ????????? ???????????? ?????? ?????????????????? ???????????? ????????? ????????? ???????????? ????????? ????????????.
                Toast.makeText(MainActivity.this, "??? ?????? ??????????????? ?????? ?????? ????????? ???????????????.", Toast.LENGTH_LONG).show();
                // 3-3. ??????????????? ????????? ????????? ?????????. ?????? ????????? onRequestPermissionResult?????? ???????????????.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. ???????????? ????????? ????????? ??? ?????? ?????? ???????????? ????????? ????????? ?????? ?????????.
                // ?????? ????????? onRequestPermissionResult?????? ???????????????.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }

    //GPS ???????????? ?????? ????????????
    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("?????? ????????? ????????????");
        builder.setMessage("?????? ???????????? ???????????? ?????? ???????????? ???????????????.\n" + "?????? ????????? ???????????????????");
        builder.setCancelable(true);
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                //???????????? GPS ?????? ???????????? ??????
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS ????????? ?????????");
//                        checkRunTimePermission();
                        return;
                    }
                }
                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}


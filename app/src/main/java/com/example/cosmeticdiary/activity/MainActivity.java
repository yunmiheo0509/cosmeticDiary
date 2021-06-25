package com.example.cosmeticdiary.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
    //    WritingListAdapter.RecyclerViewClickListener listener;
    ProfileModel profileModel;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView tv_date, tv_empty;
    int pressedTime = 0;
    String selectDate, deafaultDate;


    // header에 있는 리소스 가져오기
    NavigationView navigationView;
    View header;

    RetrofitService retrofitService;
//날씨 정보
    double humidity=0,maxTemp=0,minTemp=0;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        navigationView.setCheckedItem(R.id.menu_alarm);
        navigationView.getMenu().performIdentifierAction(R.id.menu_alarm, 0);
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
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
        deafaultDate = (Calendar.getInstance().get(Calendar.MONTH) + 1) + "월 "
                + (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "일");
        navigationView = findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);

        FloatingActionButton fabPlus = findViewById(R.id.fab_plus);
        FloatingActionButton fabSearch = findViewById(R.id.fab_search);
        calendarView = findViewById(R.id.calendarView);
        tv_date = findViewById(R.id.tv_date);

        Button btneditprofile = header.findViewById(R.id.btn_editprofile);

        recyclerView = findViewById(R.id.rv_main);
        tv_empty = findViewById(R.id.tv_empty);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        getWeather();
//        dataInfo = new ArrayList<>();

//        recyclerView.setAdapter(writingListAdapter);

        //user정보 서버검색
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
                };
//                setResult(RESULT_OK, intent);
                startActivity(intent);
//                Log.d("반환", recyclerAdapter.choice().get(0) +" "+ recyclerAdapter.choice().get(1));
                finish();
            }
        });

        fabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, WritingActivity.class);
                intent.putExtra("writing", 1000);
                intent.putExtra("dateMain", selectDate);
//                Log.d("날짜",selectDate);
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

        //user정보 서버검색
        searchProfile();

//        날짜에 맞는 글 목록 띄우기
        tv_date.setText(deafaultDate);

        searchCalender(selectDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                tv_date.setText(String.format("%d월 %d일", month + 1, dayOfMonth));
                deafaultDate = String.format("%d월 %d일", month + 1, dayOfMonth);

                selectDate = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                //서버연결(날짜에 맞는 데이터 가져오기
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
                Log.d("연결 성공", response.message());
//                SearchResultModel searchResultModel = response.body();
//                Log.d("검색", searchResultModel.calender_results.get(0).getName());
                dataList = response.body();
//                Log.d("검색 ", dataList.toString());
                if (response.body() != null) {
                    Log.d("로로로그그그",response.body().code);
                    if (response.body().getCode().equals("200")) {
                        dataInfo = dataList.calender_results;
                        writingListAdapter = new WritingListAdapter(getApplicationContext(), dataInfo);
                        recyclerView.setAdapter(writingListAdapter);
                        tv_empty.setVisibility(View.GONE);
//                    Log.d("받아온거  확인", dataInfo.toString());
                    } else {
                        dataInfo.clear();
                        writingListAdapter = new WritingListAdapter(getApplicationContext(), dataInfo);
                        recyclerView.setAdapter(writingListAdapter);
                        tv_empty.setVisibility(View.VISIBLE);
//                    Log.d("받아온거 없는경우다", dataInfo.toString());
                    }
                }

            }

            @Override
            public void onFailure(Call<SearchResultModel> call, Throwable t) {
                Log.d("ssss", t.getMessage());
            }
        });
    }

    //user정보 가져오기
    private void searchProfile() {
        retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);

        Call<ProfileModel> call = retrofitService.getSearchProfile(MySharedPreferences.getUserId(this).toString());

        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        Log.d("연결 성공", response.message());
                        profileModel = response.body();
//                        dataList = response.body();
//                        dataInfo = dataList.profile_results;
//                            recyclerAdapter = new SearchCosmeticRecyclerAdapter(getApplicationContext(), dataInfo);
//                            recyclerView.setAdapter(recyclerAdapter);

                        List<ProfileModel> profileList;

                        TextView tv_profilename = header.findViewById(R.id.tv_profilename);
                        TextView tv_skintype = header.findViewById(R.id.tv_skintype);
                        TextView tv_allergy = header.findViewById(R.id.tv_allergy);

                        tv_profilename.setText(profileModel.getName());
                        tv_skintype.setText(profileModel.getSkintype());
                        tv_allergy.setText(profileModel.getAllergy());
                    } else if (response.code() == 404) {
                        Toast.makeText(MainActivity.this, "인터넷 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
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


    // 메뉴 항목 이벤트
    private void switchScreen(int id) {
        switch (id) {
            case R.id.menu_alarm:
                MenuItem menuItem = navigationView.getMenu().findItem(R.id.menu_alarm); // This is the menu item that contains your switch
                Switch drawerSwitch = (Switch) menuItem.getActionView().findViewById(R.id.drawer_switch);

                drawerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Toast.makeText(MainActivity.this, "Switch turned on", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Switch turned off", Toast.LENGTH_SHORT).show();
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
        //toolBar를 통해 App Bar 생성
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //App Bar의 좌측 영에 Drawer를 Open 하기 위한 Icon 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Cosmetic Diary");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);

        DrawerLayout drawLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawLayout,
                toolbar, R.string.open, R.string.closed);

        drawLayout.addDrawerListener(actionBarDrawerToggle);
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = findViewById(R.id.drawerLayout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    //다이얼로그창
    private View.OnClickListener dialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_ok:
                    // 로그아웃 진행
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
            Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
            pressedTime = (int) System.currentTimeMillis();
        } else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if (seconds > 2000) {
                Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
                pressedTime = 0;
            } else {
                super.onBackPressed();
//                finish(); // app 종료 시키기
            }
        }
    }

    private void getWeather() {
        if (checkLocationServicesStatus()) {
            checkRunTimePermission();
        } else {
            showDialogForLocationServiceSetting();
        }
        GpsTracker gpsTracker = new GpsTracker(MainActivity.this);
        double latitude = gpsTracker.getLatitude(); // 위도
        double longitude = gpsTracker.getLongitude(); //경도
        String slatitude = Double.toString(latitude);
        String slongitude = Double.toString(longitude);
        Log.d("경도,위도", slatitude + "  " + slongitude);

        retrofitService = RetrofitHelper.getWeatherRetrofit().create(RetrofitService.class);
        Call<JsonObject> call = retrofitService.getWeather(slatitude,slongitude,"54b1ec64882548adfec17e7ea7afca02");

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().toString()!=null) {
                   String jsonObj = response.body().toString();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonObj);
                        maxTemp = Double.parseDouble(jsonObject.getJSONObject("main").getString("temp_max"))-273.15;
                        minTemp = Double.parseDouble(jsonObject.getJSONObject("main").getString("temp_min"))-273.15;
                        humidity = Double.parseDouble(jsonObject.getJSONObject("main").getString("humidity"));

                        Log.d("날씨정보", jsonObj);
                        Log.d("날씨파싱","최고"+ maxTemp+"도 최저 "+minTemp+"도 습도  "+humidity+"%");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("ssss", t.getMessage());
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                //위치 값을 가져올 수 있음
                ;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                } else {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }

    //여기부터는 GPS 활성화를 위한 메소드들

    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
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
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
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


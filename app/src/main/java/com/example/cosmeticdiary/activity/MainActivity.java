package com.example.cosmeticdiary.activity;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticdiary.DialogCheckIdPw;
import com.example.cosmeticdiary.DialogCheckLogout;
import com.example.cosmeticdiary.MySharedPreferences;
import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.WritingListData;
import com.example.cosmeticdiary.adapter.SearchCosmeticRecyclerAdapter;
import com.example.cosmeticdiary.adapter.WritingListAdapter;
import com.example.cosmeticdiary.model.LoginModel;
import com.example.cosmeticdiary.model.ProfileModel;
import com.example.cosmeticdiary.model.SearchCosmeticModel;
import com.example.cosmeticdiary.model.SearchResultModel;
import com.example.cosmeticdiary.retrofit.RetrofitHelper;
import com.example.cosmeticdiary.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ArrayList<WritingListData> writingListArray;
    private WritingListAdapter writingListAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DialogCheckLogout dialogCheckLogout;
    WritingListAdapter.RecyclerViewClickListener listener;
    ProfileModel profileModel;

    ActionBarDrawerToggle actionBarDrawerToggle;

    NavigationView navigationView;
    View header;

    RetrofitService retrofitService;

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

        FloatingActionButton fabPlus = findViewById(R.id.fab_plus);
        FloatingActionButton fabSearch = findViewById(R.id.fab_search);
        CalendarView calendarView = findViewById(R.id.calendarView);
        final TextView tv_date = findViewById(R.id.tv_date);

        // header에 있는 리소스 가져오기
        navigationView = findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);

        Button btneditprofile = header.findViewById(R.id.btn_editprofile);

        setOnclickListener();

        //user정보 서버검색
        searchProfile();

        tv_date.setText((Calendar.getInstance().get(Calendar.MONTH) + 1) + "월 "
                + (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "일"));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                tv_date.setText(String.format("%d월 %d일", month + 1, dayOfMonth));

                //서버연결(날짜에 맞는 데이터 가져오기
            }
        });

        recyclerView = findViewById(R.id.rv_main);
        TextView tv_empty = findViewById(R.id.tv_empty);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        writingListArray = new ArrayList<>();

        writingListAdapter = new WritingListAdapter(writingListArray, listener);
        recyclerView.setAdapter(writingListAdapter);

        if (writingListArray.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tv_empty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tv_empty.setVisibility(View.GONE);
        }

//        writingListArray.add(new WritingListData(R.drawable.ic_launcher_background, "name",
//                "condition", "satisfy"));

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
//                intent.putExtra("ingredient", recyclerAdapter.choice().get(1));
                intent.putExtra("allergy", profileModel.getAllergy());
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
                intent.putExtra("main", "plus");
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

                        System.out.println(profileModel.getName() + " / " + profileModel.getSkintype() + " / " + profileModel.getAllergy());
//                    List<ProfileModel> profileList; 이렇게 저장해야되나?

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

    private void setOnclickListener() {
        listener = new WritingListAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), WritingActivity.class);
                intent.putExtra("main", "edit");
                startActivity(intent);
            }
        };
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

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
}
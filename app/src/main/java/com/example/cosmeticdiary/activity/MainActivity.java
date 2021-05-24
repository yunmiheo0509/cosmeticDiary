package com.example.cosmeticdiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticdiary.DialogCheckLogout;
import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.WritingListData;
import com.example.cosmeticdiary.adapter.WritingListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<WritingListData> writingListArray;
    private WritingListAdapter writingListAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DialogCheckLogout dialogCheckLogout;

    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    WritingListAdapter.RecyclerViewClickListener listener;

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

        setOnclickListener();

        recyclerView = findViewById(R.id.rv_main);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        writingListArray = new ArrayList<>();

        writingListAdapter = new WritingListAdapter(writingListArray,listener);
        recyclerView.setAdapter(writingListAdapter);

        writingListArray.add(new WritingListData(R.drawable.ic_launcher_background, "name",
                "condition", "satisfy"));

        navigationView = findViewById(R.id.nav_view);
        // xml 파일에서 넣어놨던 header 선언
        View header = navigationView.getHeaderView(0);
        // header에 있는 리소스 가져오기
        Button btneditprofile = header.findViewById(R.id.btn_editprofile);

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
                startActivity(intent);
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
        fabSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchWritingActivity.class);
                startActivity(intent);
            }
        });
    }


    private void setOnclickListener() {
        listener = new WritingListAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), WritingActivity.class);
                intent.putExtra("main", writingListArray.get(position).getName());
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
                case R.id.tv_ok :
                    // 로그아웃 진행
                    Toast.makeText(MainActivity.this, "로그아웃", Toast.LENGTH_SHORT).show();
                case R.id.tv_cancel :
                    dialogCheckLogout.dismiss();
            }
        }
    };
}

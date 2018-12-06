package com.example.gavin.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.mob.MobSDK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class MainActivity extends AppCompatActivity implements OnItemClick {
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AppCompatButton button;
    private AppCompatTextView testTxt;
    private AppCompatEditText testEdt;
    private RecyclerViewAdapter adapter;
    private List<String> data = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.test1:
                Toast.makeText(this, "test1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.test2:
                Toast.makeText(this, "test2", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.test3:
                Toast.makeText(this, "test3", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.test4:
                Toast.makeText(this, "test4", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.test5:
                Toast.makeText(this, "test5", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.test6:
                Toast.makeText(this, "test6", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.test7:
                Toast.makeText(this, "test7", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.test8:
                Toast.makeText(this, "test8", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.test9:
                Toast.makeText(this, "test9", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewLayout();

        MobSDK.init(this);

        for (int i = 0; i < 100; i++) {
            data.add("item" + i);
        }

        adapter = new RecyclerViewAdapter(this);

        toolbar.setTitle("Toolbar Test");
        toolbar.setSubtitle("just test");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null && v.isPressed()) {
                    onBackPressed();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode(getApplicationContext());
//                Intent intent = new Intent(getBaseContext(), SubscriberTestActivity.class);
//                startActivity(intent);
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setItems(data);
    }

    private void initViewLayout() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        button = (AppCompatButton) findViewById(R.id.button);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        testTxt = (AppCompatTextView) findViewById(R.id.text_view);
        testEdt = (AppCompatEditText) findViewById(R.id.edit_text);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        button.setText("Action Down");
                        break;
                    case MotionEvent.ACTION_UP:
                        button.setText("Action Up");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        button.setText("Action Move");
                        break;
                }
                return false;
            }
        });
    }

    public void sendCode(final Context context) {
        RegisterPage page = new RegisterPage();
        page.setTempCode(null);
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");
                    Toast.makeText(context, "COUNTRY:" + country + "PHONE:" + phone, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "你弄啥嘞，搞错啦！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        page.show(context);
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "再次点击返回键退出", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

    @Override
    public void onItemClick(int position) {
        button.setText(adapter.getItem(position));
//        adapter.deleteItem(position);
    }
}

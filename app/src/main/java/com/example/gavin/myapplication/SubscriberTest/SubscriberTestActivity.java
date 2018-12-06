package com.example.gavin.myapplication.SubscriberTest;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.example.gavin.myapplication.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Gavin
 */

public class SubscriberTestActivity extends AppCompatActivity {
    private AppCompatButton subscriberTestBtn;
    private AppCompatButton handlerTestBtn;
    private TextInputEditText editText;
    private int qty = 1;
    private int sty = 1;
    private boolean isBtnClick;
    private Handler handler = new Handler();
    private Runnable testRunnable = new Runnable() {
        @Override
        public void run() {
            handlerTestBtn.setText("item" + qty);
            qty += 1;
            if (isBtnClick) {
                try {
                    Thread.sleep(100);
                    handler.post(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Subscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber_test);
        initViewLayout();
    }

    @Override
    protected void onDestroy() {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    private void initViewLayout() {
        subscriberTestBtn = (AppCompatButton) findViewById(R.id.subscriber_test_btn);
        handlerTestBtn = (AppCompatButton) findViewById(R.id.handler_test_btn);
        editText = (TextInputEditText) findViewById(R.id.edt_test);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (event.getAction()) {
                    case KeyEvent.ACTION_UP:
                        editText.setText("1");
                        break;
                    case KeyEvent.ACTION_DOWN:
                        editText.setText("2");
                        break;
                    case KeyEvent.ACTION_MULTIPLE:
                        editText.setText("3");
                        break;
                    default:
                        editText.setText("4");
                        break;
                }
                return true;
            }
        });
        handlerTestBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isBtnClick = true;
                        handler.post(testRunnable);
                        break;
                    case MotionEvent.ACTION_UP:
                        isBtnClick = false;
                        handler.removeCallbacks(testRunnable);
                        break;
                }
                return true;
            }
        });

        subscription = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        subscriberTestBtn.setText(String.valueOf(sty));
                        sty += 1;
                    }
                });
    }
}

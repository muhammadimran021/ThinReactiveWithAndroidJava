package com.example.muhammadimran.rxjavapractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    TextView names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        names = (TextView) findViewById(R.id.name);
        Observable.from(new Future<String>() {
            @Override
            public boolean cancel(boolean b) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public String get() throws InterruptedException, ExecutionException {

                Log.d("TAG", "Start thread");
                String name = "imran khan";
                Thread.sleep(10000);
                Log.d("TAG", "End Thread");
                return name;
            }

            @Override
            public String get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
                Log.d("TAG", "Total time; " + timeUnit);
                return "start time and End Time";
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("TAG", "Complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "OnError");
                    }

                    @Override
                    public void onNext(String s) {
                        names.setText(s);
                        Log.d("TAG", "Name: " + s);
                    }
                })
        ;
    }
}

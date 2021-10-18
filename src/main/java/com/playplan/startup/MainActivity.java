package com.playplan.startup;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.playplan.myapplication.MessageEvent;
import com.playplan.test.Test;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/***
 * 初步构思
 * 1。用apt生成一个类X（apt作用只能在当前modules）
 * 2。用字节码插桩，插入要初始化的方法 例如  A.init();
 * 3。用字节码插桩将X在application中初始化
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Test.test();
//        EventBus eventBus = EventBus.builder()
//                .addIndex(new MyEventBusIndex())
//                .addIndex(new MyEventBusIndex2())
//                .build();


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void bvbv(MessageEvent event) {

    }

    ;
}
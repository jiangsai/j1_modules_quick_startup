package com.playplan.test;

import android.util.Log;

import com.playplan.fastmodules.annotation.FastMoudle;
import com.playplan.fastmodules.module.IFastModule;
import com.playplan.myapplication.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author : jyt
 * time   : 2021/10/17
 * desc   :
 */
@FastMoudle
public class Test implements IFastModule {
    public static void test() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void haha(MessageEvent event) {/*
     Do something */
    }

    ;

    @Override
    public void intitFastNodeList() {
        Log.e("jyt", "test a is load");
    }
}

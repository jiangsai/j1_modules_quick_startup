package com.playplan.myapplication;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author : jyt
 * time   : 2021/10/17
 * desc   :
 */
public class BaseTest {


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {/*
     Do something */
    }

    ;


}

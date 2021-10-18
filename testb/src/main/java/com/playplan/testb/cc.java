package com.playplan.testb;

import com.playplan.myapplication.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author : jyt
 * time   : 2021/10/17
 * desc   :
 */
public class cc {
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void aaff(MessageEvent event) {/*
     Do something */
    }

    ;
}

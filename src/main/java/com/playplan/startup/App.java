package com.playplan.startup;

import android.app.Application;

import com.playplan.fastmodules.annotation.FastMoudleTarget;

/**
 * author : jyt
 * time   : 2021/10/18
 * desc   :
 */
@FastMoudleTarget
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

    }
}

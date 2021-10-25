package com.playplan.startup;

import android.app.Application;


import com.playplan.fastmodules.TestModuleProxyCreator;
import com.playplan.fastmodules.module.ModuleLoader;
import com.playplan.myapplication.SystemUtil;
/**
 * author : jyt
 * time   : 2021/10/18
 * desc   :
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
       
        ModuleLoader.initMoudle(SystemUtil.getCurProcessName(), SystemUtil.isProcess(BuildConfig.APPLICATION_ID));
    }
}

package com.playplan.testb;

import android.util.Log;

import com.playplan.fastmodules.module.IFastModule;
import com.playplan.fastmodules.node.FastNode;

/**
 * author : jyt
 * time   : 2021/10/19
 * desc   :
 */
public class app2 implements IFastModule {
    @Override
    public void intitFastNodeList() {
        Log.e("jyt", "test app2 is load");
        new FastNode(() -> {
            todo1();
        }, 5, false);

        new FastNode(() -> {
            todo2();
        }, 10, false);
    }

    private void todo2() {
        long currentTimeMillis = System.currentTimeMillis();
        Log.e("jyt-strart", "BMoudle2=" + currentTimeMillis + "====" + Thread.currentThread());
        long useTime = currentTimeMillis + (1000 * 1);
        while (currentTimeMillis < useTime) {
            currentTimeMillis = System.currentTimeMillis();
        }
        Log.e("jyt-End", "BMoudle2=" + currentTimeMillis + "====" + Thread.currentThread());
    }


    public void todo1() {
        long currentTimeMillis = System.currentTimeMillis();
        Log.e("jyt-strart", "BMoudle" + currentTimeMillis + "====" + Thread.currentThread());
        long useTime = currentTimeMillis + (1000 * 1);
        while (currentTimeMillis < useTime) {
            currentTimeMillis = System.currentTimeMillis();
        }
        Log.e("jyt-End", "BMoudle" + currentTimeMillis + "====" + Thread.currentThread());
    }
}

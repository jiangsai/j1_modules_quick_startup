package com.playplan.test;

import android.util.Log;

import com.playplan.fastmodules.module.IFastModule;
import com.playplan.fastmodules.node.FastNode;

/**
 * author : jyt
 * time   : 2021/10/19
 * desc   :
 */
public class app1 implements IFastModule {
    @Override
    public void intitFastNodeList() {
        Log.e("jyt", "test app1 is load");
        new FastNode(() -> {
            todo1();
        });
        new FastNode(() -> {
            todo2();
        }, 0, false);
        new FastNode(() -> {
            todo3();
        }, 0, false);
    }

    private void todo3() {
        long currentTimeMillis = System.currentTimeMillis();
        Log.e("jyt====strart", "AMoudle3==" + currentTimeMillis + "====" + Thread.currentThread().getName());
        long useTime = currentTimeMillis + (1000 * 3);
        while (currentTimeMillis < useTime) {
            currentTimeMillis = System.currentTimeMillis();
        }
        Log.e("jyt====End", "AMoudle3==" + currentTimeMillis + "====" + Thread.currentThread().getName());
    }

    private void todo2() {
        long currentTimeMillis = System.currentTimeMillis();
        Log.e("jyt====strart", "AMoudle2==" + currentTimeMillis + "====" + Thread.currentThread().getName());
        long useTime = currentTimeMillis + (1000 * 3);
        while (currentTimeMillis < useTime) {
            currentTimeMillis = System.currentTimeMillis();
        }
        Log.e("jyt====End", "AMoudle2==" + currentTimeMillis + "====" + Thread.currentThread().getName());
    }


    public void todo1() {
        long currentTimeMillis = System.currentTimeMillis();
        Log.e("jyt====strart", "AMoudle==" + currentTimeMillis + "====" + Thread.currentThread().getName());
        long useTime = currentTimeMillis + (1000 * 15);
        while (currentTimeMillis < useTime) {
            currentTimeMillis = System.currentTimeMillis();
        }
        Log.e("jyt====End", "AMoudle==" + currentTimeMillis + "====" + Thread.currentThread().getName());
    }
}

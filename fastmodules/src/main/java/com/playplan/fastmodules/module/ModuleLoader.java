package com.playplan.fastmodules.module;


import com.playplan.fastmodules.JobWork;
import com.playplan.fastmodules.TestModuleProxyCreator;
import com.playplan.fastmodules.node.FastNode;

import java.util.ArrayList;
import java.util.Collections;


/**
 * author : jyt
 * time   : 2021/03/30
 * desc   :
 */
public class ModuleLoader {

    static ArrayList<FastNode> list = new ArrayList<>();

    static boolean isMainPorcess = true;
    static String currentProcessName = "";

//    public static void registerModule(String className) {
//        System.out.println("jyt" + "===" + className);
//        if (null == className) {
//            return;
//        }
//        try {
//            Object obj = Class.forName(className).getConstructor().newInstance();
//            if (obj instanceof IFastModule) {
//                registerModule((IFastModule) obj);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private static void registerModule(IFastModule module) {
//
//        if (null == module) {
//            return;
//        }
//        module.intitFastNodeList();
//
//    }

    private static void initMoudle() {
        //  Test.INSTANCE.test();
        // clear();
        clear();

        new TestModuleProxyCreator().initData();

        if (isMainPorcess) {
            list.addAll(FastNode.getFastNodeList(FastNode.MAIN_PROCESS));
        } else {
            list.addAll(FastNode.getFastNodeList(currentProcessName));
        }

        System.out.println("jyt" + "===" + list.size());

        long currentTimeMillis = System.currentTimeMillis();
        System.out.println("jyt" + "allmoudle===" + currentTimeMillis);

        Collections.sort(list);
        JobWork.INSTANCE.toJob(list);


        long currentTimeMillisend = System.currentTimeMillis();
        System.out.println("jyt" + "allmoudle===" + (currentTimeMillisend - currentTimeMillis));

        clear();
    }

    public static void initMoudle(String processName, boolean isMain) {
        currentProcessName = processName;
        isMainPorcess = isMain;
        initMoudle();
    }

    public static void clear() {
        FastNode.clearMap();
        list.clear();
    }

}

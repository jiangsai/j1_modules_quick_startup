package com.playplan.fastmodules.node;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * author : jyt
 * time   : 2021/03/30
 * desc   :
 */
public class FastNode implements Comparable<FastNode> {

    /**
     * 需要初始化的方法回调
     */

    INodeMethod iNodeMethod;

    /**
     * 初始化优先级  0-10
     */

    int priority;

    /**
     * 是否必须在主线程初始化
     */

    boolean isMainThread = false;

    /**
     * 进程管理
     */
    private static HashMap<String, ArrayList<FastNode>> processMap = new HashMap<>();


    public static HashMap<String, ArrayList<FastNode>> getProcessMap() {
        return processMap;
    }

    public static ArrayList getFastNodeList(String processName) {
        ArrayList list = processMap.get(processName);
        if (list == null) {
            return new ArrayList();
        }
        return list;
    }

    public static void clearMap() {
        processMap.clear();
    }

    public static final String MAIN_PROCESS = "main_process";

    public FastNode() {
        addMainProcess();
    }

    public FastNode(INodeMethod iNodeMethod) {
        this.iNodeMethod = iNodeMethod;
        this.priority = 0;
        addMainProcess();
    }

    public FastNode(INodeMethod iNodeMethod, int priority) {
        this.iNodeMethod = iNodeMethod;
        this.priority = priority;
        addMainProcess();
    }

    public FastNode(INodeMethod iNodeMethod, int priority, boolean isMainThread) {
        this.iNodeMethod = iNodeMethod;
        this.priority = priority;
        this.isMainThread = isMainThread;
        addMainProcess();
    }


    private void addMainProcess() {
        ArrayList<FastNode> list = processMap.get(MAIN_PROCESS);
        if (list == null) {
            list = new ArrayList<FastNode>();
            processMap.put(MAIN_PROCESS, list);
        }

        list.add(this);
    }

    public FastNode addOtherProcess(String otherProcess) {
        ArrayList<FastNode> list = processMap.get(otherProcess);
        if (list == null) {
            list = new ArrayList<FastNode>();
            processMap.put(otherProcess, list);
        }

        list.add(this);
        return this;
    }


    public void use() {
        if (iNodeMethod != null) {
            iNodeMethod.method();
        }
    }

    public boolean isMainThread() {
        return isMainThread;
    }

    public int getPriority() {
        return priority;
    }


    @Override
    public int compareTo(@NotNull FastNode fastNode) {
        if (fastNode == null) {
            return 1;
        }
        if (priority > fastNode.priority) {
            return 1;
        } else {
            return -1;
        }
    }
}

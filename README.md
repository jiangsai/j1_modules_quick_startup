# j1_modules_quick_startup
启动优化组件


## 1 在根buid加入
```xml
buildscript {
    repositories { 
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        classpath "com.github.jiangsai:j1_modules_startup_plugin:1.0.0"
    }
}
```

##  2 在主项目buid加入
```xml
plugins {
    id 'fastmoudle'
}
dependencies {
    //这个写在base里就好了，不然每个moudle都要写
    api 'com.github.jiangsai:j1_modules_fastmodules:1.0.0'
}

fastmoduleconf{
  //com/playplan/boot/HelloWorld
    fastModules=["你组件类的的路径，请用/区分",]
}

```

## 3 在你需要初始化的组件中实现 IFastModule

```xml
public class HelloWorld  implements IFastModule {
   
    @Override
    public void intitFastNodeList() {
        
        //FastNode 细心的为你 提供了你要初始化的优先级，是否在主线程工作 ,需要在什么进程工作
        new FastNode(() -> {
            todo1();
        }, 0, false)
        .addOtherProcess("test1")
        .addOtherProcess("test2");
        
        //当然你也可以把任务在另起一个节点，就可以在一个组件里面也支持并发了
         new FastNode(() -> {
            todo2();
        }, 0, false);

    }
}
```

## 4 在你主app中初始化 加入下面代码

```xml
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ModuleLoader.initMoudle(SystemUtil.getCurProcessName(), SystemUtil.isProcess(BuildConfig.APPLICATION_ID));
    }

}
```



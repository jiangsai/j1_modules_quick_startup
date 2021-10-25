# j1_modules_quick_startup
启动优化组件




## 1 使用方式
## 在根buid加入
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

##  2 在项目buid加入
plugins {
    id 'fastmoudle'
}
dependencies {
    implementation 'com.github.jiangsai:j1_modules_fastmodules:1.0.0'
}

fastmoduleconf{
  //com/playplan/boot/App
    fastModules=["你app的路径，请用/区分",]
}



## 3 在你需要初始化的组件中实现 IFastModule

public class App组件  implements IFastModule {
   
    @Override
    public void intitFastNodeList() {
        //这里做组件初始化操作
        Log.e("jyt","test === initFastNodeList");
    }
}

## 4 在你主app中初始化 加入下面代码

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ModuleLoader.initMoudle(SystemUtil.getCurProcessName(), SystemUtil.isProcess(BuildConfig.APPLICATION_ID));
    }

}




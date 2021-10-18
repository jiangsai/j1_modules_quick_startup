package com.playplan.fastmodules.module;

/**
 * author : jyt
 * time   : 2021/03/30
 * desc   :
 */
public class ModuleConstant {

    /**
     * Module接口
     */
    public static final String MODULE_INTERFACE = "com.fastmoudle.fastmoudle_interfaces.module.IFastModule";
    /**
     * 要生成的代理类的包名，该包名下不要有其他不相关的业务类
     */
    public static final String PROXY_CLASS_PACKAGE_NAME = "com.fastmoudle.android.generated.module";

    /**
     * 生成代理类统一的后缀
     */
    public static final String PROXY_CLASS_SUFFIX = "$$Proxy";

    /**
     * 生成代理类统一的前缀
     */
    public static final String PROXY_CLASS_PREFIX = "Fast$$";
}

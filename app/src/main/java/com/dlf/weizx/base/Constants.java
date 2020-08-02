package com.dlf.weizx.base;

import java.io.File;

public interface Constants {
    //开发状态
    boolean isDebug = true;

    //网络缓存的地址
    String PATH_DATA = BaseApp.sBaseApp.getCacheDir().getAbsolutePath() +
            File.separator + "data";
    String PATH_CACHE = PATH_DATA + "/NetCache";
}

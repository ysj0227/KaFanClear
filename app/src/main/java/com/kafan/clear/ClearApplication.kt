package com.kafan.clear

import android.os.Environment
import android.util.Log
import com.blankj.utilcode.util.Utils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kafan.clear.base.BaseApplication
import com.kafan.clear.config.ApiConst
import com.kafan.clear.config.BaseConfig
import com.kafan.clear.lifecycle.CustomActivityLifecycleCallbacks
import com.kafan.clear.utilities.AppUtils
import com.kafan.clear.utilities.FileHelper
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.QbSdk.PreInitCallback


/**
 * @author ysj
 * @date 2021/6/22
 * @description
 **/
class ClearApplication : BaseApplication() {

    private var LOCAL_PATH = (Environment.getExternalStorageDirectory().absolutePath
            + "/Android/data/")

    override fun onCreate() {
        super.onCreate()
        init()
        TBS()
    }

    private fun TBS() {
        LOCAL_PATH = LOCAL_PATH + this.packageName + "/"

//
//        //今日头条适配配置
//        AutoSizeConfig.getInstance().unitsManager
//            .setSupportDP(false)
//            .setSupportSP(false).supportSubunits = Subunits.NONE
//        CommonLibConstant.init()
//            .setAppContext(this)
//            .setNoNetWorkRemind("无网络") //存储权限别忘了，别忘记修改存储的数据库名
//            .setSharedPreferencesName("x5webview_db")
//            .setCrashSavePath(LOCAL_PATH)
//            .setExternalNetworkIP() //要设置这个必有连网权限
//        //打印的tag搜索：AndroidAutoSize
//        LogUtils.e("LOCAL_PATH===$LOCAL_PATH")



        // 在调用TBS初始化、创建WebView之前进行如下配置
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_PRIVATE_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)

        QbSdk.setDownloadWithoutWifi(true)
        QbSdk.initX5Environment(this, object : PreInitCallback {
            override fun onCoreInitFinished() {
                Log.e("TAG", "999999========onCoreInitFinished:true")
            }

            override fun onViewInitFinished(b: Boolean) {
                //加载x5内核成功返回值为true，否则返回false，加载失败会调用系统的webview
                Log.e("TAG", "999999========x5初始化结果:$b")
            }
        })
    }

    private fun init() {
        initAppConfig()
        registerActivityLifecycleCallbacks(CustomActivityLifecycleCallbacks)
        initLiveEventBus()
        FileHelper.initializer
        Utils.init(instance)
    }

    /**
     * 初始化app环境配置
     */
    private fun initAppConfig() {
        val env: String? = AppUtils.getMetaValue(this, "ENV_DATA", BaseConfig.ENV_DEV)
        ApiConst().init(this, env)
    }

    /**
     * 初始化LiveEventBus
     */
    private fun initLiveEventBus() {
        LiveEventBus
            .config()
            .autoClear(true)
            .lifecycleObserverAlwaysActive(true)
    }

//    override fun attachBaseContext(base: Context?) {
//        super.attachBaseContext(base)
//        MultiDex.install(this)
//    }

}
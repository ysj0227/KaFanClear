package com.kafan.clear.base

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.inflateBindingWithGeneric
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kingja.loadsir.core.LoadService
import com.kafan.clear.utilities.MyMMKV.Companion.mmkv
import com.kafan.clear.views.LoadingDialog

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {


    val TAG = javaClass.simpleName

    private var _binding: VB? = null

    val mBinding get() = _binding!!

    private lateinit var mContext: Context
    private lateinit var mLoadingDialog: LoadingDialog
    private lateinit var loadService: LoadService<Any>

    /**
     * 列表接口每页请求的条数
     */
    val pageSize = 20

    /**
     * 布局文件id
     */
    abstract fun getLayoutId(): Int

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化 View
     */
    abstract fun initView()

    /**
     * 开始请求
     */
    abstract fun startHttp()

    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        LiveEventBus.get("isConnected", Boolean::class.java).observe(this, {
            if (it) startHttp()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //防止输入法顶起底部布局
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        mContext = this

        _binding = inflateBindingWithGeneric(layoutInflater)
        setContentView(mBinding.root)
        mLoadingDialog = LoadingDialog(this, false)

        //0是默认值
        if (mmkv.decodeInt("max_size") == 0) {
            mmkv.encode("max_size", resources.displayMetrics.heightPixels)
        }

        initView()
        initData()
        doReConnected()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    /**
     * show 加载中
     */
    fun showLoading() {
        mLoadingDialog.showDialog(mContext, false)
    }

    /**
     * dismiss loading dialog
     */
    fun dismissLoading() {
        mLoadingDialog.dismissDialog()
    }

    private var time: Long = 0
    private var oldMsg: String? = null

    /**
     * 相同msg 只显示一个。
     */
    fun showToast(msg: String) {
        if (msg != oldMsg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
            time = System.currentTimeMillis()
        } else {
            if (System.currentTimeMillis() - time > 2000) {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
                time = System.currentTimeMillis()
            }
        }
        oldMsg = msg
    }
}
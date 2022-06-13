package com.kafan.clear.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.inflateBindingWithGeneric
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kingja.loadsir.core.LoadService
import com.kafan.clear.views.LoadingDialog

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    val TAG = javaClass.simpleName
    open lateinit var mainView: View //fragment显示view
    private var _binding: VB? = null
    private lateinit var mContext: Context
    private lateinit var mLoadingDialog: LoadingDialog
    private lateinit var loadService: LoadService<Any>

    // This property is only valid between onCreateView and
    // onDestroyView.
    val mBinding get() = _binding!!

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
     * 初始化界面
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        _binding = inflateBindingWithGeneric(layoutInflater)
        _binding = inflateBindingWithGeneric(layoutInflater, container, false)
        mainView = mBinding.root
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoadingDialog = LoadingDialog(view.context, false)
        initView()
        initData()
        doReConnected()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    /**
     * show 加载中
     */
    protected fun showLoading() {
        mLoadingDialog.showDialog(mContext, false)
    }

    /**
     * dismiss loading dialog
     */
    protected fun dismissLoading() {
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

    protected fun isLogin(): Boolean {
        return true
    }
}
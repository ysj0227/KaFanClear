package com.kafan.clear.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.kafan.clear.R
import com.kafan.clear.utilities.statusbar.StatusBarCompat
import com.kafan.clear.utilities.toast
import com.kafan.clear.viewmodels.common.CommonViewModel
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.net.ConnectException
import kotlin.coroutines.cancellation.CancellationException

abstract class BaseViewModelFragment<VM : CommonViewModel, VB : ViewBinding> :
    BaseFragment<VB>() {

    private val fragmentName = javaClass.simpleName
    protected lateinit var viewModel: VM

    abstract fun providerVMClass(): Class<VM>?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVM()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initVM() {
        providerVMClass()?.let {
            viewModel = ViewModelProvider(this).get(it)
            lifecycle.addObserver(viewModel)
        }
    }

    fun gotoActivity(clazz: Class<*>?) {
        Intent(activity, clazz).apply {
            activity?.startActivity(this)
        }
    }

    fun gotoActivity(clazz: Class<*>?, bundle: Bundle?) {
        Intent(activity, clazz).apply {
            if (bundle != null) {
                putExtras(bundle)
            }
            activity?.startActivity(this)
        }
    }

    fun setTitleBarTop(view: View) {
        //设置状态栏颜色和背景
        activity?.let { StatusBarCompat.compat(it, R.color.white) }
        //设置title距离状态栏间距
        val ht = activity?.let { StatusBarCompat.getStatusBarHeight(it) }
        if (ht != null) {
            val params: ViewGroup.MarginLayoutParams =
                view.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = ht
            view.layoutParams = params
        }
    }

    fun setTopBarColor(view: View, color: Int) {
        //设置状态栏颜色和背景
        activity?.let { StatusBarCompat.compat(it, color) }
        //设置title距离状态栏间距
        val ht = activity?.let { StatusBarCompat.getStatusBarHeight(it) }
        if (ht != null) {
            val params: ViewGroup.MarginLayoutParams =
                view.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = (ht + 30)
            view.layoutParams = params
        }
    }

    //设置view距离状态栏
    fun setViewTop(view: View) {
        val ht = activity?.let { StatusBarCompat.getStatusBarHeight(it) }
        if (ht != null) {
            val params: ViewGroup.MarginLayoutParams =
                view.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = (ht + 30)
            view.layoutParams = params
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::viewModel.isInitialized)
            lifecycle.removeObserver(viewModel)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        startObserve()
        startHttp()
    }

    private fun startObserve() {
        //处理一些通用异常，比如网络超时等
        viewModel.run {
            getError().observe(requireActivity(), {

                requestError(it)
            })
            getFinally().observe(requireActivity(), {
                requestFinally(it)
            })
        }
    }

    open fun requestFinally(it: Int?) {
    }

    open fun requestError(it: Exception?) {
        //处理一些已知异常
        it?.run {
            Log.d("${TAG}--->6666666}", it.message.toString())
            when (it) {
                is CancellationException -> Log.d("${TAG}--->接口请求取消", it.message.toString())
                is TimeoutCancellationException -> toast("请求超时")
                is BaseRepository.TokenInvalidException -> toast("登陆超时")
                is HttpException -> {
                    if (it.code() == 504) toast("无法连接服务器,请检查网络设置")
                    else toast(it.message.toString())
                }
                is ConnectException -> toast("无法连接服务器,请检查网络设置")
                else -> toast(it.message.toString())
            }
        }
    }
}
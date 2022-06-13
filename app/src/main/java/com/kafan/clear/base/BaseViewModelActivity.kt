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
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.net.ConnectException
import kotlin.coroutines.cancellation.CancellationException

abstract class BaseViewModelActivity<VM : BaseViewModel, VB : ViewBinding> : BaseActivity<VB>() {

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        initVM()
        super.onCreate(savedInstanceState)
        startObserve()
        startHttp()
    }

    private fun initVM() {
        providerVMClass().let {
            viewModel = ViewModelProvider(this).get(it)
            lifecycle.addObserver(viewModel)
        }
    }

    fun gotoActivity(clazz: Class<*>?) {
        Intent(this, clazz).apply {
            startActivity(this)
        }
    }

    fun gotoActivity(clazz: Class<*>?, bundle: Bundle?) {
        Intent(this, clazz).apply {
            if (bundle != null) {
                putExtras(bundle)
            }
            startActivity(this, bundle)
        }
    }

    fun setTitleBarTop(view: View) {
        //设置状态栏颜色和背景
        StatusBarCompat.compat(this, R.color.white)
        //设置title距离状态栏间距
        val ht = StatusBarCompat.getStatusBarHeight(this)
        val params: ViewGroup.MarginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = ht
        view.layoutParams = params
    }

    fun setTopBarColor(view: View, color: Int) {
        //设置状态栏颜色和背景
        StatusBarCompat.compat(this, color)
        //设置title距离状态栏间距
        val ht = StatusBarCompat.getStatusBarHeight(this)
        val params: ViewGroup.MarginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = (ht + 30)
        view.layoutParams = params
    }

    //设置view距离状态栏
    fun setViewTop(view: View) {
        val ht = StatusBarCompat.getStatusBarHeight(this)
        val params: ViewGroup.MarginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = (ht + 30)
        view.layoutParams = params
    }

    //viewModel实例
    abstract fun providerVMClass(): Class<VM>

    private fun startObserve() {
        //处理一些通用异常，比如网络超时等
        viewModel.run {
            getError().observe(this@BaseViewModelActivity, {
//                hideLoading()
//                hideSearchLoading()
                requestError(it)
            })
            getFinally().observe(this@BaseViewModelActivity, {
                requestFinally(it)
            })
        }
    }

    //接口请求完毕，子类可以重写此方法做一些操作
    open fun requestFinally(it: Int?) {}

    //接口请求出错，子类可以重写此方法做一些操作
    open fun requestError(it: Exception?) {
        //处理一些已知异常
        it?.run {
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

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }
}
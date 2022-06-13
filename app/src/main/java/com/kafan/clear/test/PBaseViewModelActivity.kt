package com.kafan.clear.test

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.kafan.clear.base.BaseViewModel

/**
 * @author ysj
 * @date 2022/1/7
 * @description
 **/
abstract class PBaseViewModelActivity<VM : BaseViewModel, VB : ViewBinding> : PBaseActivity<VB>() {
    private lateinit var viewModel: VM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVM()
    }

    private fun initVM() {
        providerVMClass().let {
            viewModel = ViewModelProvider(this).get(it)
            lifecycle.addObserver(viewModel)
        }
    }

    abstract fun providerVMClass(): Class<VM>


    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }
}
package com.kafan.clear.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.inflateBindingWithGeneric

/**
 * @author ysj
 * @date 2022/1/7
 * @description
 **/
abstract class PBaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    private val mBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = inflateBindingWithGeneric(layoutInflater)
        setContentView(mBinding?.root)

        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun initView();

}
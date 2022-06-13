package com.kafan.clear.ui.activity

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.content.res.ResourcesCompat
import com.kafan.clear.R
import com.kafan.clear.base.BaseActivity
import com.kafan.clear.databinding.ActivityColorFilterBinding
import com.kafan.clear.ui.WaterMarkView
import com.kafan.clear.ui.test.MnActivity


/**
 * @author ysj
 * @date 2022/1/10
 * @description
 **/
class ColorFilterActivity : BaseActivity<ActivityColorFilterBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_color_filter

    override fun initData() {
    }

    override fun startHttp() {
    }

    override fun initView() {

        val bitmap = (ResourcesCompat.getDrawable(resources, R.mipmap.ic_test, null) as BitmapDrawable).bitmap
        mBinding.ivImage.setImageBitmap(bitmap)

        mBinding.btnFilter.setOnClickListener {
            val intent = Intent(this, ColorMatrixActivity::class.java)
            startActivity(intent)
        }
        mBinding.btnFilterReset.setOnClickListener {
            val intent = Intent(this, MnActivity::class.java)
            startActivity(intent)
        }


        mBinding.btnBg.setOnClickListener {
            WaterMarkView.instance.removeWaterView()

        }
        mBinding.btnBg1.setOnClickListener {
            WaterMarkView.instance.showWatermarkView(this, "Hello Word!")
        }

        mBinding.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                WaterMarkView.instance.showWatermarkView(this@ColorFilterActivity, "Hello Word!", progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }
}
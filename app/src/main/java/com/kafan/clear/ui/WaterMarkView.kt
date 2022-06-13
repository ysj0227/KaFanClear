package com.kafan.clear.ui

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import com.kafan.clear.R

/**
 * @author ysj
 * @date 2022/1/11
 * @description
 **/
class WaterMarkView() {

    private var rootView: ViewGroup? = null
    private var waterView: View? = null
    private var size = 18F
    private var margin = 40F

    companion object {

        val instance by lazy { WaterMarkView() }
    }

    /**
     *  Activity中显示水印，在onCreate的setContentView方法后调用
     *  多个界面显示水印可在BaseActivity中统一调用
     *  @param activity
     * @param string 水印文字内容
     */
    fun showWatermarkView(activity: Activity, string: String) {
        rootView = activity.window.decorView.findViewById(android.R.id.content)
        waterView = LayoutInflater.from(activity).inflate(R.layout.layout_watermark, null)

        val wm: WindowManager = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels         // 屏幕宽度（像素）
        val height = dm.heightPixels       // 屏幕高度（像素）

        waterView?.findViewById<ImageView>(R.id.ivWm)
            ?.setImageDrawable(getMarkTextBitmapDrawable(activity, string, width, height))
        //可对水印布局进行初始化操作
        rootView?.addView(waterView)
    }

    /**
     * 去除水印
     */
    fun removeWaterView() {
        if (rootView != null && waterView != null) {
            rootView?.removeView(waterView)
        }
    }

    fun showWatermarkView(activity: Activity, string: String, mSize: Int) {
        size = if (mSize == 0) {
            2F
        } else {
            mSize.toFloat()
        }
        removeWaterView()
        showWatermarkView(activity, string)
    }


    /**
     * 获得文字水印的图片
     * @param width
     * @param height
     * @return
     */
    private fun getMarkTextBitmapDrawable(gContext: Context, gText: String, width: Int, height: Int): Drawable? {
        val bitmap = getMarkTextBitmap(gContext, gText, width, height)
        if (bitmap != null) {
            val drawable = BitmapDrawable(gContext.resources, bitmap)
            drawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
            drawable.setDither(true)
            return drawable
        }
        return null
    }

    private fun getMarkTextBitmap(gContext: Context, gText: String, width: Int, height: Int): Bitmap? {
        //文字大小
        val textSize: Float =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                size,
                gContext.resources.displayMetrics
            )
        //文字间距
        val textMargin: Float =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                margin,
                gContext.resources.displayMetrics
            )

        val sideLength: Int = if (width > height) {
            Math.sqrt((2 * (width * width)).toDouble()).toInt()
        } else {
            Math.sqrt((2 * (height * height)).toDouble()).toInt()
        }

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val rect = Rect()
        paint.textSize = textSize
        //获取文字长度和宽度
        paint.getTextBounds(gText, 0, gText.length, rect)

        val strwid = rect.width()
        val strhei = rect.height()

        var markBitmap: Bitmap? = null
        try {
            markBitmap = Bitmap.createBitmap(sideLength, sideLength, Bitmap.Config.ARGB_4444)
            val canvas = Canvas(markBitmap)
            //创建透明画布
            canvas.drawColor(Color.TRANSPARENT)

            paint.color = Color.BLACK
            paint.alpha = (0.1 * 255f).toInt()
            // 获取跟清晰的图像采样
            paint.isDither = true
            paint.isFilterBitmap = true

            //先平移，再旋转才不会有空白，使整个图片充满
            if (width > height) {
                canvas.translate(width.toFloat() - sideLength.toFloat() - textMargin, sideLength - width + textMargin)
            } else {
                canvas.translate(height.toFloat() - sideLength.toFloat() - textMargin, sideLength - height + textMargin)
            }

            //将该文字图片逆时针方向倾斜45度
            canvas.rotate((-45).toFloat())

            var i = 0
            while (i <= sideLength) {
                var count = 0
                var j = 0
                while (j <= sideLength) {
                    if (count % 2 == 0) {
                        canvas.drawText(gText, i.toFloat(), j.toFloat(), paint)
                    } else {
                        //偶数行进行错开
                        canvas.drawText(gText, (i + strwid / 2).toFloat(), j.toFloat(), paint)
                    }
                    j = (j.toFloat() + textMargin + strhei.toFloat()).toInt()
                    count++
                }
                i = (i.toFloat() + strwid.toFloat() + textMargin).toInt()
            }
            canvas.save()
        } catch (e: OutOfMemoryError) {
            if (markBitmap != null && !markBitmap.isRecycled) {
                markBitmap.recycle()
                markBitmap = null
            }
        }

        return markBitmap
    }
}
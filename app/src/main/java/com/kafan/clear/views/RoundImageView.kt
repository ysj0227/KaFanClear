package com.kafan.clear.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView

/**
 * @author ysj
 * @date 2021/6/28
 * @description
 */
@SuppressLint("AppCompatCustomView")
class RoundImageView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {
    //圆角大小，默认为10
    private val mBorderRadius = 12
    private val mPaint: Paint = Paint()

    // 3x3 矩阵，主要用于缩小放大
    private val mMatrix: Matrix = Matrix()

    //渲染图像，使用图像为绘制图形着色
    private var mBitmapShader: BitmapShader? = null
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        if (drawable == null) {
            return
        }
        val bitmap = drawableToBitamp(drawable)
        mBitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        var scale = 1.0f
        if (!(bitmap.width == width && bitmap.height == height)) {
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
            scale = Math.max(
                width * 1.0f / bitmap.width,
                height * 1.0f / bitmap.height
            )
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale)
        // 设置变换矩阵
        mBitmapShader!!.setLocalMatrix(mMatrix)
        // 设置shader
        mPaint.shader = mBitmapShader
        canvas.drawRoundRect(
            RectF(0F, 0F, width.toFloat(), height.toFloat()),
            mBorderRadius.toFloat(),
            mBorderRadius.toFloat(),
            mPaint
        )
    }

    private fun drawableToBitamp(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        // 当设置不为图片，为颜色时，获取的drawable宽高会有问题，所有当为颜色时候获取控件的宽高
        val w = if (drawable.intrinsicWidth <= 0) width else drawable.intrinsicWidth
        val h = if (drawable.intrinsicHeight <= 0) height else drawable.intrinsicHeight
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        drawable.draw(canvas)
        return bitmap
    }

    init {
        mPaint.isAntiAlias = true
    }
}
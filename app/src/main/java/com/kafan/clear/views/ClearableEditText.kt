package com.kafan.clear.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.kafan.clear.R

/**
 * @author ysj
 * @date 2021/7/31
 * @description
 */
class ClearableEditText : AppCompatEditText {
    private var mClearDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        mClearDrawable = ContextCompat.getDrawable(context, R.mipmap.ic_edit_delete)
    }

    override fun onTextChanged(
        text: CharSequence,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        setClearIconVisible(hasFocus() && text.isNotEmpty())
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        setClearIconVisible(focused && length() > 0)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                val drawable = compoundDrawables[DRAWABLE_RIGHT]
                if (drawable != null && event.x <= width - paddingRight && event.x >= width - paddingRight - drawable.bounds.width()) {
                    setText("")
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun setClearIconVisible(visible: Boolean) {
        setCompoundDrawablesWithIntrinsicBounds(
            compoundDrawables[DRAWABLE_LEFT],
            compoundDrawables[DRAWABLE_TOP],
            if (visible) mClearDrawable else null,
            compoundDrawables[DRAWABLE_BOTTOM]
        )
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setClearIcon(resId: Int) {
        mClearDrawable = resources.getDrawable(resId)
    }

    companion object {
        private const val DRAWABLE_LEFT = 0
        private const val DRAWABLE_TOP = 1
        private const val DRAWABLE_RIGHT = 2
        private const val DRAWABLE_BOTTOM = 3
    }
}
package com.kafan.clear.utilities

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.kafan.clear.R
import com.kafan.clear.base.BaseApplication

fun Context.toast(resId: Int) {
    val toast = Toast.makeText(this, resId, Toast.LENGTH_SHORT)
    toast.run {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}

fun Context.toast(text: CharSequence) {
    val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
    toast.run {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}

fun Context.longToast(resId: Int) {
    val toast = Toast.makeText(this, resId, Toast.LENGTH_LONG)
    toast.run {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}

fun Context.longToast(text: CharSequence) {
    val toast = Toast.makeText(this, text, Toast.LENGTH_LONG)
    toast.run {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}

fun Context.centerToast(msg: String?) {
    val mainHandler = Handler(Looper.getMainLooper())
    mainHandler.post {
        val tvMsg = TextView(this)
        tvMsg.text = msg
        tvMsg.setBackgroundResource(R.drawable.bg_toast_black)
        tvMsg.setTextColor(ContextCompat.getColor(this, R.color.white))
        tvMsg.setPadding(20, 20, 20, 20)
        val toast = Toast(this)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = tvMsg
        toast.show()
    }
}


fun View.toast(resId: Int) = context.toast(resId)

fun View.toast(text: CharSequence) = context.toast(text)

fun View.longToast(resId: Int) = context.longToast(resId)

fun View.longToast(text: CharSequence) = context.longToast(text)

fun Fragment.toast(resId: Int) = (activity ?: BaseApplication.mContext).toast(resId)

fun Fragment.toast(text: CharSequence) = (activity ?: BaseApplication.mContext).toast(text)

fun Fragment.longToast(resId: Int) = (activity ?: BaseApplication.mContext).longToast(resId)

fun Fragment.longToast(text: CharSequence) = (activity ?: BaseApplication.mContext).longToast(text)

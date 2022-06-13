package com.kafan.clear.ui.activity

/**
 * @author ysj
 * @date 2021/11/30
 * @description
 **/

import com.kafan.clear.R
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kafan.clear.utilities.FileHelper
import java.io.File
import com.tencent.smtt.sdk.TbsReaderView

import android.text.TextUtils
import android.widget.RelativeLayout


class TBSActivity : AppCompatActivity() {

    var tbsReaderView: TbsReaderView? = null
    var url: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tbs)

        tbsReaderView = findViewById(R.id.take)
//        val rlRoot = findViewById<RelativeLayout>(R.id.rlRoot)

//        tbsReaderView = TbsReaderView(this, readerCallback)
        url = "/storage/emulated/0/kaFanScanner/download/1.pdf"

//        rlRoot.addView(
//            tbsReaderView,
//            RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.MATCH_PARENT
//            )
//        )

        openFile()
    }


    private fun openFile() {
        val file: File = File(url)
        if (!file.exists()) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_LONG).show()
        }
        val bundle = Bundle()
        bundle.putString("filePath", url)
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath())
        val result: Boolean = tbsReaderView?.preOpen(parseFormat(url?.let { parseName(it) }), false) == true
        if (result) {
            tbsReaderView?.openFile(bundle)
        }
    }

    private fun parseFormat(fileName: String?): String? {
        return fileName!!.substring(fileName.lastIndexOf(".") + 1)
    }

    private fun parseName(url: String): String? {
        var fileName: String? = null
        try {
            fileName = url.substring(url.lastIndexOf("/") + 1)
        } finally {
            if (TextUtils.isEmpty(fileName)) {
                fileName = System.currentTimeMillis().toString()
            }
        }
        return fileName
    }

    var readerCallback =
        TbsReaderView.ReaderCallback { integer, o, o1 -> }


    override fun onDestroy() {
        super.onDestroy()
        //销毁界面的时候一定要加上，否则后面加载文件会发生异常。
        tbsReaderView?.onStop()
    }


}
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

class CameraXActivity : AppCompatActivity() {
    private lateinit var imageCapture: ImageCapture
    private var takePic: Button? = null
    private var ivPick: ImageView? = null
    private var surfacePreview: PreviewView? = null
    private lateinit var camera: Camera
    private lateinit var preview: Preview
    private val REQ_CODE: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_center_item)
        initView()
        initData()
    }

    private fun initView() {
        surfacePreview = findViewById<PreviewView>(R.id.surfacePreview)
        takePic = findViewById<Button>(R.id.take)
        ivPick = findViewById<ImageView>(R.id.ivPic)

        takePic!!.setOnClickListener {
            takePhoto()
        }

    }

    private fun initData() {
        // 检查相机权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            println("===>需要权限")
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                println("===>shouldShowRequestPermissionRationale")
            } else {
                println("===>not shouldShowRequestPermissionRationale")
            }
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQ_CODE)
        } else {
            println("===>已经获取到了权限")
            startCamera();
        }

    }

    /**
     * 打开相机并预览
     */
    private fun startCamera() {
        println("打开相机")
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

        // 设置预览
        preview = Preview.Builder().build()  //  UseCase子类Preview
        // 设置拍照
        imageCapture = ImageCapture.Builder().build()// UseCase子类ImageCapture

        // 设置打开的相机（前置/后置）
        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        try {
            // 解除相机之前的所有绑定
            cameraProvider.unbindAll()
            // 绑定前面用于预览和拍照的UseCase到相机上
            camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            // 设置用于预览的view
            preview.setSurfaceProvider(surfacePreview?.surfaceProvider)
        } catch (exc: Exception) {
            println("Use case绑定失败")
        }

    }

    /**
     * 拍照
     */
    private fun takePhoto() {
        // 拍照保存路径
//        val imagePath = filesDir.absolutePath + "/test.jpg"
        val imagePath = FileHelper.SDCARD_CACHE_IMAGE_PATH + "test.jpg"
        val file = File(imagePath);
        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()
        // 开始拍照
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    println("Photo capture failed: ${exc.message}")
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(file)
                    val msg = "Photo capture success: $savedUri"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    // 显示拍照内容
                    ivPick?.setImageBitmap(BitmapFactory.decodeFile(file.absolutePath))
                }
            })
    }

    /**
     * 点击拍照按钮
     */
    fun startTake(view: View) {
        takePhoto()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera()
            println("权限获取成功")
        } else {
            println("你拒绝了权限")
        }
    }


}
package com.kafan.clear.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.load
import com.kafan.clear.R
import com.kafan.clear.base.BaseActivity
import com.kafan.clear.base.BaseApplication
import com.kafan.clear.data.User
import com.kafan.clear.databinding.ActivityTestResultBinding
import com.kafan.clear.utilities.PermissionUtils
import com.kafan.clear.utilities.toast

/**
 * @author ysj
 * @date 2021/11/15
 * @description
 **/
class TestResultActivity : BaseActivity<ActivityTestResultBinding>(), View.OnClickListener {
    private val manageAllFileIntent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                Log.d(TAG, "999999 manageAllFileIntent = ok")
            }
        }

    @SuppressLint("WrongConstant")
    private val storageIntent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                val result = activityResult.data
                val uri = result?.data
                Log.d(TAG, "999999 storageIntent uri = $uri")
                if (uri != null) {
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }
            }
        }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            val text = if (it) "相机授权成功" else "相机授权失败"
            toast(text)
        }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result: MutableMap<String, Boolean> ->
            run {
                var isRequestError: Boolean = false
                result.entries.forEach {
                    val granted = it.value
                    if (!granted) {
                        isRequestError = !isRequestError
                    }
                }
                if (isRequestError) {
                    toast("授权失败")
                } else {
                    toast("已授权")
                }
            }

        }

    private val imageIntent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                Log.d(TAG, "999999 imageIntent = ok")
            }
        }


    override fun getLayoutId(): Int {
        return R.layout.activity_test_result
    }

    override fun initData() {
    }

    override fun initView() {
        mBinding.run {
            btn0.setOnClickListener(this@TestResultActivity)
            btn1.setOnClickListener(this@TestResultActivity)
            btn2.setOnClickListener(this@TestResultActivity)
            btn3.setOnClickListener(this@TestResultActivity)
            btn4.setOnClickListener(this@TestResultActivity)
            btn5.setOnClickListener(this@TestResultActivity)
            btn6.setOnClickListener(this@TestResultActivity)
            btn7.setOnClickListener(this@TestResultActivity)
        }
    }

    override fun startHttp() {
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn0 -> {
                requestPermission.launch(Manifest.permission.CAMERA)
            }
            R.id.btn1 -> {
                requestMultiplePermissions.launch(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                )
            }
            R.id.btn2 -> {
                //直接获取data权限，推荐使用这种方案
                // targetSdkVersion <= 29 申请文件管理所有文件访问权限
                // targetSdkVersion =30 android11的时候 文件管理根目录申请访问失败
                val uri: Uri = PermissionUtils.uriRoot
//                val uri: Uri = PermissionUtils.uriAndroidData
                var hasPermissions = false
                for (item in contentResolver.persistedUriPermissions) {
                    Log.d(TAG, "999999 persistedUriPermissions  = ${item.uri}")
                    if (item.uri.equals(uri)) {
                        hasPermissions = true
                    }
                }
                if (!hasPermissions) {
                    val documentFile = DocumentFile.fromTreeUri(this, uri)
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
                    intent.flags = (Intent.FLAG_GRANT_READ_URI_PERMISSION
                            or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                            or Intent.FLAG_GRANT_PREFIX_URI_PERMISSION)
                    intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, documentFile?.uri)
                    storageIntent.launch(intent)
                }
            }
            R.id.btn3 -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (!Environment.isExternalStorageManager()) {
                        val intentFile =
                            Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                        intentFile.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        intentFile.data =
                            Uri.parse("package:" + BaseApplication.instance.packageName)
                        manageAllFileIntent.launch(intentFile)
                    }
                }
            }
            R.id.btn4 -> {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                imageIntent.launch(intent)
            }
            R.id.btn5 -> {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "video/*"
                imageIntent.launch(intent)
            }
            R.id.btn6 -> {
                //TBS
                val intent= Intent(this, CommonWebViewActivity::class.java) //文件通过微信接收下载名称一致即可
//                intent.putExtra("url", "/storage/emulated/0/kaFanScanner/download/ok.xlsx")
                intent.putExtra("url", "/storage/emulated/0/kaFanScanner/files/ok.xls")
                intent.putExtra("isOpenFile", true)
                startActivity(intent)
            }
            R.id.btn7 -> {
                val list: MutableList<String> = mutableListOf()
                list.add("11")
                list.add("11")
                list.add("22")
                list.add("33")

//                for (index in list) {
//                    Log.d(TAG, "999999 A= $index")
//                }
//
//                for (index in list.iterator()) {
//                    Log.d(TAG, "999999 B= $index")
//                }
//
//                for (index in list.withIndex()) {
//                    Log.d(TAG, "999999 C index= ${index.index}    value=  ${index.value}")
//                }

//                Glide.with(this).load(R.mipmap.ic_test).into(mBinding.ivImg)

                val imageLoader = ImageLoader.Builder(this)
                    .componentRegistry {
                        add(GifDecoder())
                    }
                    .build()
                mBinding.ivImg.load(R.mipmap.ic_test, imageLoader)

                val dataList: MutableList<User> = mutableListOf()
                repeat(4) {
                    val u = User("$it", "aa")
                    dataList.add(u)
                }

//                dataList.iterator().forEach {
//                    Log.d(TAG, "999999 forEach index= ${it.id}  ${it.name}")
//                }

                dataList.map {
                    Log.d(TAG, "999999 map index= ${it.id}  ${it.name}")
                }
            }
        }
    }


    private fun findApk(file: DocumentFile) {
        file.type
        if (file.isDirectory) {
            for (inner in file.listFiles()) {
                Log.d(TAG, "999999 apk  = ${file.name}")
                findApk(inner)
            }
        } else if (file.isFile && file.name?.contains(".apk") == true) {
            Log.d(TAG, "999999 apk  = ${file.name}")
        }
    }
}
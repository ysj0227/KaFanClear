package com.kafan.clear

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kafan.clear.databinding.ActivityMainBinding
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    private fun dialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("权限开启提示")
            .setMessage("为了更好的体验应用各项功能 \n需要开启文件管理功能 \n公司承诺不会获取用户的隐私信息")
            .setNegativeButton("退出") { _, _ ->
                exitProcess(0)
            }
            .setPositiveButton("申请授权") { _, _ ->

            }.create();
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
    }


    //直接获取data权限，推荐使用这种方案
    // targetSdkVersion <= 29 申请文件管理所有文件访问权限
    // targetSdkVersion =30 android11的时候 文件管理根目录申请访问失败
    private fun openDocumentTreeGrant(context: Activity) {
        val uri1: Uri =
            Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fdata")  //Android/data
//        val uri1: Uri = Uri.parse("content://com.android.externalstorage.documents/tree/primary%3A")  //根目录
        val documentFile = DocumentFile.fromTreeUri(context, uri1)
        val intent1 = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        intent1.flags = (Intent.FLAG_GRANT_READ_URI_PERMISSION
                or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                or Intent.FLAG_GRANT_PREFIX_URI_PERMISSION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent1.putExtra(DocumentsContract.EXTRA_INITIAL_URI, documentFile?.uri)
        }
        //  context.startActivityForResult(intent1, PermissionUtils.REQUEST_DOCUMENT_ANDROID_DATA)
    }

    /**
     *  选择视频: intent.type = "video/*";
     *  选择所有类型的资源: intent.type = "*/*"
     */
    private fun fileSearch() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        this.startActivityForResult(intent, 2000)
    }


}
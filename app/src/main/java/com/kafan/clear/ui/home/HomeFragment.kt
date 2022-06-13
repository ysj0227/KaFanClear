package com.kafan.clear.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kafan.clear.R
import com.kafan.clear.adapter.FileDataAdapter
import com.kafan.clear.data.FileAndroidData
import com.kafan.clear.data.User
import com.kafan.clear.databinding.FragmentHomeBinding
import com.kafan.clear.ui.activity.*
import com.kafan.clear.utilities.toast


class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val mLayoutManager by lazy { LinearLayoutManager(activity) }
    private val mAdapter by lazy { FileDataAdapter() }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        showDataList()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDataList() {

        binding.recyclerView.run {
            layoutManager = mLayoutManager
            adapter = mAdapter
        }

        Log.d("TAG", "999999  Environment= " + Environment.getExternalStorageDirectory().toString())

        Log.d("TAG", "999999  Environment= " + activity?.getExternalFilesDir(null))
        Log.d("TAG", "999999  Environment parent= " + activity?.parent)
        Log.d(
            "TAG",
            "999999  Environment= " + activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        Log.d(
            "TAG",
            "999999  Environment= " + activity?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        )
        Log.d(
            "TAG",
            "999999  Environment= " + activity?.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        )

        Log.d("TAG", "999999  *****************************************************")
        Log.d("TAG", "999999  Cache= " + activity?.externalCacheDir)
        Log.d("TAG", "999999  Cache= " + activity?.externalCacheDirs)
        Log.d("TAG", "999999  Cache= " + activity?.externalMediaDirs)


        //android 11使用
        val uri: Uri =
            Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fdata")
        val dFile = activity?.let { DocumentFile.fromTreeUri(it, uri) }
        if (dFile!!.isDirectory) {
            for (inner in dFile.listFiles()) {
                activity?.let { packNameInfo(it, inner.name!!) }
            }
        }

    }

    /**
     * 包名获取应用名称
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun packNameInfo(context: Context, packName: String) {
        try {
            if (!packName.substring(0, 1).contains(".")) {
                val pm: PackageManager = context.packageManager
                val applicationInfo = pm.getApplicationInfo(packName, 0)

                val label: String = applicationInfo.loadLabel(pm).toString()
                val icon: Drawable = applicationInfo.loadIcon(pm)
                val item = FileAndroidData(icon, label, packName)
                mAdapter.addData(item)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn1 -> {
                toast("1")
                val user = User("66", "blue")
                Log.d("TAG", "999999= ${user.id}   ${user.name}")
            }
            R.id.btn2 -> {
                val intent = Intent(activity, CameraActivity::class.java)
                startActivity(intent)
            }
            R.id.btn3 -> {
                val intent = Intent(activity, TestResultActivity::class.java)
                startActivity(intent)
            }
            R.id.btn4 -> {
//                val intent = Intent(activity, FlowActivity::class.java)
                val intent = Intent(activity, CameraXActivity::class.java)
                startActivity(intent)
            }
            R.id.btn5 -> {
                val intent = Intent(activity, LineAnimateActivity::class.java)
                startActivity(intent)
            }
            R.id.btn6 -> {
                val intent = Intent(activity, ColorFilterActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
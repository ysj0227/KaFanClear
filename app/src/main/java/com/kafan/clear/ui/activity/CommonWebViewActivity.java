package com.kafan.clear.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kafan.clear.R;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;


/**
 * Created by zyt on 2018/7/11.
 * WebView基础类
 */
public class CommonWebViewActivity extends AppCompatActivity {
    TBSWebView x5WebView;
    RelativeLayout rlRoot;

    private String url;
    TbsReaderView tbsReaderView;
    private boolean isOpenFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_common_webview);

        x5WebView = findViewById(R.id.x5WebView);
        rlRoot = findViewById(R.id.rl_root);

        initView();

    }


    class JsObject {
        @JavascriptInterface
        public void navigateTo(String jsonData) {//活动专题可能和移动端的交互
        }

    }

    protected void initView() {
        x5WebView.addJavascriptInterface(new JsObject(), "android");
        url = getIntent().getStringExtra("url");
        isOpenFile = getIntent().getBooleanExtra("isOpenFile", false);
//        x5WebView.loadUrl(url);
        handler.sendEmptyMessageDelayed(1001, 500);
        tbsReaderView = new TbsReaderView(this, readerCallback);
        //打开文件
        if (!TextUtils.isEmpty(url) && isOpenFile) {
            handler.removeMessages(10001);
            rlRoot.addView(tbsReaderView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            openFile();
        }
    }

    private void openFile() {
        File file = new File(url);
        if (!file.exists()) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_LONG).show();
        }
        Bundle bundle = new Bundle();
        bundle.putString("filePath", url);
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
        boolean result = tbsReaderView.preOpen(parseFormat(parseName(url)), false);
        if (result) {
            tbsReaderView.openFile(bundle);
        }
    }

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private String parseName(String url) {
        String fileName = null;
        try {
            fileName = url.substring(url.lastIndexOf("/") + 1);
        } finally {
            if (TextUtils.isEmpty(fileName)) {
                fileName = String.valueOf(System.currentTimeMillis());
            }
        }
        return fileName;
    }

    TbsReaderView.ReaderCallback readerCallback = new TbsReaderView.ReaderCallback() {
        @Override
        public void onCallBackAction(Integer integer, Object o, Object o1) {

        }
    };
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1001:
                    x5WebView.loadUrl(url);
                    break;
            }
            return false;
        }
    });



    @Override
    protected void onDestroy() {
        handler.removeMessages(1001);
        handler = null;
        x5WebView.reload();
        //销毁
        x5WebView.clearCache(true);
        x5WebView.clearFormData();
        x5WebView.destroy();
        tbsReaderView.onStop();
        super.onDestroy();
    }




}

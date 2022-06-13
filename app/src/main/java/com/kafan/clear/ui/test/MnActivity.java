package com.kafan.clear.ui.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.kafan.clear.R;

public class MnActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ttttt);

        SingleTouchView singleTouchView=(SingleTouchView)findViewById(R.id.stv_xueyou);
        singleTouchView.bringToFront();
        singleTouchView.setImageBitamp(Text2BitmapUtils.getBitmap("二字",500,5,3,0xff221133,0x00999999));
        singleTouchView.setOnDeleteListener(new SingleTouchView.OnDeleteListener() {
            @Override
            public void onDelete() {
                Toast.makeText(MnActivity.this,"你点击了删除按钮",Toast.LENGTH_SHORT).show();
            }
        });

//        SingleTouchView singleTouchView2=(SingleTouchView)findViewById(R.id.stv_xueyou2);
//        singleTouchView2.setImageBitamp(Text2BitmapUtils.getBitmap("下面的例子给出了可以配置的各种选项：",500,5,3,0xff221133,0x00999999));
//        SingleTouchView singleTouchView3=(SingleTouchView)findViewById(R.id.stv_xueyou3);
//        singleTouchView3.setImageBitamp(Text2BitmapUtils.getBitmap("共四个字",500,5,3,0xff221133,0x00999999));
    }
}

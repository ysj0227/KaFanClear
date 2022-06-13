package com.kafan.clear.ui.activity;

/**
 * @author ysj
 * @date 2022/1/6
 * @description
 **/

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.kafan.clear.R;

public class LineAnimateActivity extends Activity {

    private ConstraintLayout flContainer;
    private ImageButton ibAdd;
    private ImageView imgCart;
    private int[] outLocation = new int[2];
    private int[] carLocation = new int[2];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_animate);
        initView();
        initListener();
    }

    private void initListener() {
        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示购物车抛物线动画
                showAnimate();
            }
        });
    }

    private void showAnimate() {
        //在同一个位置复制一个加号按钮
        ImageButton ib = new ImageButton(this);
        //设置复制图片的背景
        ib.setBackgroundResource(R.mipmap.ic_data_empty);
        //拿到原图片的坐标
        ibAdd.getLocationInWindow(outLocation);
        imgCart.getLocationInWindow(carLocation);
        //将原图在屏幕上面的坐标设置给复制的图片
        Log.e("打印坐标", "X:" + outLocation[0] + "==Y:" + (outLocation[1] - getStatusBarHeight()));
        ib.setX((float) outLocation[0]);
        //要减去状态栏的高度
        ib.setY((float) (outLocation[1] - getStatusBarHeight()));
        //添加到窗体上
        flContainer.addView(ib, ibAdd.getWidth(), ibAdd.getHeight());
        Log.e("打印宽高：", ibAdd.getX() + "=" + ibAdd.getY() + "-----" + ib.getX() + "=" + ib.getY());
        //开始抛物线动画组合
        AnimationSet animationSet = new AnimationSet(false);
        TranslateAnimation translateAnimationX = new TranslateAnimation(Animation.ABSOLUTE, 0F, Animation.ABSOLUTE, carLocation[0] - outLocation[0], Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 0.0f);
        TranslateAnimation translateAnimationY = new TranslateAnimation(Animation.ABSOLUTE, 0F, Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, carLocation[1] - outLocation[1]);
        //Y轴增加加速效果
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        animationSet.addAnimation(translateAnimationX);
        animationSet.addAnimation(translateAnimationY);
        animationSet.setDuration(1500);
        //开始执行动画
        ib.startAnimation(animationSet);
        //设置动画结束的监听
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束，移除动画
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        //方法一：
//                        ((ViewGroup) ib.getParent()).removeView(ib);
                        //方法二：
                        flContainer.removeView(ib);
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initView() {
        ibAdd = findViewById(R.id.ib_add);
        imgCart = findViewById(R.id.imgCart);
        flContainer = findViewById(R.id.fl_Container);
    }

    /**
     * 获取状态栏高度
     */
    public int getStatusBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
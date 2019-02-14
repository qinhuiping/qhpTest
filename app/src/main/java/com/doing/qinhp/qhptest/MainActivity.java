package com.doing.qinhp.qhptest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    //浮动按钮按下时x坐标
    private float downX;
    //浮动按钮按下时y坐标
    private float downY;
    /**
     * 按下时浮层x坐标
     */
    float downViewX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.iv_onTouch);
        //获取屏幕宽度
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        final int with = outMetrics.widthPixels;
        final int height = outMetrics.heightPixels;

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //返回false，是点击事件；返回true，不记为点击事件 参考链接
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        //按下
                        downX = event.getX();
                        downY = event.getY();
                        downViewX = imageView.getX();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //移动
                        //移动的距离
                        float moveX = event.getX() - downX;// event.getX() 移动的X距离
                        float moveY = event.getY() - downY;// event.getY() 移动的Y距离
                        //当前view= X,Y坐标
                        float viewX = imageView.getX();
                        float viewY = imageView.getY();
                        //view的宽高
                        int viewHeigth = imageView.getWidth();
                        int viewWidth = imageView.getHeight();

                        //X当超出屏幕,取最大值
                        if (viewX + moveX + viewWidth > with) {
                            //靠右
                            imageView.setX(with - viewWidth);
                        } else if (viewX + moveX <= 0) {
                            //靠右
                            imageView.setX(0);
                        } else {
                            //正常
                            imageView.setX(viewX + moveX);
                        }
                        //Y当超出屏幕,取最大值
                        if (viewY + moveY + viewHeigth > height) {
                            //靠下
                            imageView.setY(height - viewHeigth);
                        } else if (viewY + moveY <= 0) {
                            //靠上
                            imageView.setY(0);
                        } else {
                            //正常
                            imageView.setY(viewY + moveY);
                        }
                        return true;


                    case MotionEvent.ACTION_UP:
                        //松手
                        float upX = imageView.getX();
                        //屏幕中心点
                        float center = with / 2;
                        if (imageView.getX() > center) {
                            //靠右
                            imageView.setX(with - imageView.getWidth());
                        } else {
                            imageView.setX(0);
                        }
                        //按下时与松手时X值一致的话，就干点别的事情
                        if (downViewX == upX) {
                            return false;
                        } else {
                            return true;
                        }
                }

                return false;
            }
        });
    }
}

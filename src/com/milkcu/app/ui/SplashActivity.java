package com.milkcu.app.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import com.milkcu.app.R;

import com.milkcu.app.ui.base.BaseActivity;
import com.umeng.update.UmengUpdateAgent;

public class SplashActivity extends BaseActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // 其实就是放置一张背景图
        View view = View.inflate(this, R.layout.start_activity, null);
        // 设置布局文件是start_activity.xml
        setContentView(view);
        // 设置动画效果是alpha，在anim目录下的alpha.xml文件中定义动画效果
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(animation);
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            
            // 这里监听动画结束的动作，在动画结束的时候开启一个线程，这个线程中绑定一个Handler,并
            // 在这个Handler中调用goHome方法，而通过postDelayed方法使这个方法延迟500毫秒执行，达到
            // 达到持续显示第一屏500毫秒的效果
            @Override
            public void onAnimationEnd(Animation arg0) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goHome();
                    }
                }, 500);
            }
        });
        // 这里调用友盟的SDK设置更新方式，这里是将仅在wifi下更新设置为false
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        // 这里调用友盟的SDK设置更新方式，这里设置为更新当前的Activity
        UmengUpdateAgent.update(this);
    }

    protected void onResume() {
        super.onResume();
    }

    private void goHome() {
    	// openActivity方法是在BaseActivity中定义的一个重载方法
        openActivity(MainActivity.class);
        // defaultFinish方法是在BaseActivity中定义的一个方法
        defaultFinish();
    }

    ;

}

package com.example.x_plan;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.logging.LogRecord;

public class LevelTwo extends AppCompatActivity {

//    private final static String TAG = "UOfly Android Thread ==>";
    private int count = 0;
    private String str;
    private boolean role1_run = false;
    private boolean role2_run = false;
    private boolean is_run = false;
    private Handler mHandler = new Handler();
    private Handler mHandler_ = new Handler();


    private Runnable mRunnable = new Runnable() {

        @SuppressLint("LongLogTag")
        public void run() {
            final ImageButton imageButton = findViewById(R.id.role1);
//            Button run = findViewById(R.id.run);
            Button a = findViewById(R.id.A);
            Button b = findViewById(R.id.B);
            final View[] views = new View[]{a, b};
            final long[] time = new long[]{5000,1000};
            Func f = new Func();
            if(is_run == true && role1_run == false){
                f.Move(imageButton, views, time);
                role1_run = true;
            }
            Button enemy = findViewById(R.id.enemy);
            imageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    initPopWindow(v);
                }
            });



            // 每0.3秒执行一次
            mHandler.postDelayed(mRunnable, 300);
        }
    };

    private Runnable mRunnable_ = new Runnable() {

        @SuppressLint("LongLogTag")
        public void run() {
            final ImageButton imageButton = findViewById(R.id.role2);
//            Button run = findViewById(R.id.run2);
            Button a = findViewById(R.id.A);
            Button b = findViewById(R.id.B);
            final View[] views = new View[]{a, b};
            final long[] time = new long[]{5000,1000};
            if(is_run == true && role2_run == false){
                try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Func f = new Func();
                        f.Move(imageButton, views, time);
                        role2_run = true;

            }

            // 每0.3秒执行一次
            mHandler.postDelayed(mRunnable_, 300);
        }
    };
    private void initPopWindow(View v){
        View view = LayoutInflater.from(LevelTwo.this).inflate(R.layout.pop1, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        Button one = view.findViewById(R.id.ins_one);
        Button two = view.findViewById(R.id.ins_two);
        Button three = view.findViewById(R.id.ins_three);
        //构造一个popWindow,参数依次是View、宽、高
        final PopupWindow popupWindow = new PopupWindow(view, 1600, 900, true);
        //设置加载动画
        popupWindow.setAnimationStyle(R.anim.anim_pop);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        //设置背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x80808080));
        //设置pop的位置,在v的基础上，偏移x、y
        popupWindow.showAsDropDown(v, 100 - v.getLeft(), -v.getBottom());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initPopWindow2(v);
            }
        });
    }
    private void initPopWindow2(View v){
        View view = LayoutInflater.from(LevelTwo.this).inflate(R.layout.pop2, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        Button move = view.findViewById(R.id.move);
        TextView ins1 = view.findViewById(R.id.ins1);
        if(str != ""){
            ins1.setText(str + "\n");
        }
        //构造一个popWindow,参数依次是View、宽、高
        final PopupWindow popupWindow = new PopupWindow(view, 1600, 900, true);
        //设置加载动画
        popupWindow.setAnimationStyle(R.anim.anim_pop);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        //设置背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x80808080));
        //设置pop的位置
        popupWindow.showAsDropDown(v, 100 - v.getLeft(), -v.getBottom());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initPopWindow(v);
            }
        });
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = str + "\n路径点选择:";
                popupWindow.dismiss();
                initPopWindow3(v);
            }
        });
    }
    private void initPopWindow3(View v){
        View view = LayoutInflater.from(LevelTwo.this).inflate(R.layout.pop3, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        Button A = view.findViewById(R.id.A1);
        Button B = view.findViewById(R.id.B1);
        Button C = view.findViewById(R.id.C1);
        Button save = view.findViewById(R.id.save1);
        final TextView choose = view.findViewById(R.id.choose);
        //构造一个popWindow,参数依次是View、宽、高
        final PopupWindow popupWindow = new PopupWindow(view, 1600, 900, true);
        //设置加载动画
        popupWindow.setAnimationStyle(R.anim.anim_pop);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        //设置背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x80808080));
        //设置pop的位置
        popupWindow.showAsDropDown(v, 100 - v.getLeft(), -v.getBottom());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initPopWindow2(v);
            }
        });
        final int[] judge = {0};
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(judge[0] != 1){
                    judge[0] = 1;
                    choose.setText(choose.getText() + "1->");
                }
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(judge[0] != 2){
                    judge[0] = 2;
                    choose.setText(choose.getText() + "2->");
                }
            }
        });
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(judge[0] != 3){
                    judge[0] = 3;
                    choose.setText(choose.getText() + "3->");
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = str + choose.getText().toString();
                popupWindow.dismiss();
                initPopWindow2(v);
            }
        });
    }



    /**
     * Called when the activity is first created.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level2);


        // 通过Handler启动线程
        mHandler.post(mRunnable);  //发送消息，启动线程运行
        mHandler_.post(mRunnable_);

        Button run = findViewById(R.id.run);
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_run = true;
            }
        });


    }

    @Override
    protected void onDestroy() {
        //将线程销毁掉
        mHandler.removeCallbacks(mRunnable);
        mHandler_.removeCallbacks(mRunnable_);
        super.onDestroy();
    }
}
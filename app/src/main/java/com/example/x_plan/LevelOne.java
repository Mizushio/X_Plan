package com.example.x_plan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


public class LevelOne extends AppCompatActivity {
    private String str = "";
    private String str1 = "";
    private int model1 = 0; //判断显示的是哪一个str
    private int[] move1 = new int[10];   //暂时存放移动的参数
    private int[] MoveChoose = new int[10];   //保存移动的数组
    private int judge = 0;    //记录哪一个路径是最新选择的
    private int count = 0;    //记录移动的次数
    private int isRun = 0;    //判断是否点击了run
    private ImageView a;
    private ImageView b;
    private ImageView end;
    private Button barrier;
    private AnimatorSet animatorSet = null;  //属性动画集合
    private Func f = new Func();
    private ImageView role1;
    private int hp = 6;
    private int barrierWidth = 500;     //障碍物的长宽
    private int barrierHeight = 100;
    private int speed = 300;     //移动速度
    private Handler mHandRole1 = new Handler();

    int[] rolePic = { R.drawable.hp0,R.drawable.hp1,R.drawable.hp2,R.drawable.hp3,R.drawable.hp4,R.drawable.hp5,R.drawable.hp6};

    //玩家一的线程
    private Runnable mRunRole = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void run() {
            //人物点击监听
            role1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(isRun == 0){
                        if(isRun == 0){
                            initPopWindow(v);
                        }
                    }
                }
            });
            //判断是否开始运行画面
            if(isRun == 1){
                isRun = 2;
                int count1 = 0;
                for(int i = 0; i < MoveChoose.length; i++){
                    if(MoveChoose[i] != 0){
                        count1++;
                    }
                    else{
                        break;
                    }
                }
                View[] views = new View[count1];
                long[] time = new long[count1];
                for(int i = 0; i < views.length; i++){
                    if(MoveChoose[i] == 1){
                        views[i] = a;
                    }
                    else if(MoveChoose[i] == 2){
                        views[i] = b;
                    }
                    else if(MoveChoose[i] == 3){
                        views[i] = end;
                    }
                    time[i] = 3000;
                }
                animatorSet = f.Move(role1, views, speed);
            }
            //判断是否接触到障碍物
            if(isRun == 2){
                int[] rolePos = new int[2];
                int[] barrierPos = new int[2];
                role1.getLocationOnScreen(rolePos);
                barrier.getLocationOnScreen(barrierPos);
                if(Math.abs(barrierPos[0] - rolePos[0]) <= barrierWidth && Math.abs(barrierPos[1] - rolePos[1]) <= barrierHeight && hp >= 0){
                    hp = 0;
                    role1.setImageDrawable(getResources().getDrawable(rolePic[hp]));
                }
            }
            //当hp为0时，停止运行
            if(hp == 0){

                animatorSet.pause();
            }
            if(isRun == 2 && f.victory(role1, end)){
                isRun = 3;
                initPopSuccess(role1);
            }
            // 每0.1秒执行一次
            mHandRole1.postDelayed(mRunRole, 100);
        }
    };
    //成功的弹窗
    private void initPopSuccess(ImageView v){
        View view = LayoutInflater.from(LevelOne.this).inflate(R.layout.level1_success, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        Button next = view.findViewById(R.id.next);
        //构造一个popWindow,参数依次是View、宽、高
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
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
        popupWindow.showAsDropDown(v);
        //返回上一级界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //选择指令一的功能
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
    //初始化函数
    private void init(){
        role1 = findViewById(R.id.role1);
        a = findViewById(R.id.A);
        b = findViewById(R.id.B);
        end = findViewById(R.id.end);
        barrier = findViewById(R.id.barrier);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level1);
        //初始化
        init();
        //启动玩家1的线程
        mHandRole1.post(mRunRole);

        //判断是否开始移动
        Button run = findViewById(R.id.run);
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //未移动且移动指令有参数
                if(isRun == 0 && MoveChoose[0] != 0){
                    isRun = 1;
                }
            }
        });
        //重新开始
        Button restart = findViewById(R.id.reset);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelOne.this, LevelOne.class);
                startActivity(intent);
            }
        });
    }
    //玩家1点击的弹窗界面设置
    private void initPopWindow(View v){
        View view = LayoutInflater.from(LevelOne.this).inflate(R.layout.level1_pop1, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        Button one = view.findViewById(R.id.ins_one);
        TextView ins1 = view.findViewById(R.id.ins1);
        ins1.setText(str);
        //构造一个popWindow,参数依次是View、宽、高
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
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
        popupWindow.showAsDropDown(v, 0, 0);
        //返回上一级界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //选择指令一的功能
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model1 = 1;
                popupWindow.dismiss();
                initPopWindow2(v);
            }
        });
    }
    private void initPopWindow2(View v){
        View view = LayoutInflater.from(LevelOne.this).inflate(R.layout.level1_pop2, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        final Button move = view.findViewById(R.id.move);
        Button save = view.findViewById(R.id.save);
        Button clear = view.findViewById(R.id.clear);
        final TextView ins = view.findViewById(R.id.ins);
        if(model1 == 1){
            ins.setText(str);
        }
        else if(model1 == 2){
            ins.setText(str1);
        }
        //构造一个popWindow,参数依次是View、宽、高
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
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
        popupWindow.showAsDropDown(v, 0, 0);
        //返回上一级界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initPopWindow(v);
            }
        });
        //保存功能
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将选择的路径点保存
                //str为零说明没有选择
                if(ins.getText() == ""){
                    //清空路径点选择数组
                    str = "";
                    for(int i = 0; i < MoveChoose.length; i++){
                        MoveChoose[i] = 0;
                    }
                }
                else{
                    str = str1;
                    for(int i = 0; i < MoveChoose.length; i++){
                        MoveChoose[i] = move1[i];
                    }
                }
                popupWindow.dismiss();
                initPopWindow(v);
            }
        });
        //清空功能
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ins.setText("");
            }
        });
        //前往移动选择界面
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str1 = "路径点选择:";
                popupWindow.dismiss();
                initPopWindow3(v);
            }
        });
    }
    private void initPopWindow3(View v){
        View view = LayoutInflater.from(LevelOne.this).inflate(R.layout.level1_pop3, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        Button A = view.findViewById(R.id.A);
        Button B = view.findViewById(R.id.B);
        Button C = view.findViewById(R.id.C);
        Button save = view.findViewById(R.id.save);
        final TextView choose = view.findViewById(R.id.choose);
        //构造一个popWindow,参数依次是View、宽、高
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
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
        popupWindow.showAsDropDown(v, 0, 0);
        //返回上一级界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model1 = 2;
                popupWindow.dismiss();
                initPopWindow2(v);
            }
        });
        //不同按钮点击
        judge = 0;
        count = 0;   //将count置为0
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(judge != 1 && count < 10){
                    judge = 1;
                    move1[count++] = 1;   //暂时存放选择过的路径点
                    choose.setText(choose.getText() + "1->");
                }
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(judge != 2 && count < 10){
                    judge = 2;
                    move1[count++] = 2;
                    choose.setText(choose.getText() + "2->");
                }
            }
        });
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(judge != 3 && count < 10){
                    judge = 3;
                    move1[count++] = 3;
                    choose.setText(choose.getText() + "3->");
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str1 = str1 + choose.getText().toString();
                model1 = 2;
                popupWindow.dismiss();
                initPopWindow2(v);
            }
        });
    }

    //销毁所有的线程
    @Override
    protected void onDestroy() {
        //将线程销毁掉
        mHandRole1.removeCallbacks(mRunRole);
        super.onDestroy();
    }

}

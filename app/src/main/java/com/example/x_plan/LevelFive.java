package com.example.x_plan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


public class LevelFive extends AppCompatActivity {
    //攻击手参数
    private String str1 = "";                  //攻击手显示的文本信息
    private String str11 = "", str12 = "", str13 = "";
    private String str2 = "";
    private String str21 = "", str22 = "", str23 = "";
    private int model1 = 0;              //判断显示的是哪一个str
    private int[] move1 = new int[10];   //暂时存放攻击手指令1移动的参数
    private int[] move2 = new int[10];   //暂时存放攻击手指令2移动的参数
    private int[] MoveChoose1 = new int[10];   //保存指令1移动的数组
    private int[] MoveChoose2 = new int[10];   //保存指令2移动的数组
    private boolean[] signalArr = {false, false, false};   //信号量数组
    private int signalChoose1 = -1, signalChoose11 = -1;   //指令1监听的信号量id
    private boolean signalVal1 = false;    //指令1监听的值
    private int signalChoose2 = -1, signalChoose21 = -1;
    private boolean signalVal2 = false;
    private int attack1 = 0, attack11 = 0;   //判断攻击手是否攻击
    private int attack2 = 0, attack21 = 0;
    private int judge1 = 0;    //记录哪一个路径是最新选择的
    private int judge2 = 0;
    private int count1 = 0;    //记录移动的次数
    private int count2 = 0;
    private int runType = 0, runType1 = 0;   //哪一个条指令移动的标志
    private double roleAttack = 350;   //人物的攻击距离
    private int speed = 150;   //攻击手的移动速度
    private int enemySpeed = 300, enemyTimes = 100;  //敌人移动速度和重复次数
    //侦查手参数
    private String inves1 = "", inves11 = "", inves12 = "", inves13 = "", inves14 = "", inves15 = "";
    private String inves2 = "", inves21 = "", inves22 = "", inves23 = "", inves24 = "", inves25 = "";
    private int model2 = 0;
    private int signalSet1 = -1, signalSet11 = -1;    //设置信号量，侦查手1号
    private int signalSet12 = -1, signalSet13 = -1;
    private boolean signalSetVal1 = false, signalSetVal3 = false;
    private int signalSet2 = -1, signalSet21 = -1;    //侦查手2号
    private int signalSet22 = -1, signalSet23 = -1;
    private boolean signalSetVal2 = false, signalSetVal4 = false;
    private int findEnemy1 = 0, findEnemy11 = 0, findEnemy12 = 0, findEnemy13 = 0;    //发现敌人,侦查手1号指令1，指令1保存，指令2，指令2保存
    private int findEnemy2 = 0, findEnemy21 = 0, findEnemy22 = 0, findEnemy23 = 0;    //侦查手2号
    private double roleFindRange = 600;   //侦查手发现敌人的距离
    //其他参数
    private ImageView[] points = new ImageView[4];   //敌人移动的四个路径点
    private int isRun = 0, isRun1 = 0, isRun2 = 0;    //判断是否点击了run
    private ImageView a, b, enemy, role1, role2, role3;
    private ImageView[] fEnemy = new ImageView[1];
    private AnimatorSet[] animatorSet = new AnimatorSet[3];  //属性动画集合
    private Func f = new Func();
    private int hp = 6;
    private Handler mHandRole1 = new Handler();
    private Handler mHandRole2 = new Handler();
    private Handler mHandRole3 = new Handler();

    int[] rolePic = { R.drawable.hp0,R.drawable.hp1,R.drawable.hp2,R.drawable.hp3,R.drawable.hp4,R.drawable.hp5,R.drawable.hp6};

    //攻击手的线程
    private Runnable mRunRole3 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void run() {
            //人物点击监听
            role3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isRun == 0){
                        initPopAtack(v);
                    }
                }
            });
            //判断是否开始运行画面
            if(isRun == 1){
                isRun = 2;
                //启动敌人移动
                long[] time = {3000,3000,3000,3000};
                animatorSet[2] = f.Move(enemy, points, enemySpeed, enemyTimes);
                //获取指令1的移动指令
                if (MoveChoose1[0] != 0) {
                    int count1 = 0;
                    for (int i = 0; i < MoveChoose1.length; i++) {
                        if (MoveChoose1[i] != 0) {
                            count1++;
                        } else {
                            break;
                        }
                    }
                    View[] views = new View[count1];
                    long[] time1 = new long[count1];
                    for (int i = 0; i < views.length; i++) {
                        if (MoveChoose1[i] == 1) {
                            views[i] = a;
                        } else if (MoveChoose1[i] == 2) {
                            views[i] = b;
                        }
                        time1[i] = 3000;
                    }
                    animatorSet[0] = f.Move(role3, views, speed);
                    animatorSet[0].cancel();
                }
                //获取指令2的移动数组
                if (MoveChoose2[0] != 0) {
                    int count2 = 0;
                    for (int i = 0; i < MoveChoose2.length; i++) {
                        if (MoveChoose2[i] != 0) {
                            count2++;
                        } else {
                            break;
                        }
                    }
                    View[] views = new View[count2];
                    long[] time1 = new long[count2];
                    for (int i = 0; i < views.length; i++) {
                        if (MoveChoose2[i] == 1) {
                            views[i] = a;
                        } else if (MoveChoose2[i] == 2) {
                            views[i] = b;
                        }
                        time1[i] = 3000;
                    }
                    animatorSet[1] = f.Move(role3, views, speed);
                    animatorSet[1].cancel();
                }
            }
            if (isRun == 2) {
                //指令1是否有前提条件
                if (signalChoose1 != -1) {
                    //信号量是否正确
                    if (signalArr[signalChoose1] == signalVal1) {
                        //是否有移动
                        if(MoveChoose1[0] != 0 && runType != 1){
                            runType = 1;
                            animatorSet[0].start();
                            if(animatorSet[1] != null){
                                animatorSet[1].cancel();
                            }
                        }
                        //是否有攻击
                        if (attack1 == 1) {
                            ImageView[] fEnemy = {enemy};
                            if (f.FindEnemy(role3, fEnemy, roleAttack) != -1 && hp >= 2) {
                                hp -= 2;
                                enemy.setImageDrawable(getResources().getDrawable(rolePic[hp]));
                            }
                        }
                    }
                    //指令一不满足条件，执行指令2
                    else{
                        //是否有移动
                        if (MoveChoose2[0] != 0 && runType1 == 0) {
                            runType1 = 2;
                            animatorSet[1].start();
                            if(animatorSet[0] != null){
                                animatorSet[0].cancel();
                            }
                        }
                        //是否有攻击
                        if (attack2 == 1) {
                            ImageView[] fEnemy = {enemy};
                            if (f.FindEnemy(role3, fEnemy, roleAttack) != -1 && hp >= 2) {
                                hp -= 2;
                                enemy.setImageDrawable(getResources().getDrawable(rolePic[hp]));
                            }
                        }
                    }
                }
                //指令一没有条件
                else{
                    //指令1是否有操作
                    if(MoveChoose1[0] != 0 || attack1 != 0){
                        //是否有移动
                        if (MoveChoose1[0] != 0 && runType1 == 0) {
                            runType1 = 1;
                            animatorSet[0].start();
                            if(animatorSet[1] != null){
                                animatorSet[1].cancel();
                            }
                        }
                        //是否有攻击
                        if (attack1 == 1) {
                            ImageView[] fEnemy = {enemy};
                            if (f.FindEnemy(role3, fEnemy, roleAttack) != -1 && hp >= 2) {
                                hp -= 2;
                                enemy.setImageDrawable(getResources().getDrawable(rolePic[hp]));
                            }
                        }
                    }
                    //指令1没有操作，执行指令2
                    else if(signalChoose2 != -1){
                        //信号是否正确
                        if(signalArr[signalChoose2] == signalVal2){
                            //是否有移动
                            if(MoveChoose2[0] != 0 && runType != 2){
                                runType = 2;
                                animatorSet[1].start();
                                if(animatorSet[0] != null){
                                    animatorSet[0].cancel();
                                }
                            }
                            //是否有攻击
                            if (attack2 == 1) {
                                if (f.FindEnemy(role3, fEnemy, roleAttack) != -1 && hp >= 2) {
                                    hp -= 2;
                                    enemy.setImageDrawable(getResources().getDrawable(rolePic[hp]));
                                }
                            }
                        }
                    }
                    //指令2没有条件
                    else{
                        //是否有移动
                        if (MoveChoose2[0] != 0 && runType1 == 0) {
                            runType1 = 2;
                            animatorSet[1].start();
                            if(animatorSet[0] != null){
                                animatorSet[0].cancel();
                            }
                        }
                        //是否有攻击
                        if (attack2 == 1) {
                            ImageView[] fEnemy = {enemy};
                            if (f.FindEnemy(role3, fEnemy, roleAttack) != -1 && hp >= 2) {
                                hp -= 2;
                                enemy.setImageDrawable(getResources().getDrawable(rolePic[hp]));
                            }
                        }
                    }
                }
                //当hp为0时，停止运行
                if (hp == 0) {
                    animatorSet[2].pause();
                }
            }
            // 每1秒执行一次
            mHandRole3.postDelayed(mRunRole3, 1000);
        }
    };
    //攻击手点击的弹窗界面设置
    private void initPopAtack(View v){
        View view = LayoutInflater.from(LevelFive.this).inflate(R.layout.level5_attack1, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        Button one = view.findViewById(R.id.ins_one);
        TextView ins1 = view.findViewById(R.id.ins1);
        Button two = view.findViewById(R.id.ins_two);
        TextView ins2 = view.findViewById(R.id.ins2);
        //打印指令1和指令2的参数
        ins1.setText(str1);
        ins2.setText(str2);
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
                initPopIns1(v, 1);
            }
        });
        //指令二
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model1 = 1;
                popupWindow.dismiss();
                initPopIns1(v, 2);
            }
        });
    }
    //攻击手点击的指令1界面设置
    private void initPopIns1(View v, final int id){
        View view = LayoutInflater.from(LevelFive.this).inflate(R.layout.level5_attack2, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        Button signal = view.findViewById(R.id.signal);
        Button attack = view.findViewById(R.id.attack);
        Button move = view.findViewById(R.id.move);
        Button save = view.findViewById(R.id.save);
        Button clear = view.findViewById(R.id.clear);
        final TextView ins = view.findViewById(R.id.ins);
        if(model1 == 1){
            if(id == 1){
                ins.setText(str1);
            }
            else if(id == 2){
                ins.setText(str2);
            }
        }
        else if(model1 == 2){
            if(id == 1){
                ins.setText(str11 + "," + str12 + "," + str13);
            }
            else if(id == 2){
                ins.setText(str21 + "," + str22 + "," + str23);
            }
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
        //设置pop的位置,在v的基础上，偏移x、y
        popupWindow.showAsDropDown(v, 0, 0);
        //返回上一级界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    str11 = "";
                    str12 = "";
                    str13 = "";
                }
                else if(id == 2){
                    str21 = "";
                    str22 = "";
                    str23 = "";
                }
                popupWindow.dismiss();
                initPopAtack(v);
            }
        });
        //选择信号量的功能
        signal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initPopSignal(v, id);
            }
        });
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    str12 = "路径点选择:";
                }
                else if(id == 2){
                    str22 = "路径点选择:";
                }
                popupWindow.dismiss();
                initPopMove(v, id);
            }
        });
        attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    str13 = "攻击";
                    ins.setText(str11 + "," + str12 + "," + str13);
                    attack11 = 1;
                }
                else if(id == 2){
                    str23 = "攻击";
                    ins.setText(str21 + "," + str22 + "," + str23);
                    attack21 = 1;
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ins.setText("");
                if(id == 1){
                    str11 = "";
                    str12 = "";
                    str13 = "";
                }
                else if(id == 2){
                    str21 = "";
                    str22 = "";
                    str23 = "";
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将选择的结果保存
                //str为零说明没有选择
                if(ins.getText() == "") {
                    //清空所有选择
                    if (id == 1) {
                        str1 = "";
                        str11 = "";
                        str12 = "";
                        str13 = "";
                        for (int i = 0; i < MoveChoose1.length; i++) {
                            MoveChoose1[i] = 0;
                        }
                        attack1 = 0;
                        signalChoose1 = -1;
                    } else if (id == 2) {
                        str2 = "";
                        str21 = "";
                        str22 = "";
                        str23 = "";
                        for (int i = 0; i < MoveChoose2.length; i++) {
                            MoveChoose2[i] = 0;
                        }
                        attack2 = 0;
                        signalChoose2 = -1;
                    }
                }
                else{
                    if(id == 1){
                        str1 = str11 + "," + str12 + "," + str13;
                        for(int i = 0; i < MoveChoose1.length; i++){
                            MoveChoose1[i] = move1[i];
                        }
                        attack1 = attack11;
                        signalChoose1 = signalChoose11;
                    }
                    else if(id == 2){
                        str2 = str21 + "," + str22 + "," + str23;
                        for(int i = 0; i < MoveChoose2.length; i++){
                            MoveChoose2[i] = move2[i];
                        }
                        attack2 = attack21;
                        signalChoose2 = signalChoose21;
                    }
                }
                popupWindow.dismiss();
                initPopAtack(v);
            }
        });
    }
    //攻击手移动的界面设置
    private void initPopMove(View v, final int id){
        View view = LayoutInflater.from(LevelFive.this).inflate(R.layout.level5_attackmove, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        Button A = view.findViewById(R.id.A);
        Button B = view.findViewById(R.id.B);
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
                initPopIns1(v, id);
            }
        });
        //不同指令的移动参数
        if(id == 1){
            judge1 = 0;
            count1 = 0;
        } else if (id == 2) {
            judge2 = 0;
            count2 = 0;
        }
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    if(judge1 != 1 && count1 < 10){
                        judge1 = 1;
                        move1[count1++] = 1;   //暂时存放选择过的路径点
                        choose.setText(choose.getText() + "1->");
                    }
                }
                else if(id == 2){
                    if(judge2 != 1 && count2 < 10){
                        judge2 = 1;
                        move2[count2++] = 1;   //暂时存放选择过的路径点
                        choose.setText(choose.getText() + "1->");
                    }
                }
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    if(judge1 != 2 && count1 < 10){
                        judge1 = 2;
                        move1[count1++] = 2;
                        choose.setText(choose.getText() + "2->");
                    }
                }
                else if(id == 2){
                    if(judge2 != 2 && count2 < 10){
                        judge2 = 2;
                        move2[count2++] = 2;
                        choose.setText(choose.getText() + "2->");
                    }
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    str12 += choose.getText().toString();
                }
                else if(id == 2){
                    str22 += choose.getText().toString();
                }
                model1 = 2;
                popupWindow.dismiss();
                initPopIns1(v, id);
            }
        });
    }
    //攻击手点击的识别信号量界面设置
    private void initPopSignal(View v, final int id){
        View view = LayoutInflater.from(LevelFive.this).inflate(R.layout.level5_attacksignal, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        Button signal1 = view.findViewById(R.id.signal1);
        Button signal2 = view.findViewById(R.id.signal2);
        Button signal3 = view.findViewById(R.id.signal3);
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
        //选择信号量1的功能
        signal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    str11 = "识别信号量1:";
                }
                else if(id == 2){
                    str21 = "识别信号量1:";
                }
                popupWindow.dismiss();
                initPopSignalVal(v, id, 0);
            }
        });
        signal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    str11 = "识别信号量2:";
                }
                else if(id == 2){
                    str21 = "识别信号量2:";
                }
                popupWindow.dismiss();
                initPopSignalVal(v, id, 1);
            }
        });
        signal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    str11 = "识别信号量3:";
                }
                else if(id == 2){
                    str21 = "识别信号量3:";
                }
                popupWindow.dismiss();
                initPopSignalVal(v, id, 2);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model1 = 2;
                popupWindow.dismiss();
                initPopIns1(v, id);
            }
        });
    }
    //攻击手点击的信号量的识别界面设置
    private void initPopSignalVal(View v, final int id, final int number){
        View view = LayoutInflater.from(LevelFive.this).inflate(R.layout.level5_attacksignalval, null, false);
        //通过view获取pop界面的组件
        Button yes = view.findViewById(R.id.yes);
        Button no = view.findViewById(R.id.no);
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
        //选择信号量的值
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    str11 += "真";
                    signalChoose11 = number;
                    signalVal1 = true;
                }
                else if(id == 2){
                    str21 += "真";
                    signalChoose21 = number;
                    signalVal2 = true;
                }
                popupWindow.dismiss();
                initPopSignal(v, id);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    str11 += "假";
                    signalChoose11 = number;
                    signalVal1 = false;
                }
                else if(id == 2){
                    str21 += "假";
                    signalChoose21 = number;
                    signalVal2 = false;
                }
                popupWindow.dismiss();
                initPopSignal(v, id);
            }
        });
    }
    //侦查手手的线程
    private Runnable mRunRole1 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void run() {
            //人物点击监听
            role1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isRun1 == 0){
                        initPopInves(v, 1);
                    }
                }
            });
            //判断是否开始运行画面
            if(isRun1 == 1){
                //指令1是否设置条件发现敌人
                if(findEnemy1 == 1){
                    //判断是否发现敌人
                    if(f.FindEnemy(role1, fEnemy, roleFindRange) != -1){
                        //是否设置信号量
                        if(signalSet1 != -1){
                            signalArr[signalSet1] = signalSetVal1;   //设置对应信号量的值
                        }
                    }
                }
                //指令2是否发现敌人
                else if(findEnemy12 == 1){
                    //判断是否发现敌人
                    if(f.FindEnemy(role1, fEnemy, roleFindRange) != -1){
                        //是否设置信号量
                        if(signalSet12 != -1){
                            signalArr[signalSet12] = signalSetVal3;   //设置对应信号量的值
                        }
                    }
                }
                //没有发现敌人
                else{
                    //是否设置信号量
                    if(signalSet1 != -1){
                        signalArr[signalSet1] = signalSetVal1;   //设置对应信号量的值
                    }
                    else if(signalSet2 != -1){
                        signalArr[signalSet12] = signalSetVal3;   //设置对应信号量的值
                    }
                }
            }

            // 每0.1秒执行一次
            mHandRole1.postDelayed(mRunRole1, 100);
        }
    };
    private Runnable mRunRole2 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void run() {
            //人物点击监听
            role2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isRun2 == 0){
                        initPopInves(v, 2);
                    }
                }
            });
            //判断是否开始运行画面
            if(isRun2 == 1){
                //指令1是否设置条件发现敌人
                if(findEnemy2 == 1){
                    //判断是否发现敌人
                    if(f.FindEnemy(role2, fEnemy, roleFindRange) != -1){
                        //是否设置信号量
                        if(signalSet2 != -1){
                            signalArr[signalSet2] = signalSetVal2;   //设置对应信号量的值
                        }
                    }
                }
                //指令2是否发现敌人
                else if(findEnemy22 == 1){
                    //判断是否发现敌人
                    if(f.FindEnemy(role2, fEnemy, roleFindRange) != -1){
                        //是否设置信号量
                        if(signalSet22 != -1){
                            signalArr[signalSet22] = signalSetVal4;   //设置对应信号量的值
                        }
                    }
                }
                //没有发现敌人
                else{
                    //是否设置信号量
                    if(signalSet2 != -1){
                        signalArr[signalSet2] = signalSetVal2;   //设置对应信号量的值
                    }
                    else if(signalSet22 != -1){
                        signalArr[signalSet22] = signalSetVal4;   //设置对应信号量的值
                    }
                }
            }

            // 每0.1秒执行一次
            mHandRole2.postDelayed(mRunRole2, 100);
        }
    };
    //侦查手点击的弹窗界面设置
    private void initPopInves(View v, final int id){
        View view = LayoutInflater.from(LevelFive.this).inflate(R.layout.level5_inves1, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        Button one = view.findViewById(R.id.ins_one);
        TextView ins1 = view.findViewById(R.id.ins1);
        Button two = view.findViewById(R.id.ins_two);
        TextView ins2 = view.findViewById(R.id.ins2);
        //打印侦查手1和侦查手2的参数
        if(id == 1){
            ins1.setText(inves1);
            ins2.setText(inves13);
        }
        else if(id == 2){
            ins1.setText(inves2);
            ins2.setText(inves23);
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
                initPopIns2(v, id, 1);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model1 = 1;
                popupWindow.dismiss();
                initPopIns2(v, id, 2);
            }
        });
    }
    //侦查手点击的指令界面设置
    private void initPopIns2(View v, final int id, final int ins_id){
        View view = LayoutInflater.from(LevelFive.this).inflate(R.layout.level5_inves2, null, false);
        //通过view获取pop界面的组件
        Button find = view.findViewById(R.id.findEnemy);
        final Button setSignal = view.findViewById(R.id.setSignal);
        Button back = view.findViewById(R.id.back);
        Button save = view.findViewById(R.id.save);
        Button clear = view.findViewById(R.id.clear);
        final TextView ins = view.findViewById(R.id.ins);
        if(model1 == 1){
            if(id == 1){
                if(ins_id == 1){
                    ins.setText(inves1);
                }
                else if(ins_id == 2){
                    ins.setText(inves13);
                }
            }
            else if(id == 2){
                if(ins_id == 1){
                    ins.setText(inves2);
                }
                else if(ins_id == 2){
                    ins.setText(inves23);
                }
            }
        }
        else if(model1 == 2){
            if(id == 1){
                if(ins_id == 1){
                    ins.setText(inves11 + "," + inves12);
                }
                else if(ins_id == 2){
                    ins.setText(inves14 + "," + inves15);
                }
            }
            else if(id == 2){
                if(ins_id == 1){
                    ins.setText(inves21 + "," + inves22);
                }
                else if(ins_id == 2){
                    ins.setText(inves24 + "," + inves25);
                }
            }
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
        //设置pop的位置,在v的基础上，偏移x、y
        popupWindow.showAsDropDown(v, 0, 0);
        //返回上一级界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    if(ins_id == 1){
                        inves11 = "";
                        inves12 = "";
                    }
                    else if(ins_id == 2){
                        inves14 = "";
                        inves15 = "";
                    }
                }
                else if(id == 2){
                    if(ins_id == 1){
                        inves21 = "";
                        inves22 = "";
                    }
                    else if(ins_id == 2){
                        inves24 = "";
                        inves25 = "";
                    }
                }
                popupWindow.dismiss();
                initPopInves(v, id);
            }
        });
        //设置信号量的功能
        setSignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initPopSetSignal(v, id, ins_id);
            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    if(ins_id == 1){
                        inves11 = "发现敌人";
                        ins.setText(inves11 + "," + inves12);
                        findEnemy11 = 1;
                    }
                    else if(ins_id == 2){
                        inves14 = "发现敌人";
                        ins.setText(inves14 + "," + inves15);
                        findEnemy13 = 1;
                    }
                }
                else if(id == 2){
                    if(ins_id == 1){
                        inves21 = "发现敌人";
                        ins.setText(inves21 + "," + inves22);
                        findEnemy21 = 1;
                    }
                    else if(ins_id == 2){
                        inves24 = "发现敌人";
                        ins.setText(inves24 + "," + inves25);
                        findEnemy23 = 1;
                    }
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ins.setText("");
                if(id == 1){
                    if(ins_id == 1){
                        inves11 = "";
                        inves12 = "";
                    }
                    else if(ins_id == 2){
                        inves14 = "";
                        inves15 = "";
                    }
                }
                else if(id == 2){
                    if(ins_id == 1){
                        inves21 = "";
                        inves22 = "";
                    }
                    else if(ins_id == 2){
                        inves24 = "";
                        inves25 = "";
                    }
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将选择的结果保存
                //str为零说明没有选择
                if(ins.getText() == "") {
                    //清空所有选择
                    if (id == 1) {
                        if(ins_id == 1){
                            inves1 = "";
                            inves11 = "";
                            inves12 = "";
                            findEnemy1 = 0;
                            signalSet1 = -1;
                        }
                        else if(ins_id == 2){
                            inves13 = "";
                            inves14 = "";
                            inves15 = "";
                            findEnemy12 = 0;
                            signalSet12 = -1;
                        }
                    } else if (id == 2) {
                        if(ins_id == 1){
                            inves2 = "";
                            inves21 = "";
                            inves22 = "";
                            findEnemy2 = 0;
                            signalSet2 = -1;
                        }
                        else if(ins_id == 2){
                            inves23 = "";
                            inves24 = "";
                            inves25 = "";
                            findEnemy22 = 0;
                            signalSet22 = -1;
                        }
                    }
                }
                else{
                    if(id == 1){
                        if(ins_id == 1){
                            inves1 = inves11 + "," + inves12;
                            findEnemy1 = findEnemy11;
                            signalSet1 = signalSet11;
                        }
                        else if(ins_id == 2){
                            inves13 = inves14 + "," + inves15;
                            findEnemy12 = findEnemy13;
                            signalSet12 = signalSet13;
                        }
                    }
                    else if(id == 2){
                        if(ins_id == 1){
                            inves2 = inves21 + "," + inves22;
                            findEnemy2 = findEnemy21;
                            signalSet2 = signalSet21;
                        }
                        else if(ins_id == 2){
                            inves23 = inves24 + "," + inves25;
                            findEnemy22 = findEnemy23;
                            signalSet22 = signalSet23;
                        }
                    }
                }
                popupWindow.dismiss();
                initPopInves(v, id);
            }
        });
    }
    //侦查手点击的设置信号量界面设置
    private void initPopSetSignal(View v, final int id, final int ins_id){
        View view = LayoutInflater.from(LevelFive.this).inflate(R.layout.level5_invessignal, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        Button signal1 = view.findViewById(R.id.signal1);
        Button signal2 = view.findViewById(R.id.signal2);
        Button signal3 = view.findViewById(R.id.signal3);
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
        //选择信号量1的功能
        signal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    if(ins_id == 1){
                        inves12 = "设置信号量1:";
                    }
                    else if(ins_id == 2){
                        inves15 = "设置信号量1:";
                    }
                }
                else if(id == 2){
                    if(ins_id == 1){
                        inves22 = "设置信号量1:";
                    }
                    else if(ins_id == 2){
                        inves25 = "设置信号量1:";
                    }
                }
                popupWindow.dismiss();
                initPopSetSignalVal(v, id, ins_id, 0);
            }
        });
        signal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    if(ins_id == 1){
                        inves12 = "设置信号量2:";
                    }
                    else if(ins_id == 2){
                        inves15 = "设置信号量2:";
                    }
                }
                else if(id == 2){
                    if(ins_id == 1){
                        inves22 = "设置信号量2:";
                    }
                    else if(ins_id == 2){
                        inves25 = "设置信号量2:";
                    }
                }
                popupWindow.dismiss();
                initPopSetSignalVal(v, id, ins_id, 1);
            }
        });
        signal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    if(ins_id == 1){
                        inves12 = "设置信号量3:";
                    }
                    else if(ins_id == 2){
                        inves15 = "设置信号量3:";
                    }
                }
                else if(id == 2){
                    if(ins_id == 1){
                        inves22 = "设置信号量3:";
                    }
                    else if(ins_id == 2){
                        inves25 = "设置信号量3:";
                    }
                }
                popupWindow.dismiss();
                initPopSetSignalVal(v, id, ins_id, 2);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model1 = 2;
                popupWindow.dismiss();
                initPopIns2(v, id, ins_id);
            }
        });
    }
    //侦查手点击的信号量的设置界面设置
    private void initPopSetSignalVal(View v, final int id, final int ins_id, final int number){
        View view = LayoutInflater.from(LevelFive.this).inflate(R.layout.level5_invessignalval, null, false);
        //通过view获取pop界面的组件
        Button yes = view.findViewById(R.id.yes);
        Button no = view.findViewById(R.id.no);
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
        //选择信号量的值
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    if(ins_id == 1){
                        inves12 += "真";
                        signalSet11 = number;
                        signalSetVal1 = true;
                    }
                    else if(ins_id == 2){
                        inves15 += "真";
                        signalSet13 = number;
                        signalSetVal3 = true;
                    }
                }
                else if(id == 2){
                    if(ins_id == 1){
                        inves22 += "真";
                        signalSet21 = number;
                        signalSetVal2 = true;
                    }
                    else if(ins_id == 2){
                        inves25 += "真";
                        signalSet23 = number;
                        signalSetVal4 = true;
                    }
                }
                popupWindow.dismiss();
                initPopSetSignal(v, id, ins_id);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == 1){
                    if(ins_id == 1){
                        inves12 += "假";
                        signalSet11 = number;
                        signalSetVal1 = false;
                    }
                    else if(ins_id == 2){
                        inves15 += "假";
                        signalSet13 = number;
                        signalSetVal3 = false;
                    }
                }
                else if(id == 2){
                    if(ins_id == 1){
                        inves22 += "假";
                        signalSet21 = number;
                        signalSetVal2 = false;
                    }
                    else if(ins_id == 2){
                        inves25 += "假";
                        signalSet23 = number;
                        signalSetVal4 = false;
                    }
                }
                popupWindow.dismiss();
                initPopSetSignal(v, id, ins_id);
            }
        });
    }
    //初始化函数
    private void init(){
        role1 = findViewById(R.id.role1);
        role2 = findViewById(R.id.role2);
        role3 = findViewById(R.id.role3);
        a = findViewById(R.id.A);
        b = findViewById(R.id.B);
        enemy = findViewById(R.id.enemy);
        points[0] = findViewById(R.id.point1);
        points[1] = findViewById(R.id.point2);
        points[2] = findViewById(R.id.point3);
        points[3] = findViewById(R.id.point4);
        fEnemy[0] = enemy;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level5);
        //初始化
        init();
        //启动侦查手的线程
        mHandRole1.post(mRunRole1);
        mHandRole2.post(mRunRole2);
        //启动攻击手的线程
        mHandRole3.post(mRunRole3);
        //启动侦查手的线程

        //判断是否开始移动
        Button run = findViewById(R.id.run);
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //未移动且指令有参数
                if(isRun == 0 && (MoveChoose1[0] != 0 || MoveChoose2[0] != 0
                        || attack1 != 0 || attack2 != 0
                        || signalChoose1 != -1 || signalChoose2 != -1
                        || findEnemy1 != 0 || findEnemy2 != 0
                        || signalSet1 != -1 || signalSet2 != -1)){
                    isRun = 1;
                    isRun1 = 1;
                    isRun2 = 1;
                }
            }
        });
        //重新开始
        Button restart = findViewById(R.id.reset);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelFive.this, LevelFive.class);
                startActivity(intent);
            }
        });
    }

    //销毁所有的线程
    @Override
    protected void onDestroy() {
        //将线程销毁掉
        mHandRole1.removeCallbacks(mRunRole1);
        mHandRole2.removeCallbacks(mRunRole2);
        mHandRole3.removeCallbacks(mRunRole3);
        super.onDestroy();
    }

}

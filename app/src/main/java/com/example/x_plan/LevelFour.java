package com.example.x_plan;


import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import java.util.logging.LogRecord;


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

public class LevelFour extends AppCompatActivity{

    private int roleRun = 0;
    private int count = 0;
    private String str;
    private boolean role1_run = false;
    //private boolean role2_run = false;
    private boolean is_run = false;
    private int[] move1 = new int[10];   //暂时存放移动的参数
    private int[] MoveChoose = new int[10];   //保存移动的数组
    //private int isRun = 0;
    private Handler mHandler = new Handler();
    private int speed = 200;
    private AnimatorSet animatorSet;  //属性动画集合
    //private Handler mHandler_ = new Handler();
//攻击方面
    private double playerroleAttack = 400;   //人物的攻击距离
    private double enemyroleAttack = 300;   //敌人的攻击距离
    private double touch = 0;
    private ImageView a,b,c,enemy,enemy1,role1;
    private ImageView[] fEnemy = new ImageView[1];
    private ImageView[] fEnemy1 = new ImageView[1];
    private ImageView[] frole = new ImageView[1];
    private ImageView[] fc = new ImageView[1];
    private Func f = new Func();
    private int hp = 5;
    private int hp1 = 5;
    private int rolehp = 6;
    private boolean attack1=false;
    private boolean attack2=false;//遇到敌人的三种状态
    private boolean attack3=false;
    int[] enemypic = {R.drawable.deathhp0,R.drawable.deathhp1,R.drawable.deathhp2,R.drawable.deathhp3,R.drawable.deathhp4,R.drawable.deathhp5};
    int[] enemy1pic = {R.drawable.zombiehp0,R.drawable.zombiehp1,R.drawable.zombiehp2,R.drawable.zombiehp3,R.drawable.zombiehp4,R.drawable.zombiehp5};
    int[] rolepic = {R.drawable.hp0,R.drawable.hp1,R.drawable.hp2,R.drawable.hp3,R.drawable.hp4,R.drawable.hp5};


    private Runnable mRunnable = new Runnable() {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("LongLogTag")
        public void run() {


            role1 = findViewById(R.id.role1);
            enemy = findViewById(R.id.enemy);
            enemy1 = findViewById(R.id.enemy1);
            c = findViewById(R.id.C);
            role1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(is_run == false) {
                        initPopWindow2(v);//监听pop2
                    }
                }
            });
            final Func f = new Func();
            if(is_run == true){
                a = findViewById(R.id.A);
                b = findViewById(R.id.B);
                c = findViewById(R.id.C);

                if(roleRun == 0){
                    roleRun = 1;
                    final View[] views = new View[]{a, b,c};
                    final long[] time = new long[]{5000,5000,5000};
                    animatorSet = f.Move(role1, views, time);
                }


                frole[0] =role1;
                fEnemy[0] = enemy;
                fEnemy1[0] = enemy1;
                fc[0] = c;
                if(f.FindEnemy(role1,fc,touch) != -1){
                    //Toast.makeText(LevelFour.this,"胜利", Toast.LENGTH_SHORT).show();//胜利界面
                }

                if(attack1){
                    if(f.FindEnemy(role1,fEnemy,playerroleAttack) !=-1 && rolehp > 0 && hp > 0) {
                        hp -= 1;
                        enemy.setImageDrawable(getResources().getDrawable(enemypic[hp]));
                    }
                    if( f.FindEnemy(role1,fEnemy1,playerroleAttack) !=-1 && rolehp > 0 && hp1 > 0){
                        hp1 -=1;
                        enemy1.setImageDrawable(getResources().getDrawable(enemy1pic[hp1]));
                    }
                    if(rolehp > 0 && hp > 0 && f.FindEnemy(enemy,frole,enemyroleAttack) !=-1){
                        rolehp -= 1;
                        role1.setImageDrawable(getResources().getDrawable(rolepic[rolehp]));
                    }
                    if(rolehp > 0 && hp1 > 0 && f.FindEnemy(enemy1,frole,enemyroleAttack) !=-1){
                        rolehp -= 1;
                        role1.setImageDrawable(getResources().getDrawable(rolepic[rolehp]));
                    }
                    if(rolehp==0){
                        animatorSet.cancel();
                        //失败界面
                    }


                }
                else
                if(attack2){
                    if(f.FindEnemy(role1,fEnemy,playerroleAttack) !=-1 && rolehp > 0 && hp > 0){
                        animatorSet.pause();
                        hp--;
                        enemy.setImageDrawable(getResources().getDrawable(enemypic[hp]));

                    }
                    else if(f.FindEnemy(role1,fEnemy1,playerroleAttack) !=-1 && rolehp > 0 && hp1 > 0){
                        animatorSet.pause();
                        hp1--;
                        enemy1.setImageDrawable(getResources().getDrawable(enemy1pic[hp1]));
                    }
                    else{
                        animatorSet.resume();
                    }
                }
                else{
                    if(rolehp > 0 && hp > 0 && f.FindEnemy(enemy,frole,enemyroleAttack) != -1){
                        rolehp -= 1;
                        role1.setImageDrawable(getResources().getDrawable(rolepic[rolehp]));

                    }
                    if(rolehp > 0 && hp1 > 0 && f.FindEnemy(enemy1,frole,enemyroleAttack) !=-1){
                        rolehp -= 1;
                        role1.setImageDrawable(getResources().getDrawable(rolepic[rolehp]));

                    }
                    if(rolehp==0){
                        animatorSet.cancel();
                        //失败界面
                    }
                }
            }


            mHandler.postDelayed(mRunnable, 300);
        }
    };





    private void initPopWindow2(View v){
        View view = LayoutInflater.from(LevelFour.this).inflate(R.layout.xzxpop2, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        //Button move = view.findViewById(R.id.move);
        Button meet = view.findViewById(R.id.meet);
        Button clear = view.findViewById(R.id.clear);
        //Button save = view.findViewById(R.id.save);
        final TextView choose = view.findViewById(R.id.choose);
        TextView ins1 = view.findViewById(R.id.ins1);
        if(str != ""){
            ins1.setText(str + "\n");
        }
        //构造一个popWindow,参数依次是View、宽、高
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
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
                str = "";
                popupWindow.dismiss();
                //initPopWindow(v);
            }
        });

        meet.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        str = str + "\n遇敌选择:";
                                        popupWindow.dismiss();
                                        initPopWindow4(v);
                                    }
                                }

        );
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                player.setIns_text("",str);
//                player.getInstructions(str).clear();
                str = "";
                popupWindow.dismiss();
                //initPopWindow(v);
            }
        });

    }




    //遇敌pop界面
    private void initPopWindow4(View v){
        View view = LayoutInflater.from(LevelFour.this).inflate(R.layout.xzxpop4, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        Button A = view.findViewById(R.id.A1);
        Button B = view.findViewById(R.id.B1);
        Button C = view.findViewById(R.id.C1);
        //Button save = view.findViewById(R.id.save1);
        final TextView choose = view.findViewById(R.id.choose);
        //构造一个popWindow,参数依次是View、宽、高
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
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
                str = "";
                popupWindow.dismiss();
                initPopWindow2(v);
            }
        });
        //final int[] judge = {0};
        //更改遇到敌人时行动逻辑

        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attack1 = true;
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose.setText(choose.getText() + "停下攻击");
                attack2 = true;
            }
        });
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose.setText(choose.getText() + "移动不攻击");
                attack3 = true;
            }
        });

    }
    /**
     * Called when the activity is first created.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xzxlevel4);

        //init();
        // 通过Handler启动线程
        mHandler.post(mRunnable);  //发送消息，启动线程运行
        //mHandler_.post(mRunnable_);

        Button run = findViewById(R.id.run);
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_run = true;

            }
        });

        Button restart = findViewById(R.id.reset);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelFour.this, LevelFour.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        //将线程销毁掉
        mHandler.removeCallbacks(mRunnable);
        //mHandler_.removeCallbacks(mRunnable_);
        super.onDestroy();
    }
}

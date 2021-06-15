package com.example.x_plan;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.x_plan.layer.MenuLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LevelSix extends AppCompatActivity {
    private String username;
    private ImageView back_ = null;//返回按钮
    private boolean is_finish = false;
    private Button begin=null;//启动
    private ImageView start1,start2;//两个起点
    private ImageView end;//终点
    private Player player1=new Player();//玩家一
    private Player player2=new Player();
    private ImageView[] enemies=null;//敌人集合
    private ImageView[] boss=null;
    private View[] blocks=null;//障碍物集合
    private ImageView A,B,C,D;//地点A\B\C\D
    private TextView msg=null;
    private int enemyDiedCount=0;
    private double count1=0;
    private double count2=0;
    private double[] enemyCount={0,0,0};
    private double bossCount=0;
    private boolean attackFlag = false;
    private boolean ifWin=false;
    private boolean flag=false;
    private boolean ifBossDied=false;
    //攻击半径
    private int player1R=300;
    private int player2R=400;
    private int enemyR=200;
    private int bossR=400;


    private int time=15;
    private int time2=15;

    Func f = new Func();
    private Handler handler1=new Handler();
    private Handler handler2=new Handler();
    private Handler handler3=new Handler();
    final AnimatorSet[] animatorSet = new AnimatorSet[2];
    int[] draw1 = { R.drawable.role1_hp6,R.drawable.role1_hp5,R.drawable.role1_hp4,R.drawable.role1_hp3,R.drawable.role1_hp2,R.drawable.role1_hp1,R.drawable.role1_hp0};
    int[] draw2 = { R.drawable.role2_hp6,R.drawable.role2_hp5,R.drawable.role2_hp4,R.drawable.role2_hp3,R.drawable.role2_hp2,R.drawable.role2_hp1,R.drawable.role2_hp0};
    int[] draw3={ R.drawable.zombiehp5,R.drawable.zombiehp4,R.drawable.zombiehp3,R.drawable.zombiehp2,R.drawable.zombiehp1,R.drawable.zombiehp0};
    int[] draw4={ R.drawable.deathhp5,R.drawable.deathhp4,R.drawable.deathhp3,R.drawable.deathhp2,R.drawable.deathhp1,R.drawable.deathhp0};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level6);
        Intent intent = getIntent();
        this.username = (String)intent.getExtras().get("username");
        init();
        Button restart = findViewById(R.id.reset);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_change = new Intent(LevelSix.this, LevelSix.class);    //切换 Activityanother至MainActivity
                Bundle bundle = new Bundle();// 创建Bundle对象
                bundle.putString("username",username);
                activity_change.putExtras(bundle);
                startActivity(activity_change);//  开始跳转
            }
        });
        back_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CCDirector director = null;

                CCGLSurfaceView ccglSurfaceView=new CCGLSurfaceView(LevelSix.this);
                setContentView(ccglSurfaceView);

                director=CCDirector.sharedDirector();
                director.attachInView(ccglSurfaceView);

                director.setDisplayFPS(true);//显示帧率
                //设置为横屏显示
                director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
                //可以等比例缩放
                director.setScreenSize(480,320);


                //创建一个情景来显示游戏界面
                CCScene ccScene=CCScene.node();
                //将Layer层加到场景中
                try {
                    ccScene.addChild(new MenuLayer(username,LevelSix.this));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //运行场景
                director.runWithScene(ccScene);
            }
        });
        begin.setOnClickListener(new View.OnClickListener(){//启动游戏
            @Override
            public void onClick(View view) {
                if(isSetPath(player1)&&isSetPath(player2)) {
                    msg.setText("");
                    begin.setEnabled(false);
                    player1.player.setEnabled(false);
                    player2.player.setEnabled(false);
                    cal(player1);//解析命令数组
                    cal(player2);//解析命令数组
                    animatorSet[0] = f.Move_(player1.player, player1.views, 200);
                    animatorSet[1] = f.Move_(player2.player, player2.views, 200);
                    handler2.post(player2Runnable);
                    handler1.post(player1Runnable);
                    handler3.post(enemyRunnable);
                }
                else{
                    msg.setText("请先为玩家分配路径");
                }
            }
        });
    }

    private boolean isSetPath(Player player){
        for(int i=0;i<3;i++) {
            try {
                Iterator it = player.getInstructions(i).entrySet().iterator();
                while (it.hasNext()) {
                    java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
                    if (entry.getKey().equals("0")) {//路径选择
                        if(entry.getValue().toString().length()>0)
                            return true;
                    }
                }
            } catch (Exception e) {
            }
        }
        return false;
    }

    private void init(){
        begin=(Button)findViewById(R.id.begin);

        back_ = findViewById(R.id.back_);
        msg=(TextView)findViewById(R.id.msg);

        start1=(ImageView) findViewById(R.id.start1);
        start2=(ImageView) findViewById(R.id.start2);
        end=(ImageView) findViewById(R.id.end);

        A=(ImageView)findViewById(R.id.A);
        B=(ImageView)findViewById(R.id.B);
        C=(ImageView)findViewById(R.id.C);
        D=(ImageView)findViewById(R.id.D);

        player1.player=(ImageView) findViewById(R.id.player1);
        player1.views.add(start1);
        player2.player=(ImageView) findViewById(R.id.player2);
        player2.views.add(start2);


        ImageView enemy1=(ImageView) findViewById(R.id.enemy1);
        ImageView enemy2=(ImageView) findViewById(R.id.enemy2);
        ImageView enemy3=(ImageView) findViewById(R.id.enemy3);
        enemies = new ImageView[]{enemy1,enemy2,enemy3};
        ImageView boss1=(ImageView)findViewById(R.id.boss) ;
        boss=new ImageView[]{boss1};

        Button block1=(Button)findViewById(R.id.block1);
        Button block2=(Button)findViewById(R.id.block2);
        Button block3=(Button)findViewById(R.id.block3);
        Button block4=(Button)findViewById(R.id.block4);
        blocks=new View[]{block1,block2,block3,block4};

        player1.player.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                initPopWindow(view,player1);
            }
        });
        player2.player.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                initPopWindow(view,player2);
            }
        });
    }

    //玩家1线程
    private Runnable player1Runnable = new Runnable(){
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            //被普通怪物攻击，掉血
            if ((f.FindEnemy_(player1.player, enemies, enemyR)) && count1 < draw1.length) {
                attackFlag = true;
                player1.player.setImageDrawable(getResources().getDrawable(draw1[(int)count1]));
                count1=count1+0.25;
            } else {
                attackFlag = false;
            }
            //被boss攻击或碰到障碍物，掉血
            if ((f.FindEnemy_(player1.player, boss, bossR)||f.FindEnemy_(player1.player, blocks, 0)) && count1 < draw1.length) {
                attackFlag = true;
                player1.player.setImageDrawable(getResources().getDrawable(draw1[(int)count1]));
                count1=count1+0.5;
            } else {
                attackFlag = false;
            }
            if (count1 >= draw1.length) {
                animatorSet[0].pause();
                player1.ifDied = true;
                player1.setSignal(true, 0);
                ViewGroup parent = (ViewGroup) player1.player.getParent();
                if (parent != null && parent instanceof ViewGroup) {
                    parent.removeView(player1.player);
                }
            }

            //暂停点
            if(!player1.ifDied) {
                int pausePoint;
                if ((pausePoint = f.arrivePause(player1.player, player1.pauseViews)) != -1) {
                    if (time2 > 0) {
                        animatorSet[0].pause();
                        time2--;
                    } else {
                        player1.pauseViews.remove(pausePoint);
                        animatorSet[0].resume();
                        time2 = 15;
                    }
                }
            }
            if(enemyDiedCount==3){
                player1.setSignal(true, 0);
            }

            handler1.postDelayed(player1Runnable, 300);
        }
    };

    //玩家2线程
    private Runnable player2Runnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            if(f.victory(player2.player,end) && is_finish == false){//到达终点，胜利
                animatorSet[1].end();
                ifWin=true;
                Intent activity_change= new Intent(LevelSix.this, SuccessActivity.class);    //切换 Activity
                Bundle bundle = new Bundle();// 创建Bundle对象
                bundle.putInt("data",6 );//  放入data值为int型
                bundle.putString("username",username);
                activity_change.putExtras(bundle);// 将Bundle对象放入到Intent上
                startActivity(activity_change);//  开始跳转
                is_finish = true;
            }
            if(player2.ifDied && is_finish == false){
                animatorSet[0].end();
                animatorSet[1].end();
                Intent activity_change= new Intent(LevelSix.this, ErrorActivity.class);    //切换 Activity
                Bundle bundle = new Bundle();// 创建Bundle对象
                bundle.putString("username",username);
                bundle.putInt("data",6);
                activity_change.putExtras(bundle);// 将Bundle对象放入到Intent上
                startActivity(activity_change);//  开始跳转
                is_finish = true;
            }
            if (f.FindEnemy_(player2.player, enemies, enemyR)&& count2 < draw2.length) {
                attackFlag = true;
                player2.player.setImageDrawable(getResources().getDrawable(draw2[(int)count2]));
                count2=count2+0.4;
            } else {
                attackFlag = false;
            }
            //被boss攻击或碰到障碍物，掉血
            if ((f.FindEnemy_(player2.player, boss, bossR)||f.FindEnemy_(player2.player, blocks, 0)) && count2 < draw2.length) {
                attackFlag = true;
                player2.player.setImageDrawable(getResources().getDrawable(draw2[(int)count2]));
                count2=count2+0.8;
            } else {
                attackFlag = false;
            }
            if (count2 >= draw2.length) {
                animatorSet[1].end();
                player2.ifDied = true;
            }

            //暂停点
            if(!player2.ifDied) {
                int pausePoint;
                if ((pausePoint = f.arrivePause(player2.player, player2.pauseViews)) != -1) {
                    if (time > 0) {
                        animatorSet[1].pause();
                        time--;
                    } else {
                        player2.pauseViews.remove(pausePoint);
                        animatorSet[1].resume();
                        time = 15;
                    }
                }
            }

            if(player2.ifDied||ifWin){
                animatorSet[1].end();
            }
            else {
                if(!flag){
                    if ((player1.getSignal(0) && player2.getIfSignal())||(!player2.getIfSignal() || !player2.getIfSignal())) {
                        animatorSet[1].resume();
                        flag=true;
                    } else animatorSet[1].pause();
                }

            }
            handler2.postDelayed(player2Runnable, 300);
        }
    };

    //怪物线程
    private Runnable enemyRunnable = new Runnable(){
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            if(enemyDiedCount<3) {
                if (!player1.ifDied) {
                    int i = f.beAttacked(player1.player, enemies, player1R);
                    if (i != -1) {
                        if (enemyCount[i] < draw3.length) {
                            enemies[i].setImageDrawable(getResources().getDrawable(draw3[(int) enemyCount[i]]));
                            enemyCount[i] = enemyCount[i] + 1;
                        } else {
                            enemyDiedCount++;
                            ViewGroup parent = (ViewGroup) enemies[i].getParent();
                            if (parent != null && parent instanceof ViewGroup) {
                                parent.removeView(enemies[i]);
                            }
                            enemies[i] = null;
                        }
                    }
                }
                if (!player2.ifDied) {
                    int j = f.beAttacked(player2.player, enemies, player2R);
                    if (j != -1) {
                        if (enemyCount[j] < draw3.length) {
                            enemies[j].setImageDrawable(getResources().getDrawable(draw3[(int) enemyCount[j]]));
                            enemyCount[j] = enemyCount[j] + 0.5;
                        } else {
                            enemyDiedCount++;
                            ViewGroup parent = (ViewGroup) enemies[j].getParent();
                            if (parent != null && parent instanceof ViewGroup) {
                                parent.removeView(enemies[j]);
                            }
                            enemies[j] = null;
                        }
                    }
                }
            }

            if(!ifBossDied) {
                if (!player1.ifDied) {
                    int i = f.beAttacked(player1.player, boss, player1R);
                    if (i != -1) {
                        if (bossCount < draw4.length) {
                            boss[i].setImageDrawable(getResources().getDrawable(draw4[(int) bossCount]));
                            bossCount = bossCount + 1;
                        } else {
                            ifBossDied = true;
                            ViewGroup parent = (ViewGroup) boss[i].getParent();
                            if (parent != null && parent instanceof ViewGroup) {
                                parent.removeView(boss[i]);
                            }
                            boss[i]=null;
                        }
                    }
                }
                if (!player2.ifDied) {
                    int j = f.beAttacked(player2.player, boss, player2R);
                    if (j != -1) {
                        if (bossCount < draw4.length) {
                            boss[j].setImageDrawable(getResources().getDrawable(draw4[(int) bossCount]));
                            bossCount = bossCount + 0.3;
                        } else {
                            ifBossDied = true;
                            ViewGroup parent = (ViewGroup) boss[j].getParent();
                            if (parent != null && parent instanceof ViewGroup) {
                                parent.removeView(boss[j]);
                            }
                            boss[j]=null;
                        }
                    }
                }
            }
            handler3.postDelayed(enemyRunnable, 200);
        }
    };

    //解析指令
    private void cal(Player player){
        for (int i=0;i<3;i++) {
            try {
                Iterator it = player.getInstructions(i).entrySet().iterator();
                while (it.hasNext()) {
                    java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
                    if(entry.getKey().equals("0")){//路径
                        for(int j=player.views.size()-1;j>0;j--){
                            player.views.remove(j);
                        }
                        for(int j=0;j<entry.getValue().toString().length();j++){
                            switch (String.valueOf(entry.getValue().toString().charAt(j))){
                                case "A":player.views.add(A);break;
                                case "B":player.views.add(B);break;
                                case "C":player.views.add(C);break;
                                case "D":player.views.add(D);break;
                                default:break;
                            }
                        }
                        player.views.add(end);
                    }
                    else if (entry.getKey().equals("1")) {//信号量
                        player.setIfSignal(true);
                    }
                    else if (entry.getKey().equals("2")) {//暂停点
                        for(int j=player.pauseViews.size()-1;j>0;j--){
                            player.pauseViews.remove(j);
                        }
                        for(int j=1;j<=entry.getValue().toString().length();j++){
                            switch (String.valueOf((entry.getValue().toString()).charAt(j-1))){
                                case "A":player.pauseViews.add(A);break;
                                case "B":player.pauseViews.add(B);break;
                                case "C":player.pauseViews.add(C);break;
                                case "D":player.pauseViews.add(D);break;
                                default:break;
                            }
                        }
                    }
                }
            }catch (Exception e){
            }
        }
    }

    private void initPopWindow(View v, final Player player){
        View view= LayoutInflater.from(LevelSix.this).inflate(R.layout.level3_pop1,null,false);
        Button back = view.findViewById(R.id.back);
        //指令1,2,3
        Button one = view.findViewById(R.id.ins_one);
        Button two = view.findViewById(R.id.ins_two);
        Button three = view.findViewById(R.id.ins_three);
        //指令123的内容
        TextView one_text=view.findViewById(R.id.ins_one_text);
        TextView two_text=view.findViewById(R.id.ins_two_text);
        TextView three_text=view.findViewById(R.id.ins_three_text);
        one_text.setText(player.getIns_text(0));
        two_text.setText(player.getIns_text(1));
        three_text.setText(player.getIns_text(2));

        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setAnimationStyle(R.anim.anim_pop);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x80808080));
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
                initPopWindow2(v,0,player);
            }
        });
        two.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initPopWindow2(v,1,player);
            }
        });
        three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initPopWindow2(v,2,player);
            }
        });
    }

    private void initPopWindow2(View v, final int ins, final Player player){
        View view = LayoutInflater.from(LevelSix.this).inflate(R.layout.level6_pop2, null, false);
        //通过view获取pop界面的组件
        Button clear = view.findViewById(R.id.clear);
        Button back=view.findViewById(R.id.back);
        Button move = view.findViewById(R.id.move);
        Button signal=view.findViewById(R.id.signal);
        Button pause=view.findViewById(R.id.pause);
        final TextView ins1 = view.findViewById(R.id.ins1);
        if(player.getIns_text(ins)!= ""){
            ins1.setText(player.getIns_text(ins) + "\n");
        }
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setAnimationStyle(R.anim.anim_pop);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x80808080));
        popupWindow.showAsDropDown(v, 100 - v.getLeft(), -v.getBottom());
        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initPopWindow(v,player);
            }
        });
        //设置路径点
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setIns_text("路径点选择:start->",ins);
                popupWindow.dismiss();
                initPopWindow3(v,ins,player);
            }
        });
        //设置信息量
        signal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setIns_text("已选择的信息量：",ins);
                popupWindow.dismiss();
                initSignalPopWindow(v,ins,player);
            }
        });
        //设置暂停点
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> paths=new ArrayList<>();
                for(int i=0;i<3;i++) {
                    try {
                        Iterator it = player.getInstructions(i).entrySet().iterator();
                        while (it.hasNext()) {
                            java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
                            if (entry.getKey().equals("0")) {//路径选择
                                paths=new ArrayList<>();
                                for (int j = 0; j < entry.getValue().toString().length(); j++) {
                                    paths.add(String.valueOf((entry.getValue().toString()).charAt(j)));
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if(paths.size()>0){
                    player.setIns_text("已选择的暂停点：",ins);
                    popupWindow.dismiss();
                    initPausePopWindow(v,ins,player,paths);
                }
                else {
                    ins1.setText("请先选择路径");
                }
            }
        });
        //清空该指令
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setIns_text("",ins);
                player.getInstructions(ins).clear();
                popupWindow.dismiss();
                initPopWindow(v,player);
            }
        });
    }

    private void initPopWindow3(View v, final int ins, final Player player){
        View view = LayoutInflater.from(LevelSix.this).inflate(R.layout.level6_pop3, null, false);
        Button back = view.findViewById(R.id.back);
        Button save = view.findViewById(R.id.save);
        Button A = view.findViewById(R.id.A);
        Button B = view.findViewById(R.id.B);
        Button C = view.findViewById(R.id.C);
        Button D = view.findViewById(R.id.D);
        final TextView choose = view.findViewById(R.id.choose);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setAnimationStyle(R.anim.anim_pop);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x80808080));
        popupWindow.showAsDropDown(v, 100 - v.getLeft(), -v.getBottom());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initPopWindow2(v,ins,player);
            }
        });
        final String[] path = {""};
        final String[] path_text = {"选择的路径为：start->"};
        final String[] now = {""};
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(now[0] != "A") {
                    path_text[0]+="A->";
                    path[0] += "A";
                    now[0]="A";
                    choose.setText(path_text[0]);
                }
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(now[0] != "B") {
                    path_text[0]+="B->";
                    path[0] += "B";
                    now[0]="B";
                    choose.setText(path_text[0]);
                }
            }
        });
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(now[0] != "C") {
                    path_text[0]+="C->";
                    path[0] += "C";
                    now[0]="C";
                    choose.setText(path_text[0]);
                }
            }
        });
        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(now[0] != "D") {
                    path_text[0]+="D->";
                    path[0] += "D";
                    now[0]="D";
                    choose.setText(path_text[0]);
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path_text[0]+="end";
                player.setIns_text(path_text[0],ins);
                Map<String,String> map=new HashMap<>();
                map.put("0",path[0]);
                player.setInstructions(map,ins);
                popupWindow.dismiss();
                initPopWindow(v,player);
            }
        });
    }

    private void initSignalPopWindow(View v, final int ins, final Player player){
        View view = LayoutInflater.from(LevelSix.this).inflate(R.layout.signal_pop, null, false);
        Button back = view.findViewById(R.id.back);
        Button save = view.findViewById(R.id.save);
        final CheckBox signal1 = view.findViewById(R.id.signal1);
        final TextView choose = view.findViewById(R.id.choose);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setAnimationStyle(R.anim.anim_pop);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        final String[] signals = {""};
        final String[] signals_text = {"已选择的信号量为："};
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x80808080));
        popupWindow.showAsDropDown(v, 100 - v.getLeft(), -v.getBottom());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initPopWindow2(v,ins,player);
            }
        });
        signal1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(signal1.isChecked()){
                    signals_text[0]+=signal1.getText();
                    signals[0] += "1";
                    choose.setText(signals_text[0]);
                }
                else {
                    signals_text[0]=signals_text[0].replaceAll((String) signal1.getText(),"");
                    signals[0] =signals[0].replaceAll( "1","");
                    choose.setText(signals_text[0]);
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setIns_text(signals_text[0],ins);
                Map<String,String> map=new HashMap<>();
                map.put("1",signals[0]);
                player.setInstructions(map,ins);
                popupWindow.dismiss();
                initPopWindow(v,player);
            }
        });
    }

    private void initPausePopWindow(View v, final int ins, final Player player,List<String> paths){
        View view = LayoutInflater.from(LevelSix.this).inflate(R.layout.level6_pause, null, false);
        Button back = view.findViewById(R.id.back);
        Button save = view.findViewById(R.id.save);
        Button A=view.findViewById(R.id.A);
        A.setEnabled(false);
        Button B=view.findViewById(R.id.B);
        B.setEnabled(false);
        Button C=view.findViewById(R.id.C);
        C.setEnabled(false);
        Button D=view.findViewById(R.id.D);
        D.setEnabled(false);
        final TextView choose = view.findViewById(R.id.choose);
        for(int i=0;i<paths.size();i++){
            switch (paths.get(i)){
                case "A":A.setEnabled(true);break;
                case "B":B.setEnabled(true);break;
                case "C":C.setEnabled(true);break;
                case "D":D.setEnabled(true);break;
                default:break;
            }
        }
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setAnimationStyle(R.anim.anim_pop);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        final String[] pauses = {""};
        final String[] pauses_text = {"已选择的暂停点为："};
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x80808080));
        popupWindow.showAsDropDown(v, 100 - v.getLeft(), -v.getBottom());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initPopWindow2(v,ins,player);
            }
        });
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    pauses_text[0]+="A";
                    pauses[0] += "A";
                    choose.setText(pauses_text[0]);
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauses_text[0]+="B";
                pauses[0] += "B";
                choose.setText(pauses_text[0]);
            }
        });
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauses_text[0]+="C";
                pauses[0] += "C";
                choose.setText(pauses_text[0]);
            }
        });
        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauses_text[0]+="D";
                pauses[0] += "D";
                choose.setText(pauses_text[0]);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setIns_text(pauses_text[0],ins);
                Map<String,String> map=new HashMap<>();
                map.put("2",pauses[0]);
                player.setInstructions(map,ins);
                popupWindow.dismiss();
                initPopWindow(v,player);
            }
        });
    }

}

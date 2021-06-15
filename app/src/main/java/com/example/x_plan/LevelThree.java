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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.x_plan.layer.MenuLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LevelThree extends AppCompatActivity{

    private String username;
    private ImageView back_ = null;//返回按钮
    private Button sumbit=null;//确定
    private Button instroduction=null;//游戏说明
    private ImageView start1=null;//起点1
    private ImageView start2=null;//起点2
    private ImageView end=null;//终点
    private ImageView position1=null;//地点1
    private Player player1=new Player();
    private Player player2=new Player();
    private ImageView tower1=null;//塔楼一
    private View[] towers=null;//塔楼集合
    private Handler handler1=new Handler();
    private Handler handler2=new Handler();
    private double count1=0,count2=0;
    private boolean attackFlag = false;
    private boolean ifWin=false;
    private boolean is_finish = false;
    private boolean flag=false;

    final AnimatorSet[] animatorSet = new AnimatorSet[2];
    Func f = new Func();

    int[] draw1 = { R.drawable.role1_hp6,R.drawable.role1_hp5,R.drawable.role1_hp4,R.drawable.role1_hp3,R.drawable.role1_hp2,R.drawable.role1_hp1,R.drawable.role1_hp0};
    int[] draw2 = { R.drawable.role2_hp6,R.drawable.role2_hp5,R.drawable.role2_hp4,R.drawable.role2_hp3,R.drawable.role2_hp2,R.drawable.role2_hp1,R.drawable.role2_hp0};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level3);
        Intent intent = getIntent();
        this.username = (String)intent.getExtras().get("username");
        init();

        Button restart = findViewById(R.id.reset);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_change = new Intent(LevelThree.this, LevelThree.class);    //切换 Activityanother至MainActivity
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

                CCGLSurfaceView ccglSurfaceView=new CCGLSurfaceView(LevelThree.this);
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
                    ccScene.addChild(new MenuLayer(username,LevelThree.this));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //运行场景
                director.runWithScene(ccScene);
            }
        });

        sumbit.setOnClickListener(new View.OnClickListener(){//启动游戏
            @Override
            public void onClick(View view) {
                sumbit.setEnabled(false);
                player1.player.setEnabled(false);
                player2.player.setEnabled(false);
                cal();//解析命令数组
                animatorSet[0] = f.Move_(player1.player,player1.views,100);
                animatorSet[1] = f.Move_(player2.player, player2.views, 250);
                handler2.post(player2Runnable);
                handler1.post(player1Runnable);
            }
        });
        instroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initInstructionWindow(view);
            }
        });
    }

    //解析指令s
    private void cal(){
        for (int i=0;i<3;i++) {
            try {
                Iterator it = player1.getInstructions(i).entrySet().iterator();
                while (it.hasNext()) {
                    java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
                    if (entry.getKey().equals("1")) {//信号量
                        player1.setIfSignal(true);
                    }
                    if(entry.getKey().equals("0")){//路径选择
                        for(int j=player1.views.size()-1;j>0;j--){
                            player1.views.remove(j);
                        }
                        for(int j=0;j<entry.getValue().toString().length();j++){
                            if(((entry.getValue().toString()).charAt(j))=='1') {
                                player1.views.add(position1);
                            }
//                            else if(((entry.getValue().toString()).charAt(j))=='2'){
//                                player1.views.add(position2);
//                            }
                        }
                        player1.views.add(end);
                    }
                }
            }catch (Exception e){ }
            try {
                Iterator it = player2.getInstructions(i).entrySet().iterator();
                while (it.hasNext()) {
                    java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
                    if (entry.getKey().equals("1")) {
                        player2.setIfSignal(true);
                    }
                    if(entry.getKey().equals("0")){
                        for(int j=player2.views.size()-1;j>0;j--){
                            player2.views.remove(j);
                        }
                        for(int j=1;j<=entry.getValue().toString().length();j++){
                            if(((entry.getValue().toString()).charAt(j-1))=='1') {
                                player2.views.add(position1);
                            }
//                            else if(((entry.getValue().toString()).charAt(j-1))=='2'){
//                                player2.views.add(position2);
//                            }
                        }
                        player2.views.add(end);
                    }
                }
            }catch (Exception e){}
        }
    }

    //初始化
    private void init(){
        back_ = findViewById(R.id.back_);
        sumbit=(Button)findViewById(R.id.submit);
        instroduction=(Button)findViewById(R.id.introduction);
        start1=(ImageView)findViewById(R.id.start1);
        start2=(ImageView)findViewById(R.id.start2);
        end=(ImageView) findViewById(R.id.end);
        position1=(ImageView)findViewById(R.id.position1);
        player1.player=(ImageView) findViewById(R.id.player1);
        player1.views.add(start1);
        player1.views.add(position1);
        player1.views.add(end);
        player2.player=(ImageView) findViewById(R.id.player2);
        player2.views.add(start2);
        player2.views.add(position1);
        player2.views.add(end);
        tower1=(ImageView) findViewById(R.id.tower1);
        towers = new View[]{tower1};
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
            if (f.FindEnemy_(player1.player, towers, 400)) {
                player1.setSignal(true, 0);
            }
            if (f.FindEnemy_(player1.player, towers, 200) && count1 < draw1.length && !attackFlag) {
                attackFlag = true;
                player1.player.setImageDrawable(getResources().getDrawable(draw1[(int)count1]));
                count1+=0.5;
            } else {
                attackFlag = false;
            }
            if (count1 >= draw1.length) {
                animatorSet[0].pause();
                player1.ifDied = true;
            }
            handler1.postDelayed(player1Runnable, 200);
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
                Intent activity_change= new Intent(LevelThree.this, SuccessActivity.class);    //切换 Activity
                Bundle bundle = new Bundle();// 创建Bundle对象
                bundle.putInt("data",3 );//  放入data值为int型
                bundle.putString("username",username);
                activity_change.putExtras(bundle);// 将Bundle对象放入到Intent上
                startActivity(activity_change);//  开始跳转
                is_finish = true;
            }

            if(player2.ifDied == true && is_finish==false){
                Intent activity_change= new Intent(LevelThree.this, ErrorActivity.class);    //切换 Activity
                Bundle bundle = new Bundle();// 创建Bundle对象
                bundle.putString("username",username);
                bundle.putInt("data",3);
                activity_change.putExtras(bundle);// 将Bundle对象放入到Intent上
                startActivity(activity_change);//  开始跳转
                is_finish = true;
            }

            if (f.FindEnemy_(player2.player, towers, 200) && count2 < draw2.length && !attackFlag) {
                attackFlag = true;
                player2.player.setImageDrawable(getResources().getDrawable(draw2[(int)count2]));
                count2+=1;
            } else {
                attackFlag = false;
            }
            if (count2 >= draw2.length) {
                animatorSet[1].pause();
                player2.ifDied = true;
            }

            if(player2.ifDied||ifWin){
                animatorSet[1].pause();
            }
            else {
                if(!flag){
                    if ((player1.getSignal(0) && player2.getIfSignal())||(!player2.getIfSignal() || !player2.getIfSignal())) {
                        animatorSet[1].resume();
                        flag=true;
                    } else animatorSet[1].pause();
                }
            }
            handler2.postDelayed(player2Runnable, 200);
        }

    };

    private void initPopWindow(View v, final Player player){
        View view= LayoutInflater.from(LevelThree.this).inflate(R.layout.level3_pop1,null,false);
        Button back  = view.findViewById(R.id.back);
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
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
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
    }

    private void initPopWindow2(View v, final int ins, final Player player){
        View view = LayoutInflater.from(LevelThree.this).inflate(R.layout.level3_pop2, null, false);
        //通过view获取pop界面的组件
        Button clear = view.findViewById(R.id.clear);
        Button back=view.findViewById(R.id.back);
        Button move = view.findViewById(R.id.move);
        Button signal=view.findViewById(R.id.signal);
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
        //清空该指令
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setIns_text("",ins);
                if(player.getInstructions(ins)!=null){
                    player.getInstructions(ins).clear();
                }
                popupWindow.dismiss();
                initPopWindow(v,player);
            }
        });
    }

    private void initPopWindow3(View v, final int ins, final Player player){
        View view = LayoutInflater.from(LevelThree.this).inflate(R.layout.level3_pop3, null, false);
        Button back = view.findViewById(R.id.back);
        Button A = view.findViewById(R.id.A1);
        Button save = view.findViewById(R.id.save1);
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
                if(now[0] != "1") {
                    path_text[0]+="1->";
                    path[0] += "1";
                    now[0]="1";
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

    private void initInstructionWindow(View v){
        View view = LayoutInflater.from(LevelThree.this).inflate(R.layout.level3_introduction, null, false);
        Button back = view.findViewById(R.id.close);
        TextView instruction=view.findViewById(R.id.introduction_text);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setAnimationStyle(R.anim.anim_pop);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) { return false; }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.showAsDropDown(v, 100 - v.getLeft(), -v.getBottom());
        instruction.setText("\n点击玩家进入指令选择界面\n" +
                "-> 信号量：两名玩家同时设置信息量后，玩家一先行出发，识别到塔楼后，发出信息量，玩家二接收到信号量后出发。如果有一方没有设置信息量，则两名玩家同时出发\n" +
                "-> 设置信息量：点击信息量选择按钮即可设置信息量\n" +
                "-> 自由选择路径：路径的起止分别为固定的起点和终点，玩家不可更改。玩家可以选择中间点的顺序，默认路径是起点->1->2->终点\n" +
                "-> 选择路径：进入选择路径界面后，依次点击中间点即可,选择完成后点击保存\n" +
                "-> 取消选择：进入指令详情后，点击清空按钮即可取消选择");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    private void initSignalPopWindow(View v, final int ins, final Player player){
        View view = LayoutInflater.from(LevelThree.this).inflate(R.layout.signal_pop, null, false);
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
}

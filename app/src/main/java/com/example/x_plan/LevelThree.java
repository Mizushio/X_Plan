package com.example.x_plan;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LevelThree extends AppCompatActivity{
    private Button sumbit=null;//确定
    private Button instroduction=null;//游戏说明
    private TextView start=null;//起点
    private TextView end=null;//终点
    private TextView position1=null;//地点1
    private TextView position2=null;//地点2
    private Player player1=new Player();
    private Player player2=new Player();
    private TextView tower1=null;//塔楼一
    private TextView tower2=null;//塔楼二
    private View[] towers=null;//塔楼集合
    private Button block=null;//障碍物
    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fight3);
        init();
        sumbit.setOnClickListener(new View.OnClickListener(){//启动游戏
            @Override
            public void onClick(View view) {
//                sumbit.setEnabled(false);
                cal();//解析命令数组
                Func f=new Func();
                f.Move_(player1.player,player1.views,150);
                Player1Runnable player1Runnable=new Player1Runnable();
                Thread thread=new Thread(player1Runnable);
                thread.start();
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
                            else if(((entry.getValue().toString()).charAt(j))=='2'){
                                player1.views.add(position2);
                            }
//                            player1.time.add((long) 5000);
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
                            else if(((entry.getValue().toString()).charAt(j-1))=='2'){
                                player2.views.add(position2);
                            }
//                            player2.time.add((long) 5000);
                        }
                        player2.views.add(end);
                    }
                }
            }catch (Exception e){}
        }
    }

    //初始化
    private void init(){
        sumbit=(Button)findViewById(R.id.submit);
        instroduction=(Button)findViewById(R.id.introduction);
        block=(Button)findViewById(R.id.block);
        start=(TextView)findViewById(R.id.start);
        end=(TextView) findViewById(R.id.end);
        position1=(TextView)findViewById(R.id.position1);
        position2=(TextView)findViewById(R.id.position2);
        player1.player=(ImageView) findViewById(R.id.player1);
        player1.views.add(start);
        player1.views.add(position1);
        player1.views.add(position2);
        player1.views.add(end);
        player2.player=(ImageView) findViewById(R.id.player2);
        player2.views.add(start);
        player2.views.add(position1);
        player2.views.add(position2);
        player2.views.add(end);
//        for(int i=0;i<5;i++){
//            player1.time.add((long) 5000);
//            player2.time.add((long) 5000);
//        }
        tower1=(TextView)findViewById(R.id.tower1);
        tower2=(TextView)findViewById(R.id.tower2);
        towers = new View[]{tower1, tower2};
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
    class Player1Runnable implements Runnable{
        @Override
        public void run() {
            Func f = new Func();
            while (true) {
                //没有设置信号量,同时前进
                if(!player2.getIfSignal()||!player2.getIfSignal()){
                    handler.post(player2Runnable);
                    break;
                }//设置了信号量，检测到信号量玩家二再前进
                else if (f.FindEnemy_(player1.player,towers,625)) {
                    player1.setSignal(true,0);
                    handler.post(player2Runnable);
                    break;
                }
            }
        }
    }

    //玩家2线程
    private Runnable player2Runnable = new Runnable() {
        @Override
        public void run() {
//            if(player2.getIfSignal()&&player1.getSignal(0)){
                Func f = new Func();
                f.Move_(player2.player, player2.views, 150);
//            }
        }
    };

    private void initPopWindow(View v, final Player player){
        View view= LayoutInflater.from(LevelThree.this).inflate(R.layout.fight3_pop1,null,false);
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

        final PopupWindow popupWindow = new PopupWindow(view, 1600, 1000, true);
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
        View view = LayoutInflater.from(LevelThree.this).inflate(R.layout.fight3_pop2, null, false);
        //通过view获取pop界面的组件
        Button clear = view.findViewById(R.id.clear);
        Button back=view.findViewById(R.id.back);
        Button move = view.findViewById(R.id.move);
        Button signal=view.findViewById(R.id.signal);
        final TextView ins1 = view.findViewById(R.id.ins1);
        if(player.getIns_text(ins)!= ""){
            ins1.setText(player.getIns_text(ins) + "\n");
        }
        final PopupWindow popupWindow = new PopupWindow(view, 1600, 900, true);
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
                player.setIns_text("已设置信号量",ins);
                Map<String,String> map=new HashMap<>();
                map.put("1","0");
                player.setInstructions(map,ins);
                popupWindow.dismiss();
                initPopWindow(v,player);
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
        View view = LayoutInflater.from(LevelThree.this).inflate(R.layout.fight3_pop3, null, false);
        Button back = view.findViewById(R.id.back);
        Button A = view.findViewById(R.id.A1);
        Button B = view.findViewById(R.id.B1);
        Button save = view.findViewById(R.id.save1);
        final TextView choose = view.findViewById(R.id.choose);
        final PopupWindow popupWindow = new PopupWindow(view, 1600, 900, true);
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
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(now[0] != "2"){
                    path_text[0]+="2->";
                    path[0] += "2";
                    now[0]="2";
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
                initPopWindow2(v,ins,player);
            }
        });
    }

    private void initInstructionWindow(View v){
        View view = LayoutInflater.from(LevelThree.this).inflate(R.layout.fight3_introduction, null, false);
        Button back = view.findViewById(R.id.close);
        TextView instruction=view.findViewById(R.id.introduction_text);
        final PopupWindow popupWindow = new PopupWindow(view, 1600, 900, true);
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
}

package com.example.x_plan;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.x_plan.engine.Func;

public class FightActivity extends AppCompatActivity {
    private Spinner ins = null;
    public String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Func f = new Func();
        final ImageView imageButton = findViewById(R.id.role1);
        boolean[] signal = new boolean[9];
        for(int i = 0; i < 9; i++){
            signal[i] = false;
        }
        Button run = findViewById(R.id.run);
        Button a = findViewById(R.id.A);
        Button b = findViewById(R.id.B);
        Button c = findViewById(R.id.C);
        final View[] views = new View[]{a, b, c};
        final long[] time = new long[]{5000,1000,5000};
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Func f = new Func();
                f.Move(imageButton, views, time);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                initPopWindow(v);
            }
        });
    }
    private void initPopWindow(View v){
        View view = LayoutInflater.from(FightActivity.this).inflate(R.layout.level1_pop1, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        Button one = view.findViewById(R.id.ins_one);
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
        View view = LayoutInflater.from(FightActivity.this).inflate(R.layout.level1_pop2, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        Button move = view.findViewById(R.id.move);
        TextView ins = view.findViewById(R.id.ins);
        if(str != ""){
            ins.setText(str + "\n");
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
        View view = LayoutInflater.from(FightActivity.this).inflate(R.layout.level1_pop3, null, false);
        //通过view获取pop界面的组件
        Button back = view.findViewById(R.id.back);
        Button A = view.findViewById(R.id.A);
        Button B = view.findViewById(R.id.B);
        Button C = view.findViewById(R.id.C);
        Button save = view.findViewById(R.id.save);
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
}

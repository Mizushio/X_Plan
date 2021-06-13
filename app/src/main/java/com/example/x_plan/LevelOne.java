package com.example.x_plan;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;

//import org.cocos2d.layers.CCScene;
//import org.cocos2d.nodes.CCDirector;
//import org.cocos2d.opengl.CCGLSurfaceView;

public class LevelOne extends AppCompatActivity {
    private Spinner ins = null;
    public String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ins = findViewById(R.id.instruction);
        ins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String content = parent.getItemAtPosition(position).toString();
                switch(parent.getId()){
                    case R.id.instruction:
                        Toast.makeText(LevelOne.this,"选择的模式:" + content, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Func f = new Func();
        final ImageButton imageButton = findViewById(R.id.catBtn);
//        ImageView[] imageViews = new ImageView[1];
//        imageViews[0] = imageView1;
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
        View view = LayoutInflater.from(LevelOne.this).inflate(R.layout.pop1, null, false);
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
        View view = LayoutInflater.from(LevelOne.this).inflate(R.layout.pop2, null, false);
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
        View view = LayoutInflater.from(LevelOne.this).inflate(R.layout.pop3, null, false);
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
}
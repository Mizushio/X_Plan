package com.example.x_plan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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

        final Func f = new Func();
        final AnimatorSet[] animatorSet = new AnimatorSet[2];
        final ImageView imageButton = findViewById(R.id.catBtn);
//        ImageView[] imageViews = new ImageView[1];
//        imageViews[0] = imageView1;
        boolean[] signal = new boolean[9];
        for(int i = 0; i < 9; i++){
            signal[i] = false;
        }
        Button run = findViewById(R.id.run);
        final Button a = findViewById(R.id.A);
        final Button b = findViewById(R.id.B);
        final ImageView c = findViewById(R.id.C);
        final View[] views = new View[]{c, a , b};
        final long[] time = new long[]{5000,1000,5000};
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatorSet[0] = f.Move(imageButton, views, time);
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Toast.makeText(LevelOne.this, "A", Toast.LENGTH_SHORT).show();
                View[] views1 = new View[]{c, b};
                long[] time1 = new long[]{3000, 3000};
                animatorSet[1] = f.Move(imageButton, views1, time1);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                animatorSet[0].resume();
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f.victory(imageButton, c)){
                    Toast.makeText(LevelOne.this,"胜利", Toast.LENGTH_SHORT).show();
                };
                int[] im = new int[2];
                int[] end = new int[2];
                int[] bpos = new int[2];
                int[] apos = new int[2];
                imageButton.getLocationOnScreen(im);
                c.getLocationOnScreen(end);
                a.getLocationOnScreen(apos);
                b.getLocationOnScreen(bpos);
//                ObjectAnimator animator = ObjectAnimator.ofFloat(imageButton, "translationX", a.getLeft() - im[0]);
//                animator.setDuration(3000);
//                ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageButton, "translationX", 0);
//                animator1.setDuration(3000);
//                AnimatorSet animatorSet1 = new AnimatorSet();
//                animatorSet1.play(animator).before(animator1);
//                animatorSet1.start();

                Toast.makeText(LevelOne.this,"role:" + im[0] + " " +
                        im[1] + "\nc:" + end[0] + " " + end[1]
                        + "\na:" + apos[0] + " " + apos[1]
                        + "\nb:" + bpos[0] + " " + bpos[1], Toast.LENGTH_SHORT).show();
//                Toast.makeText(LevelOne.this,"role:" + im[0] + " " + im[1]
//                        + "\nc:" + c.getLeft() + " " + c.getBottom()
//                        + "\na:" + a.getLeft() + " " + a.getBottom()
//                        + "\nb:" + b.getLeft() + " " + b.getBottom(), Toast.LENGTH_SHORT).show();
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
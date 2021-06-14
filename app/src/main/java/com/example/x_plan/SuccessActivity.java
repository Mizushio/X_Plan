package com.example.x_plan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.x_plan.layer.MenuLayer;
import com.example.x_plan.layer.WelcomeLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

/**
 * Created by hujiahui on 2021/6/14.
 */
public class SuccessActivity extends AppCompatActivity {


    private Button return_,next_;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);
//        Intent intent = getIntent();
//        final int level = (int) intent.getExtras().get("data");
        final int level = 5;


        next_ = findViewById(R.id.next_);
        return_ = findViewById(R.id.return_);

        if(level == 6){next_.setVisibility(View.INVISIBLE);}

        next_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (level){
                    case 1:
                        Intent activity_change1= new Intent(SuccessActivity.this, LevelTwo.class);    //切换 Activityanother至MainActivity
                        Bundle bundle1 = new Bundle();// 创建Bundle对象
                        startActivity(activity_change1);//  开始跳转
                        break;
                    case 2:
                        Intent activity_change2= new Intent(SuccessActivity.this, LevelThree.class);    //切换 Activityanother至MainActivity
                        Bundle bundle2 = new Bundle();// 创建Bundle对象
                        startActivity(activity_change2);//  开始跳转
                        break;
                    case 3:
                        Intent activity_change3= new Intent(SuccessActivity.this, LevelThree.class);    //切换 Activityanother至MainActivity
                        Bundle bundle3 = new Bundle();// 创建Bundle对象
                        startActivity(activity_change3);//  开始跳转
                        break;
                    case 4:
                        Intent activity_change4= new Intent(SuccessActivity.this, LevelThree.class);    //切换 Activityanother至MainActivity
                        Bundle bundle4 = new Bundle();// 创建Bundle对象
                        startActivity(activity_change4);//  开始跳转
                        break;
                    case 5:
                        Intent activity_change5= new Intent(SuccessActivity.this, LevelThree.class);    //切换 Activityanother至MainActivity
                        Bundle bundle5 = new Bundle();// 创建Bundle对象
                        startActivity(activity_change5);//  开始跳转
                        break;

                }
            }
        });

        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CCDirector director = null;

                CCGLSurfaceView ccglSurfaceView=new CCGLSurfaceView(SuccessActivity.this);
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
                ccScene.addChild(new MenuLayer("hjh",SuccessActivity.this));
                //运行场景
                director.runWithScene(ccScene);
                System.out.println("haha");
            }
        });




    }



}

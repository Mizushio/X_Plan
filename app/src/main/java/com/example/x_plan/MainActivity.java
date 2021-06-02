package com.example.x_plan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import com.example.x_plan.layer.WelcomeLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends AppCompatActivity {
    private CCDirector director;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        System.out.println("\n欢迎"+intent.getStringExtra("username")+"\n");

        CCGLSurfaceView ccglSurfaceView=new CCGLSurfaceView(this);
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
        ccScene.addChild(new WelcomeLayer());
        //运行场景
        director.runWithScene(ccScene);
    }

    //恢复游戏运行
    @Override
    protected  void onResume(){
        super.onResume();
        director.resume();
    }
    //暂停
    @Override
    protected void  onPause(){
        super.onPause();
        director.onPause();
    }
    //结束
    @Override
    protected void onDestroy(){
        super.onDestroy();
        director.end();
    }
}

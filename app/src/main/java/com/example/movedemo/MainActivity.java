package com.example.movedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends AppCompatActivity {

    private CCDirector director;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CCGLSurfaceView view = new CCGLSurfaceView(this);//创建一个surfaceView
        setContentView(view);

        director = CCDirector.sharedDirector();//获取导演单例
        director.attachInView(view);//开启绘制线程

        //director.setScreenSize(206,338);//用于屏幕适配

        CCScene scene = CCScene.node();//创建一个场景对象

        //CCLayer layer = CCLayer.node();//创建一个图层对象
        //FirstLayer layer = new FirstLayer();
        ActionLayer layer1 = new ActionLayer();

        //scene.addChild(layer);
        scene.addChild(layer1);
        director.runWithScene(scene);
    }

    @Override
    protected void onResume() {
        super.onResume();
        director.onResume(); //游戏继续
    }

    @Override
    protected void onPause() {
        super.onPause();
        director.onPause();//游戏暂停
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        director.end(); //游戏结束
    }
}
package com.example.x_plan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.x_plan.layer.MenuLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

/**
 * Created by hujiahui on 2021/6/15.
 */
public class ErrorActivity extends AppCompatActivity {
    private int level;
    private String username;
    private Button again;
    private Button return_;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error);
        Intent intent = getIntent();
        this.username = (String)intent.getExtras().get("username");
        this.level = (int)intent.getExtras().get("data");
        again = findViewById(R.id.again);
        return_ = findViewById(R.id.return_);

        again.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                switch (level){
                    case 1:
                        Intent activity_change = new Intent(ErrorActivity.this, LevelOne.class);
                        Bundle bundle = new Bundle();// 创建Bundle对象
                        bundle.putString("username",username);
                        activity_change.putExtras(bundle);
                        startActivity(activity_change);//  开始跳转

                        break;
                    case 2:

                        Intent activity_change1 = new Intent(ErrorActivity.this, LevelTwo.class);
                        Bundle bundle1 = new Bundle();// 创建Bundle对象
                        bundle1.putString("username",username);
                        activity_change1.putExtras(bundle1);
                        startActivity(activity_change1);//  开始跳转
                        break;
                    case 3:
                        Intent activity_change2 = new Intent(ErrorActivity.this, LevelThree.class);
                        Bundle bundle2 = new Bundle();// 创建Bundle对象
                        bundle2.putString("username",username);
                        activity_change2.putExtras(bundle2);
                        startActivity(activity_change2);//  开始跳转
                        break;

                    case 4:
                        Intent activity_change4= new Intent(ErrorActivity.this, LevelFour.class);    //切换 Activityanother至MainActivity
                        Bundle bundle4 = new Bundle();// 创建Bundle对象
                        bundle4.putString("username",username);
                        activity_change4.putExtras(bundle4);
                        startActivity(activity_change4);//  开始跳转
                        break;
                    case 5:
                        Intent activity_change5= new Intent(ErrorActivity.this, LevelFive.class);    //切换 Activityanother至MainActivity
                        Bundle bundle5 = new Bundle();// 创建Bundle对象
                        bundle5.putString("username",username);
                        activity_change5.putExtras(bundle5);
                        startActivity(activity_change5);//  开始跳转
                        break;
                    case 6:
                        Intent activity_change6= new Intent(ErrorActivity.this, LevelSix.class);    //切换 Activityanother至MainActivity
                        Bundle bundle6 = new Bundle();// 创建Bundle对象
                        bundle6.putString("username",username);
                        activity_change6.putExtras(bundle6);
                        startActivity(activity_change6);//  开始跳转
                        break;

                }

            }
        });

        return_.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                CCDirector director = null;

                CCGLSurfaceView ccglSurfaceView=new CCGLSurfaceView(ErrorActivity.this);
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
                    ccScene.addChild(new MenuLayer(username,ErrorActivity.this));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //运行场景
                director.runWithScene(ccScene);
            }
        });

    }
}

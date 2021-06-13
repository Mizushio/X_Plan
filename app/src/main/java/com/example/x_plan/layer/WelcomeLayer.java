package com.example.x_plan.layer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.MotionEvent;


import com.example.x_plan.HelpActivity;
import com.example.x_plan.MainActivity;
import com.example.x_plan.RankActivity;
import com.example.x_plan.SignActivity;
import com.example.x_plan.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import static org.cocos2d.types.CGPoint.ccp;

// TODO: 2021/5/30 添加音效
// TODO: 2021/6/2 登录注册
// TODO: 2021/6/2 排行榜
public class WelcomeLayer extends CCLayer {
    private CCSprite loading;
    private CCSprite start;
    private CCSprite help;
    private CCSprite list;
    private String username;
    private Context context;
    protected CGSize winSize= CCDirector.sharedDirector().winSize();

    public  WelcomeLayer(){}


    public   WelcomeLayer(String username,Context context){
        this.context = context;
        this.username = username;
//        CCSprite welcome=CCSprite.sprite("index/welcome.png");
        CCSprite welcome=CCSprite.sprite("index/background.jpg");
        welcome.setAnchorPoint(ccp(0,0));
        welcome.setScale(0.6f);
       //welcome.setTextureRect();
        this.addChild(welcome);

        //进度条
        loading=CCSprite.sprite("loading/loading_01.png");
        loading.setPosition((winSize.width/2),0);
        loading.setScaleY(0.5f);
        this.addChild(loading);

        //开始游戏按钮
        start=CCSprite.sprite("index/start2.png");
        start.setPosition((winSize.width/2),winSize.height/2);
        start.setScaleX(0.15f);
        start.setScaleY(0.2f);
        this.addChild(start);

        //排行榜
        list=CCSprite.sprite("index/paihangbang.png");
        list.setPosition((winSize.width-50),winSize.height-30);
        list.setScale(0.3f);
        list.setScaleY(0.1f);
        this.addChild(list);

        //帮助按钮
        help=CCSprite.sprite("index/help.png");
        help.setPosition((winSize.width/2),list.getPosition().y-80);
        help.setScale(0.15f);
        help.setScaleY(0.1f);
        this.addChild(help);



        //进度条动画
        CCAction animate= CommonUtils.animate("loading/loading_%02d.png",7,false);
        loading.runAction(animate);

        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try{
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result){
                setIsTouchEnabled(true);
            }
        }.execute();
    }


    @Override
    public boolean ccTouchesBegan(MotionEvent event){
        CGPoint convertTouchNodeSpace=convertTouchToNodeSpace(event);
        if(CGRect.containsPoint(start.getBoundingBox(),convertTouchNodeSpace)){
            CommonUtils.changeLayer(new MenuLayer(username));
        }
         else if (CGRect.containsPoint(help.getBoundingBox(),convertTouchNodeSpace)){
            Intent intent = new Intent(context, HelpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        else if (CGRect.containsPoint(list.getBoundingBox(),convertTouchNodeSpace)){
            Intent intent = new Intent(context, RankActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        return super.ccTouchesBegan(event);
    }
}

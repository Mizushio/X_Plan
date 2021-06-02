package com.example.x_plan.layer;

import android.content.Context;
import android.os.AsyncTask;
import android.view.MotionEvent;


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
    protected CGSize winSize= CCDirector.sharedDirector().winSize();

    public   WelcomeLayer(){
        CCSprite welcome=CCSprite.sprite("index/welcome.png");
        welcome.setAnchorPoint(ccp(0,0));
        welcome.setScale(0.7f);
        this.addChild(welcome);

        //进度条
        loading=CCSprite.sprite("loading/loading_01.png");
        loading.setPosition((winSize.width/2),0);
        loading.setScaleY(0.5f);
        this.addChild(loading);

        //开始游戏按钮
        start=CCSprite.sprite("index/start.png");
        start.setPosition((winSize.width/2),winSize.height/2+70);
        start.setScale(0.15f);
        start.setScaleY(0.1f);
        this.addChild(start);

        //排行榜
        list=CCSprite.sprite("index/list.png");
        list.setPosition((winSize.width/2),start.getPosition().y-80);
        list.setScale(0.15f);
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

    //转到菜单页面
    @Override
    public boolean ccTouchesBegan(MotionEvent event){
        CGPoint convertTouchNodeSpace=convertTouchToNodeSpace(event);
        if(CGRect.containsPoint(start.getBoundingBox(),convertTouchNodeSpace)){
            CommonUtils.changeLayer(new MenuLayer());
        }
        return super.ccTouchesBegan(event);
    }
}

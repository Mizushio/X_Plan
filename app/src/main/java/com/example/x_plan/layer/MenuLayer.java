package com.example.x_plan.layer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;

import com.example.x_plan.LevelFive;
import com.example.x_plan.LevelOne;
import com.example.x_plan.LevelSix;
import com.example.x_plan.LevelThree;
import com.example.x_plan.LevelTwo;
import com.example.x_plan.utils.CommonUtils;
import com.example.x_plan.utils.HttpUtils;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;

public class MenuLayer extends CCLayer {
    protected CGSize winSize= CCDirector.sharedDirector().winSize();

    private int level;
    private Context context;
    private String username;
    private boolean level_ok = false;
    private boolean init_ = false;
    private CCSprite back;

    public MenuLayer(){}

    public void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String resCode;
                    String pamm="{\"username\":\""+username+"\"}";
                    String result=(HttpUtils.sendPost("http://mizushio.top:8080/AppGetdata",pamm));
                    JSONObject jsonObject=new JSONObject(result);
                    resCode=jsonObject.getString("all");

                    if(resCode != null){
                        level = Integer.parseInt(resCode);
                        level_ok = true;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    public MenuLayer(final String username, Context context) throws InterruptedException {
        this.context = context;
        this.username = username;
        init();

        Thread.sleep(1500);

            //加载背景
            CCSprite bg=CCSprite.sprite("menu/menu_bg3.png");
            bg.setAnchorPoint(0,0);
            bg.setScaleX(0.3f);
            bg.setScaleY(0.4f);
            this.addChild(bg);



            CCMenu menu=CCMenu.menu();

            CCSprite game1=CCSprite.sprite("menu/game1.png");
            CCMenuItemSprite itemSprite1=CCMenuItemSprite.item(game1,game1,this,"onClick1");
            itemSprite1.setAnchorPoint(0,0);
            itemSprite1.setScale(0.15f);
            itemSprite1.setPosition(-220,winSize.height/2-130);
            CCSprite game2=CCSprite.sprite("menu/game2.png");
            CCMenuItemSprite itemSprite2=CCMenuItemSprite.item(game2,game2,this,"onClick2");
            itemSprite2.setAnchorPoint(0,0);
            itemSprite2.setScale(0.15f);
            itemSprite2.setPosition(-180,winSize.height/2-200);
            CCSprite game3=CCSprite.sprite("menu/game3.png");
            CCMenuItemSprite itemSprite3=CCMenuItemSprite.item(game3,game3,this,"onClick3");
            itemSprite3.setAnchorPoint(0,0);
            itemSprite3.setScale(0.15f);
            itemSprite3.setPosition(-140,winSize.height/2-130);
            CCSprite game4=CCSprite.sprite("menu/game4.png");
            CCMenuItemSprite itemSprite4=CCMenuItemSprite.item(game4,game4,this,"onClick4");
            itemSprite4.setAnchorPoint(0,0);
            itemSprite4.setScale(0.15f);
            itemSprite4.setPosition(-100,winSize.height/2-200);
            CCSprite game5=CCSprite.sprite("menu/game5.png");
            CCMenuItemSprite itemSprite5=CCMenuItemSprite.item(game5,game5,this,"onClick5");
            itemSprite5.setScale(0.15f);
            itemSprite5.setPosition(-30,winSize.height/2-110);
            CCSprite game6=CCSprite.sprite("menu/game6.png");
            CCMenuItemSprite itemSprite6=CCMenuItemSprite.item(game6,game6,this,"onClick6");
            itemSprite6.setScale(0.15f);
            itemSprite6.setPosition(20,winSize.height/2-173);

            CCMenuItemSprite[] levelArray = new CCMenuItemSprite[6];
            levelArray[0] = itemSprite1;
            levelArray[1] = itemSprite2;
            levelArray[2] = itemSprite3;
            levelArray[3] = itemSprite4;
            levelArray[4] = itemSprite5;
            levelArray[5] = itemSprite6;

            if(level<6) {
                for (int i = 0; i <= level; i++) {
                    menu.addChild(levelArray[i]);
                }
            }else {
                for (int i = 0; i <= 5; i++) {
                    menu.addChild(levelArray[i]);
                }


            }


            this.addChild(menu);

            back=CCSprite.sprite("menu/back.png");
            back.setPosition(20,winSize.height-40);
            back.setScaleX(0.15f);
            back.setScaleY(0.2f);
            this.addChild(back);






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
        if(CGRect.containsPoint(back.getBoundingBox(),convertTouchNodeSpace)){
            CommonUtils.changeLayer(new WelcomeLayer(username,context));
        }
        return super.ccTouchesBegan(event);
    }






    public void onClick1(Object obj){
        Intent intent = new Intent(context, LevelOne.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();// 创建Bundle对象
        bundle.putString("username",username );
        intent.putExtras(bundle);// 将Bundle对象放入到Intent上
        context.startActivity(intent);
    }
    public void onClick2(Object obj){
        Intent intent = new Intent(context, LevelTwo.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();// 创建Bundle对象
        bundle.putString("username",username );
        intent.putExtras(bundle);// 将Bundle对象放入到Intent上
        context.startActivity(intent);
    }
    public void onClick3(Object obj){
        Intent intent = new Intent(context, LevelThree.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();// 创建Bundle对象
        bundle.putString("username",username );
        intent.putExtras(bundle);// 将Bundle对象放入到Intent上
        context.startActivity(intent);
    }
    public void onClick5(Object obj){
        Intent intent = new Intent(context, LevelFive.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();// 创建Bundle对象
        bundle.putString("username",username );
        intent.putExtras(bundle);// 将Bundle对象放入到Intent上
        context.startActivity(intent);
    }
    public void onClick6(Object obj){
        Intent intent = new Intent(context, LevelSix.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();// 创建Bundle对象
        bundle.putString("username",username );
        intent.putExtras(bundle);// 将Bundle对象放入到Intent上
        context.startActivity(intent);
    }




}

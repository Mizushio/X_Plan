package com.example.x_plan.layer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.x_plan.LevelOne;
import com.example.x_plan.RankActivity;
import com.example.x_plan.utils.CommonUtils;
import com.example.x_plan.utils.HttpUtils;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MenuLayer extends CCLayer {
    protected CGSize winSize= CCDirector.sharedDirector().winSize();

    private int level;
    private Context context;

    public MenuLayer(){}

    public MenuLayer(final String username, final Context context){
        this.context = context;
        System.out.println("menu发出的"+username);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String resCode;
                    String utf8Username= URLDecoder.decode(username,"UTF-8");
                    String pamm="{\"username\":\""+utf8Username+"\"}";
                    String result=(HttpUtils.sendPost("http://mizushio.top:8080/AppGetdata",pamm));
                    JSONObject jsonObject=new JSONObject(result);
                    resCode=jsonObject.getString("all");

                    if(resCode != null){
                        level = Integer.parseInt(resCode);
                    }
                    System.out.println(level);


                } catch (JSONException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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
        itemSprite5.setPosition(-60,winSize.height/2-130);

        CCMenuItemSprite[] levelArray = new CCMenuItemSprite[5];
        levelArray[0] = itemSprite1;
        levelArray[1] = itemSprite2;
        levelArray[2] = itemSprite3;
        levelArray[3] = itemSprite4;
        levelArray[4] = itemSprite5;

        for(int i = 0;i <= level;i++) {
            menu.addChild(levelArray[i]);
        }

        this.addChild(menu);
    }

    public void onClick1(Object obj){
        Intent intent = new Intent(context, LevelOne.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }



}

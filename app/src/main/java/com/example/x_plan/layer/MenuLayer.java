package com.example.x_plan.layer;

import android.util.Log;

import com.example.x_plan.utils.CommonUtils;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

public class MenuLayer extends CCLayer {
    protected CGSize winSize= CCDirector.sharedDirector().winSize();

    public MenuLayer(){
        //加载背景
        CCSprite bg=CCSprite.sprite("bg.png");
        bg.setAnchorPoint(0,0);
        this.addChild(bg);

        CCMenu menu=CCMenu.menu();

        CCSprite normalSprite=CCSprite.sprite("menu/PlayButtonBefore.png");
        CCSprite selectedSprite=CCSprite.sprite("menu/PlayButtonAfter.png");
        CCMenuItemSprite itemSprite=CCMenuItemSprite.item(normalSprite,selectedSprite,this,"onClick");

        menu.addChild(itemSprite);
        menu.setScale(0.1f);
        menu.setPosition(0,winSize.height/2-110);

        this.addChild(menu);
    }

    public void onClick(Object obj){
        Log.d("Test","item click...");
        CommonUtils.changeLayer(new FightLayer());
    }



}

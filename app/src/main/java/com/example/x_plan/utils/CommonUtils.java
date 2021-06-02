package com.example.x_plan.utils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

import java.util.ArrayList;
import java.util.HashMap;

import static org.cocos2d.types.CGPoint.ccp;

//动画
public class CommonUtils {
    public static  void changeLayer(CCLayer layer){
        CCScene scene=CCScene.node();
        scene.addChild(layer);
        //淡入淡出
        CCFadeTransition fade=CCFadeTransition.transition(1,scene);
        CCDirector.sharedDirector().replaceScene(fade);
    }

    //动画
    public static CCAction animate(String format,int num,boolean loop){
        return animate(format,num,.2f,loop);
    }

    public static CCAction animate(String format,int num,float t,boolean loop){
        ArrayList<CCSpriteFrame> frames=new ArrayList<CCSpriteFrame>();
        for(int i=1;i<=num;i++)
        {
            CCSpriteFrame frame= CCSprite.sprite(String.format(format,i)).displayedFrame();
            frames.add(frame);
        }

        CCAnimation animation=CCAnimation.animation("loading",t,frames);
        if(loop){
            CCAnimate animate=CCAnimate.action(animation);
            CCRepeatForever repeatForever=CCRepeatForever.action(animate);
            return repeatForever;
        }
        else {
            CCAnimate animate=CCAnimate.action(animation,false);
            return animate;
        }
    }

    public static ArrayList<CGPoint> loadingPoints(String groupName, CCTMXTiledMap map){
        CCTMXObjectGroup objectGroup=map.objectGroupNamed((groupName));
        ArrayList<HashMap<String,String>> objects=objectGroup.objects;
        ArrayList<CGPoint> list=new ArrayList<CGPoint>();
        for(HashMap<String,String> hashMap:objects){
            int x=Integer.parseInt(hashMap.get("x"));
            int y=Integer.parseInt(hashMap.get("y"));
            CGPoint point= ccp(x,y);
            list.add(point);
        }
        return list;
    }
}

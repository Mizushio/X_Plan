package com.example.x_plan.tower;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

public class ShowTower extends CCSprite{
    public ShowTower(){
        super("tower/tower.png");
        this.setScale(0.5f);
        this.setAnchorPoint(CGPoint.ccp(0.5f,0.5f));

        //出场闪烁
//        CCAction animate= CommonUtils.animate("master/master_%02d.png",2,true);
//        this.runAction(animate);


    }


}

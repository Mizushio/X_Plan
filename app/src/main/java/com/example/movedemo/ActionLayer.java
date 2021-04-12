package com.example.movedemo;

import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;

import static org.cocos2d.types.CGPoint.ccp;

/**
 * Created by hujiahui on 2021/4/12.
 */
public class ActionLayer extends CCLayer {

    private CCSprite sprite;
    public ActionLayer(){
        sprite = CCSprite.sprite("cat.jpeg");
        sprite.setAnchorPoint(0,0);

        this.addChild(sprite);

        move();
    }

    /**
     * 人物移动
     */
    public void move(){
        CCMoveTo moveaction = CCMoveTo.action(3,ccp(600, 0));
        sprite.runAction(moveaction);
        //CCMoveTo moveaction2 = CCMoveTo.action(3,ccp(600,500));
        //sprite.runAction(moveaction2);
    }

}

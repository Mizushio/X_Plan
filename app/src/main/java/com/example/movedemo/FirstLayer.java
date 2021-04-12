package com.example.movedemo;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;

import static org.cocos2d.types.CGPoint.ccp;

/**
 * Created by hujiahui on 2021/4/12.
 */
public class FirstLayer extends CCLayer {
    public FirstLayer(){
        CCSprite sprite = CCSprite.sprite("cat.jpeg");

        sprite.setAnchorPoint(0,0);//设置锚点
        sprite.setPosition(ccp(300,0));

        this.addChild(sprite);
    }
}
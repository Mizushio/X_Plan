package com.example.x_plan.base;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import static org.cocos2d.types.CGPoint.ccp;

//子弹
public  class Bullet extends BaseElement{
    protected int attack;//攻击力
    protected int speed;//移动速度

    public Bullet(){
        super("tower/bullet.png");
        setScale(0.65f);
    }

    public int getAttack(){
        return attack;
    }

    public void setAttack(int attack){
        this.attack=attack;
    }

    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int speed){
        this.speed=speed;
    }

    //移动
    // TODO: 2021/5/29 自定义起始点
    public void move(){
        float t=(CCDirector.sharedDirector().winSize().width-getPosition().x)/speed;//移动时间
        CCMoveTo move=CCMoveTo.action(t,ccp(CCDirector.sharedDirector().winSize().width,getPosition().y));
        CCSequence sequence=CCSequence.actions(move, CCCallFunc.action(this,"destroy"));
        runAction(sequence);
    }



}

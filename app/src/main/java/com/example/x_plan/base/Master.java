package com.example.x_plan.base;
import com.example.x_plan.utils.CommonUtils;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;


//怪物基类
// TODO: 2021/5/29 添加中间点
public  class Master extends BaseElement {
    protected int life;//生命力
    protected int speed;//移动速度
    protected CGPoint startPoint;//起点
    protected CGPoint endPoint;//终点

    protected boolean isDieing;//是否死亡


    public Master(CGPoint startPoint,CGPoint endPoint) {
        super("master/master_01.png");
        setScale(0.5f);
        setAnchorPoint(0.5f,0);
        this.startPoint=startPoint;
        this.endPoint=endPoint;
        setPosition(startPoint);
    }

    //移动
    public void move(){
        CCMoveTo moveTo=CCMoveTo.action(CGPointUtil.distance(getPosition(),endPoint)/speed,endPoint);
        CCSequence sequence=CCSequence.actions(moveTo, CCCallFunc.action(this,"destroy"));
        this.runAction(sequence);
    }

    //被攻击
    public  void beAttacked(int attack){
        life-=attack;
        if(life<0&&!isDieing){
            isDieing=true;
            this.stopAllActions();
            CCAnimate animate1=(CCAnimate) CommonUtils.animate("master/master_%02d.png",3,false);
            CCSequence sequence=CCSequence.actions(animate1,CCCallFunc.action(this,"died"));
            this.runAction(sequence);
        }
    }

    public  void died(){
        isDieing=true;
        destroy();
    }



}

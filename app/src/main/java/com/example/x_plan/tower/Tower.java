package com.example.x_plan.tower;


import com.example.x_plan.base.BaseElement;
import com.example.x_plan.base.Bullet;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.cocos2d.types.CGPoint.ccp;

// TODO: 2021/5/29 实现塔楼随目标旋转
public  class Tower extends BaseElement {
    private float x,y;//位置
    protected List<Bullet> bullets=new CopyOnWriteArrayList<Bullet>();//弹夹

    public  Tower(){
        super("tower/tower.png");
        setScale((float) 0.65);
        //将解析的点放在中间
        setAnchorPoint(0.5f,0.5f);
    }

    public float getX(){
        return x;
    }

    public void setX(float x){
        this.x=x;
    }

    public float getY(){
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    //生产用于攻击的子弹
    public  Bullet createBullet(){
        if(bullets.size()<1){
            final Bullet bullet=new Bullet();
            bullet.setPosition(ccp(getPosition().x+25,getPosition().y+40));
            bullet.setDieListener(new DieListener() {
                @Override
                public void die() {
                    bullets.remove(bullet);
                }
            });
            this.getParent().addChild(bullet);
            bullet.move();
            bullets.add(bullet);
            return bullet;
        }
        return null;
    }

    public List<Bullet> getBullets(){
        return bullets;
    }
}

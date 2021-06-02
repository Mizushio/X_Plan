package com.example.x_plan.base;

import org.cocos2d.nodes.CCSprite;

public class BaseElement extends CCSprite {
    public interface DieListener{
        void die();
    }

    //死亡监听
    private DieListener dieListener;

    public void setDieListener(DieListener dieListener){
        this.dieListener=dieListener;
    }

    public BaseElement(String filePath){
        super(filePath);
    }

    public void destroy(){
        if(dieListener!=null)
            dieListener.die();
        removeSelf();
    }
}

package com.example.x_plan.master;

import org.cocos2d.nodes.CCSprite;

public class ShowMaster extends CCSprite {
    private CCSprite bgMaster;
    private CCSprite showMaster;
    private int id;
    public ShowMaster(int i){
        String format="preparation/chose/chose_%02d.png";
        bgMaster=CCSprite.sprite(String.format(format,i));
        showMaster=CCSprite.sprite(String.format(format,i));

        bgMaster.setAnchorPoint(0,0);
        showMaster.setOpacity(100);
        showMaster.setAnchorPoint(0,0);

        id=i;
    }

    public int getId(){
        return id;
    }

    public CCSprite getBgMaster(){
        return bgMaster;
    }

    public CCSprite getShowMaster(){
        return showMaster;
    }
}

package com.example.x_plan.layer;


import android.view.MotionEvent;

import com.example.x_plan.master.ShowMaster;
import com.example.x_plan.tower.ShowTower;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import org.cocos2d.types.CGSize;

import java.util.ArrayList;

import java.util.List;

import static org.cocos2d.types.CGPoint.ccp;

public class FightLayer extends CCLayer {
    private int TAG_SELECTED=0;
    private CCSprite chooseBox;
    private CCSprite playBtn;//开始游戏按钮
    private CCSprite selectedBox;//已被选择的中间点
    private ArrayList<ShowMaster> showMasters;
    private ArrayList<ShowTower> showTowers;//塔楼
    private ArrayList<CGPoint> towerPoints;//塔楼放置处
    private CCSprite bg;
    private ShowMaster master;//怪物
    private List<CCLabel> showRoadPoints;//选择点
    private ArrayList<CGPoint> roadPoints;//选择点放置处
    protected CGSize winSize= CCDirector.sharedDirector().winSize();

    public FightLayer(){
        loadMap();
//        showMasterBox();
        addPoints();
        loadTowers();
        loadChoosePoints();
    }

    private void addPoints(){
        //设置塔楼所在点
        towerPoints=new ArrayList<>();
        addPoint(towerPoints,385,68);
        addPoint(towerPoints,46,94);
        addPoint(towerPoints,93,259);
        addPoint(towerPoints,285,198);

        //设置选择点
        roadPoints=new ArrayList<>();
        addPoint(roadPoints,275,45);
        addPoint(roadPoints,253,241);
        addPoint(roadPoints,97,247);
        addPoint(roadPoints,82,50);
    }

    private void addPoint(ArrayList list,int x,int y)
    {
        CGPoint point= ccp(x,y);
        list.add(point);
    }

    //加载地图
    public void loadMap(){
        try {
            bg =CCSprite.sprite("Fight/bg.png");
            bg.setAnchorPoint(0,0);
            this.addChild(bg);
        }catch (Exception e){
            System.out.println("\nerror\n");
        }
    }

    public void showMasterBox(){
        showChooseBox();
        showSelectPoints();
    }

    private void showChooseBox(){
        chooseBox=CCSprite.sprite("fight_choose.png");
        chooseBox.setAnchorPoint(ccp(0,0));
        this.addChild(chooseBox);

        showMasters=new ArrayList<ShowMaster>();
        for(int i=1;i<=4;i++){
            ShowMaster showMaster=new ShowMaster(i);
            showMaster.getBgMaster().setPosition((i-1)%4*54+16,175-(i-1)/4*59);
            chooseBox.addChild(showMaster.getBgMaster());

            showMaster.getShowMaster().setPosition(showMaster.getBgMaster().getPosition());
            chooseBox.addChild(showMaster.getShowMaster());
            showMasters.add(showMaster);
        }

        playBtn=CCSprite.sprite("Fight/startBtn.png");
        playBtn.setScale(0.1f);
        playBtn.setPosition(ccp(chooseBox.getContentSize().width/2,chooseBox.getContentSize().height));
        chooseBox.addChild(playBtn);
    }

    //显示已被选择的点
    private void showSelectPoints(){
        selectedBox=CCSprite.sprite("Fight/fight_chose.png");
        selectedBox.setAnchorPoint(ccp(0,1));
        selectedBox.setPosition(0,selectedBox.getContentSize().height);
        this.addChild(selectedBox,0,TAG_SELECTED);
    }

    private void loadTowers(){
        showTowers=new ArrayList<ShowTower>();
        for(CGPoint point:towerPoints){
            ShowTower tower=new ShowTower();
            tower.setPosition(point);
            bg.addChild(tower);
            showTowers.add(tower);
        }
    }

    private void loadChoosePoints(){
        showRoadPoints=new ArrayList<>();
        int i=1;
        for(CGPoint point:roadPoints){
            CCLabel number=CCLabel.makeLabel(String.valueOf(i),"hkbd.ttf",15);
            number.setPosition(point);
            bg.addChild(number);
            showRoadPoints.add(number);
            i++;
        }
        setIsTouchEnabled(true);
    }

    private boolean isStart;

    //点击显示坐标
    @Override
    public boolean ccTouchesBegan(MotionEvent event){
        CGPoint convertTouchNodeSpace=convertTouchToNodeSpace(event);
        System.out.println("\n"+convertTouchNodeSpace+"\n");

        //判断是否落在选择框里
//        for(CCLabel number:showRoadPoints)
//            if(CGRect.containsPoint(number.getBoundingBox(),convertTouchNodeSpace)){
//                System.out.printf("select "+String.valueOf(number.getTexture()));
//                showRoadPoints.remove(number);
//            }

//        if(CGRect.containsPoint(playBtn.getBoundingBox(),convertTouchNodeSpace)){
//            gamePrepare();
//        }
        return super.ccTouchesBegan(event);
    }

    //游戏前的准备
//    private void gamePrepare(){
//        setIsTouchEnabled(false);
//    }

    private void gameStart(){



    }


}

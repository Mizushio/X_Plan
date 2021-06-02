package com.example.x_plan.engine;

import com.example.x_plan.tower.ShowTower;
import org.cocos2d.actions.CCScheduler;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameControl {
    public static boolean isStart;
    private CCSprite map;
    private CopyOnWriteArrayList<ShowTower> towers;
    private CopyOnWriteArrayList<CCLabel> roads;

    private static GameControl sInstance=new GameControl();

    public static GameControl getInstance(){
        return sInstance;
    }

    public void gameStart(CCSprite bg, CopyOnWriteArrayList<ShowTower> towerPoint, CopyOnWriteArrayList<CCLabel> roadPoints){
        isStart=true;
        map=bg;
        towers=towerPoint;
        roads=roadPoints;

        CCScheduler scheduler=CCScheduler.sharedScheduler();
        scheduler.schedule("loadTowers",this,2,false);





    }
}

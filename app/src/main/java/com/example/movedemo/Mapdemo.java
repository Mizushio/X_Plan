package com.example.movedemo;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCTMXTiledMap;

/**
 * Created by hujiahui on 2021/4/14.
 */
public class Mapdemo extends CCLayer {

    public Mapdemo(){
        loadMap();
    }

    private void loadMap(){

        CCTMXTiledMap map = CCTMXTiledMap.tiledMap("demo.tmx");
        this.addChild(map);
        }

}

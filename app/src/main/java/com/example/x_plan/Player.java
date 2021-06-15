package com.example.x_plan;

import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {
    public ImageView player=null;
    private boolean ifSignal;
    private boolean[] signal=new boolean[9];//信号量
    private Map<String,String>[] instructions=new Map[3];
    private String[] ins_text={"","",""};
    public List<View> views=new ArrayList<>();
    public List<View> pauseViews=new ArrayList<>();
    public boolean ifDied=false;
//    public List<Long> time=new ArrayList<>();

    public void setIfSignal(boolean ifSignal){
        this.ifSignal=ifSignal;
    }
    public void setInstructions(Map<String,String> map,int i){
        instructions[i]=map;
    }

    public void setSignal(boolean signal,int i){
        this.signal[i]=signal;
    }

    public void setIns_text(String text,int i){
        ins_text[i]=text;
    }

    public boolean getIfSignal(){
        return ifSignal;
    }
    public Map<String,String> getInstructions(int i){
        return instructions[i];
    }

    public boolean getSignal(int i){
        return signal[i];
    }

    public String getIns_text(int i){
        return ins_text[i];
    }


}

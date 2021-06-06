package com.example.x_plan;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.x_plan.utils.HttpUtils;

import org.json.JSONObject;

/**
 *  展示排行榜内容
 */
//todo：整的稍微好看点，字体之类的什么的）
public class RankActivity extends AppCompatActivity {

    private TextView RankingText;
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking);
        init();
    }



    private void init(){
        //todo:后端排行榜还没打，马上就去补上
        RankingText=findViewById(R.id.helpText);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String rank="";
                    //本来get就行的。随便post点东西，都一样）
                    String pamm="{\"username\":\""+"get"+"\",\"password\":\""+"passwordText"+"\"}";
                    String result=(HttpUtils.sendPost("http://mizushio.top:8080/GetRank",pamm));
                    JSONObject jsonObject=new JSONObject(result);
                    //回头直接返回string类型得了，json都不包了
                    //{"rank":"包装好的string"}
                    rank=jsonObject.getString("rank");
                    RankingText.setText(rank);
                } catch (Exception e) {
                    e.printStackTrace();
                    RankingText.setText("获取失败");
                }
            }
        }).start();

    }

}

package com.example.x_plan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.TimerTask;

public class SpinnerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner=null;
    private Spinner spinner2=null;
    private TextView text=null;
    private Button sumbit=null;
    private String t1="";
    private String t2="";
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinnner);
        spinner=(Spinner)findViewById(R.id.spinner);
        spinner2=(Spinner)findViewById(R.id.spinner2);
        text=(TextView) findViewById(R.id.text);
        sumbit=(Button)findViewById(R.id.submit);
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner2.setEnabled(false);
        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText("if "+t1+" then "+t2);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String content=parent.getItemAtPosition(i).toString();
        switch (parent.getId()){
            case R.id.spinner:
                spinner2.setEnabled(true);
                if(content.equals("C++")){
                    String[] arr={"1","2"};
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arr);
                    spinner2.setAdapter(adapter);
                    t1="";
                    t1+=content;
                }
                Toast.makeText(SpinnerActivity.this, "选择的专业是：" + content, Toast.LENGTH_SHORT).show();
                break;
            case  R.id.spinner2:
                t2="";
                t2+=content;
                Toast.makeText(SpinnerActivity.this, "选择的是：" + content, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

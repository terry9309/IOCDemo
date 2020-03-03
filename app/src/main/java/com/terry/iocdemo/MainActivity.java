package com.terry.iocdemo;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


//需要标识哪个布局文件；

@ContentView(R.layout.activity_main)

//此处性能较差；

public class MainActivity extends BaseActivity {

    @ViewInject(R.id.btn1)
    Button btn1;

    @ViewInject(R.id.btn2)
    Button btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       btn1.setText("BUTTON1");
       btn2.setText("BUTTON2");
    }

    @OnClick({R.id.btn1,R.id.btn2})
    public void  onclick(View view){
        Toast.makeText(this,"按下了",Toast.LENGTH_SHORT).show();
    }


    @OnLongClick({R.id.btn2})
    public boolean  onLongClick(View view){
        Toast.makeText(this,"长按下了",Toast.LENGTH_SHORT).show();
        return  false;
    }
}

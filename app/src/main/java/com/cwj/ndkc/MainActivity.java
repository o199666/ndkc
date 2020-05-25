package com.cwj.ndkc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnJava, btnC,btnMT,btnYL;
    private TextView tvJava, tvC;
    JNI jni=new JNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnJava = findViewById(R.id.btn_java);
        btnC = findViewById(R.id.btn_c);
        btnMT = findViewById(R.id.btn_mt);
        btnYL = findViewById(R.id.btn_yali);
        tvJava = findViewById(R.id.tv_java);
        tvC = findViewById(R.id.tv_c);
        btnJava.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnYL.setOnClickListener(this);
        btnMT.setOnClickListener(this);

        // Example of a call to a native method
        String text = new JNI().stringFromJNI();
    }
    int [] ints={1,2,3,4,5};
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_java:
                //java调用C
                jni.strOrStr("参数");


//                 String text = new JNI().strOrStr("我是java 中的String ||");
                int [] intss1=new JNI().getArray(ints);
                for (int i = 0; i <intss1.length ; i++) {
                    Log.e("Array=---",intss1[i]+"");
                }
                break;
            case R.id.btn_c:
                //C调用java
//                jni.callAdd();//C
//                jni.javaStringCall();//C
                 MainActivity.this.showToastCall();
                break;
                case R.id.btn_mt:
                    Intent intent=new Intent( this,MTActivity.class);
                    startActivity(intent);
                break;
                case R.id.btn_yali:
                    Intent inten1t=new Intent(MainActivity.this,YLActivity.class);
                    startActivity(inten1t);
                break;
            default:
                break;

        }
    }
    public  native void showToastCall();

    public void  showToast(){
        Toast.makeText(this, "C++调用我了", Toast.LENGTH_SHORT).show();
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */


}

package com.cwj.ndkc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mt.mtxx.image.JNI;

/**
 * Created by CWJ on 2020/5/23.
 * Author:Chen
 * Email:1181620038@qq.com
 * Ver:1
 * DEC:
 */
public class MTActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn1, btn2, btn3, btn4;
    private ImageView image;
    private JNI jni;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mt);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        image=findViewById(R.id.img);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        jni=new JNI();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                lomoHDR();
                break;
            case R.id.btn2:
                lomoB();
                break;
            case R.id.btn3:
                lomoC();
                break;
            case R.id.btn4:
                reset();
                break;
        }
    }

    public void lomoHDR( ){
        //6.1，把图片转换成数组
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ab);
        //装图片的像数
        int[] pixels = new int[bitmap.getWidth()*bitmap.getHeight()];
        /**
         * 参数

         pixels       接收位图颜色值的数组

         offset      写入到pixels[]中的第一个像素索引值

         stride       pixels[]中的行间距个数值(必须大于等于位图宽度)。可以为负数

         x             从位图中读取的第一个像素的x坐标值。

         y             从位图中读取的第一个像素的y坐标值

         width       从每一行中读取的像素宽度

         height 　　读取的行数

         　　异常
         */
        bitmap.getPixels(pixels,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
        //6.2,把数组传入给C代码处理
        jni.StyleLomoHDR(pixels,bitmap.getWidth(),bitmap.getHeight());
        // 6.3，把处理好的数组重新生成图片
        bitmap =  Bitmap.createBitmap(pixels,bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        // 6.4,把图片像数
        image.setImageBitmap(bitmap);


    }

    public void lomoC( ){
        //6.1，把图片转换成数组
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ab);
        //装图片的像数
        int[] pixels = new int[bitmap.getWidth()*bitmap.getHeight()];
        bitmap.getPixels(pixels,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
        //6.2,把数组传入给C代码处理
        jni.StyleLomoC(pixels, bitmap.getWidth(), bitmap.getHeight());
        // 6.3，把处理好的数组重新生成图片
        bitmap =  Bitmap.createBitmap(pixels,bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        // 6.4,把图片像数
        image.setImageBitmap(bitmap);

    }

    public void lomoB(){
        //6.1，把图片转换成数组
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ab);
        //装图片的像数
        int[] pixels = new int[bitmap.getWidth()*bitmap.getHeight()];
        bitmap.getPixels(pixels,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
        //6.2,把数组传入给C代码处理
        jni.StyleLomoB(pixels,bitmap.getWidth(),bitmap.getHeight());
        // 6.3，把处理好的数组重新生成图片
        bitmap =  Bitmap.createBitmap(pixels,bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        // 6.4,把图片像数
        image.setImageBitmap(bitmap);
    }
    public void reset(){
        image.setImageResource(R.drawable.ab);
    }
}

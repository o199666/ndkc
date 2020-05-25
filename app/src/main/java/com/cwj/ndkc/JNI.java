package com.cwj.ndkc;

import android.util.Log;

import java.lang.reflect.Array;

import static android.content.ContentValues.TAG;

/**
 * Created by CWJ on 2020/5/22.
 * Author:Chen
 * Email:1181620038@qq.com
 * Ver:1
 * DEC:
 */
public class JNI {
    //加载本地C语言文件库。库名字为你写的C语言文件名
    static {
        System.loadLibrary("Hello");
    }


    //todo: java 调用 C =======
    public native String stringFromJNI();
//相加
    public native  int numberTest(int a,int b);

//字符串拼接 java 和 C
    public native String strOrStr(String str);

 // 在C中数组中的每一个元素+10 然后返回到 java;
    public native int [] getArray(int array[]);





    //todo: C 调用java=======================================================
    public int  addJava(int x,int y){
        Log.e(TAG, "addJava() x=" + x + " y=" + y);
        return x+y;
    }
    public native void callAdd();


    //todo:C  c调用静态java方法
     public static void javaString(String jstr){
         Log.e(TAG, "javaString()  "  +jstr);

     }
     public native void javaStringCall();
//c 调用 activity中的toast

    public  native void showToastCall();
}

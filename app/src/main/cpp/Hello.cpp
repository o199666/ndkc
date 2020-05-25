//
// Created by Administrator on 2020/5/22.
//
#include <jni.h>
#include <string>
#include<android/log.h>
#define  LOG_TAG    "nativeprint"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGD(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__

// java String 转成C char
char *jstringTostring(JNIEnv *env, jstring jstr);

#pragma clang diagnostic push
#pragma ide diagnostic ignored "err_typecheck_member_reference_arrow"
extern "C" JNIEXPORT jstring JNICALL
Java_com_cwj_ndkc_JNI_stringFromJNI(JNIEnv *env, jobject) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT jint JNICALL
Java_com_cwj_ndkc_JNI_numberTest(JNIEnv *env, jobject thiz, jint a, jint b) {
    // TODO: implement numberTest()
    int result = a + b;
    return result;
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_cwj_ndkc_JNI_strOrStr(JNIEnv *env, jobject thiz, jstring str) {
    // TODO: implement strOrStr()
    char *javas = jstringTostring(env, str);
    char *cc = "我是C语言中的char*";
    strcat(javas, cc);
//    return (*env)->NewStringUTF(env,javas);
    return env->NewStringUTF((char *) javas);
}

//char* 转成String
jstring stoJstring(JNIEnv *env, const char *pat) {
    jclass strClass = env->FindClass("java/lang/String");
    jmethodID ctorID = env->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
    jbyteArray bytes = env->NewByteArray(strlen(pat));
    env->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte *) pat);
    jstring encoding = env->NewStringUTF("utf-8");
    return (jstring) env->NewObject(strClass, ctorID, bytes, encoding);
}
// java String 转成C char
char *jstringTostring(JNIEnv *env, jstring jstr) {
    char *rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("utf-8");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid, strencode);
    jsize alen = env->GetArrayLength(barr);
    jbyte *ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) malloc(alen + 1);
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
}

extern "C"
JNIEXPORT jintArray JNICALL
Java_com_cwj_ndkc_JNI_getArray(JNIEnv *env, jobject thiz, jintArray array) {
    // TODO: implement getArray()
    //1, 得到数组的长度
    int lenght = env->GetArrayLength(array);
    //2,得到数组的元素
    jint *intArray = env->GetIntArrayElements(array,0);
    //3,遍历数组顺便加10
    int i;
    for (i = 0; i < lenght; ++i) {
        *intArray+=10;
    }
    return array;
}
//=============================================todo:===================== C调用java
extern "C"
JNIEXPORT void JNICALL
Java_com_cwj_ndkc_JNI_callAdd(JNIEnv *env, jobject thiz) {
    // TODO: implement callAdd()
    //要用到反射
    //1,得到字节码
    jclass  jclass1=env->FindClass("com/cwj/ndkc/JNI");
    //2,得到方法
    jmethodID jmethodId=env->GetMethodID(jclass1,"addJava","(II)I");
    //3,实例化该类
    jobject  jobject=env->AllocObject(jclass1);
    //4,调用方法,得到结果
   jint jint1= env->CallIntMethod(jobject,jmethodId,99,1);
}
//调用静态java方法
extern "C"
JNIEXPORT void JNICALL
Java_com_cwj_ndkc_JNI_javaStringCall(JNIEnv *env, jobject thiz) {
    // TODO: implement javaStringCall()
        //1，得到字节码
            jclass jclass1=env->FindClass("com/cwj/ndkc/JNI");
        //2.得到方法 字节码，方法名，方法签名
            jmethodID jmethodId=env->GetStaticMethodID(jclass1,"javaString","(Ljava/lang/String;)V");
        //3，调用方法
            jstring jst=env->NewStringUTF("这个是java静态方法参数");
            env -> CallStaticVoidMethod(jclass1,jmethodId,jst);
            printf("日志：=======");
            __android_log_print(ANDROID_LOG_INFO,"日志",  "aa");
}extern "C"
JNIEXPORT void JNICALL
/**
 *
 * @param env
 * @param thiz 这个是错误的，因为Activity 不同于 类，有上下文的，所以这个来调用toast是失败的。
 */
Java_com_cwj_ndkc_JNI_showToastCall(JNIEnv *env, jobject thiz) {
    //要用到反射
    //1,得到字节码
    jclass  jclass1=env->FindClass("com/cwj/ndkc/MainActivity");
    //2,得到方法
    jmethodID jmethodId=env->GetMethodID(jclass1,"showToast","()V");
    //3,实例化该类
    jobject  jobject=env->AllocObject(jclass1);
    //4,调用方法,得到结果
    env->CallVoidMethod(jobject,jmethodId);

}

extern "C"
JNIEXPORT void JNICALL
/**
 *
 * @param env
 * @param thiz 这个代表的Activity  ，不需要实例化
 */
Java_com_cwj_ndkc_MainActivity_showToastCall(JNIEnv *env, jobject thiz) {
    // TODO: implement showToastCall()
    //要用到反射
    //1,得到字节码
    jclass  jclass1=env->FindClass("com/cwj/ndkc/MainActivity");
    //2,得到方法
    jmethodID jmethodId=env->GetMethodID(jclass1,"showToast","()V");
    //3,实例化该类
//    jobject  jobject=env->AllocObject(jclass1);
    //4,调用方法,得到结果
    env->CallVoidMethod(thiz,jmethodId);
}
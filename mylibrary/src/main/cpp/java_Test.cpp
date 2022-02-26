#include <jni.h>
#include "java_Test.h"
#include "test.h"
#include "log.h"
/*
 * Method:    test1
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_zw_mylibrary_Test_test1
(JNIEnv *env, jclass jclazz){
    test1();
    LOGI("java test1");
}

/*
 * Method:    test2
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_zw_mylibrary_Test_test2
        (JNIEnv *env, jclass jclazz, jint jint_a, jint jint_b){
    test2(1,2);
    LOGI("java test2");
    return 1;
}

/*
 * Method:    test3
 * Signature: ([II)V
 */
JNIEXPORT void JNICALL Java_com_zw_mylibrary_Test_test3
(JNIEnv *env, jclass jclazz, jintArray jint_arr, jint jlen){
    int arr[3] = {1,2,3};
    test3(arr,3);
    LOGI("java test3");
}

/*
 * Method:    test4
 * Signature: ([I)V
 */
JNIEXPORT void JNICALL Java_com_zw_mylibrary_Test_test4
(JNIEnv *env, jclass jclazz, jintArray jarr){
    int arr[3] = {1,2,3};
    test4(arr);
    LOGI("java test4");
}

/*
 * Method:    test5
 * Signature: (C)V
 */
JNIEXPORT void JNICALL Java_com_zw_mylibrary_Test_test5
(JNIEnv *env, jclass jclazz, jchar jch){
    test5('1');
    LOGI("java test5");
}

/*
 * Method:    test6
 * Signature: ([CI)V
 */
JNIEXPORT void JNICALL Java_com_zw_mylibrary_Test_test6
(JNIEnv *env, jclass jclazz, jcharArray jch_arr, jint jint_len){
    test6(0,0);
    LOGI("java test6");
}



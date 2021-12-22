/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_zw_new_demo1_util_NativeFileLogger */

#ifndef _Included_com_zw_new_demo1_util_NativeFileLogger
#define _Included_com_zw_new_demo1_util_NativeFileLogger
#ifdef __cplusplus
extern "C" {
#endif
#undef com_zw_new_demo1_util_NativeFileLogger_ONE_DAY
#define com_zw_new_demo1_util_NativeFileLogger_ONE_DAY 86400000LL
#undef com_zw_new_demo1_util_NativeFileLogger_MAX_SIZE
#define com_zw_new_demo1_util_NativeFileLogger_MAX_SIZE 16777216LL
#undef com_zw_new_demo1_util_NativeFileLogger_FLUSH_WHAT
#define com_zw_new_demo1_util_NativeFileLogger_FLUSH_WHAT 1024L
/*
 * Class:     com_zw_new_demo1_util_NativeFileLogger
 * Method:    native_init
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_zw_new_1demo1_util_NativeFileLogger_native_1init
        (JNIEnv *, jobject, jstring);

/*
 * Class:     com_zw_new_demo1_util_NativeFileLogger
 * Method:    native_write
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_zw_new_1demo1_util_NativeFileLogger_native_1write
(JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     com_zw_new_demo1_util_NativeFileLogger
 * Method:    native_flush
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_zw_new_1demo1_util_NativeFileLogger_native_1flush
(JNIEnv *, jobject, jlong);

/*
 * Class:     com_zw_new_demo1_util_NativeFileLogger
 * Method:    native_close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_zw_new_1demo1_util_NativeFileLogger_native_1close
(JNIEnv *, jobject, jlong);

/*
 * Class:     com_zw_new_demo1_util_NativeFileLogger
 * Method:    native_data
 * Signature: (J[B)V
 */
JNIEXPORT void JNICALL Java_com_zw_new_1demo1_util_NativeFileLogger_native_1data
(JNIEnv *, jobject, jlong, jbyteArray);

#ifdef __cplusplus
}
#endif
#endif

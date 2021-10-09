#include "java_NativeFileLogger.h"
#include "FileLogger.h"

/*
 * Class:     com_zw_new_demo1_util_NativeFileLogger
 * Method:    native_init
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_zw_new_1demo1_util_NativeFileLogger_native_1init
        (JNIEnv *env, jobject jobj, jstring jpath) {
    const char *path = env->GetStringUTFChars(jpath, nullptr);
    FileLogger *fileLogger = new FileLogger(path);
    env->ReleaseStringUTFChars(jpath, path);
    return reinterpret_cast<jlong>(fileLogger);
}

/*
 * Class:     com_zw_new_demo1_util_NativeFileLogger
 * Method:    native_read
 * Signature: (J)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_zw_new_1demo1_util_NativeFileLogger_native_1read
        (JNIEnv *env, jobject jobj, jlong jinstance) {

}

/*
 * Class:     com_zw_new_demo1_util_NativeFileLogger
 * Method:    native_write
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_zw_new_1demo1_util_NativeFileLogger_native_1write
        (JNIEnv *env, jobject jobj, jlong jinstance, jstring jstr) {
    FileLogger *fileLogger = reinterpret_cast<FileLogger *>(jinstance);
    const char *d = env->GetStringUTFChars(jstr, nullptr);
    int n = env->GetStringUTFLength(jstr);
    fileLogger->write((uint8_t *) d, n);
    env->ReleaseStringUTFChars(jstr, d);
}

/*
 * Class:     com_zw_new_demo1_util_NativeFileLogger
 * Method:    native_close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_zw_new_1demo1_util_NativeFileLogger_native_1close
        (JNIEnv *env, jobject jobj, jlong jinstance) {
    FileLogger *fileLogger = reinterpret_cast<FileLogger *>(jinstance);
    fileLogger->close();
}

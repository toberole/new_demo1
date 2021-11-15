#include "java_MemTest.h"
#include <string>
#include "log.h"
/*
 * Class:     com_zw_new_demo1_native_demo_MemTest
 * Method:    native_test1
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_zw_new_1demo1_native_1demo_MemTest_native_1test1
        (JNIEnv *env, jclass jclazz, jint jsize) {
    LOGI("malloc size: %d", jsize);
    int32_t *p = static_cast<int32_t *>(malloc(sizeof(int32_t) * jsize));
    if (!p) {
        LOGE("malloc fail......");
        return;
    }
    memset(p, 1, jsize);
}


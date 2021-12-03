#include "java_HttpServer.h"

/*
 * Class:     com_zw_server_HttpServer
 * Method:    native_start
 * Signature: (ILcom/zw/server/HttpServer/HttpServerCallback;)J
 */
JNIEXPORT jlong JNICALL Java_com_zw_server_HttpServer_native_1start
        (JNIEnv *, jobject, jint, jobject) {
    return 0;
}


/*
 * Class:     com_zw_server_HttpServer
 * Method:    native_stop
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_zw_server_HttpServer_native_1stop
        (JNIEnv *, jobject, jlong){

}


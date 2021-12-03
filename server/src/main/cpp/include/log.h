#ifndef APP_NATIVE_LOG_H
#define APP_NATIVE_LOG_H

#include <android/log.h>

#define TAG "jni-log"
#define debugable 1

#if debugable
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__)
#else
#define LOGI(...)
#define LOGD(...)
#define LOGW(...)
#define LOGE(...)
#define LOGF(...)
#endif

#endif //APP_NATIVE_LOG_H


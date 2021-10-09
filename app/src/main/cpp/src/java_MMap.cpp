#include "java_MMap.h"
#include "log.h"

#include <sys/mman.h>
#include <cstdio>
#include <fcntl.h>
#include <cstdlib>
#include <sys/stat.h>
#include <unistd.h>
#include <cstring>

/*
 * Class:     com_zw_new_demo1_util_MMap
 * Method:    native_read
 * Signature: (Ljava/lang/String;)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_zw_new_1demo1_util_MMap_native_1read
        (JNIEnv *env, jclass jclazz, jstring jpath) {
    return nullptr;
}

/*
 * Class:     com_zw_new_demo1_util_MMap
 * Method:    native_write
 * Signature: (Ljava/lang/String;[B)I
 */
JNIEXPORT jint JNICALL Java_com_zw_new_1demo1_util_MMap_native_1write
        (JNIEnv *env, jclass jclazz, jstring jpath, jbyteArray jbs) {
    const char *path = env->GetStringUTFChars(jpath, nullptr);
    LOGI("file path: %s\n", path);
    int fd = open(path, O_RDWR);

    if (fd < 0) {
        LOGE("Can't open %s\n", path);
        return 0;
    }

    //获取文件信息（此处获取大小信息）
    struct stat sb;
    if ((fstat(fd, &sb)) == -1) {
        LOGE("Can't file status failed\n");
        return 0;
    }

    int pageSize = getpagesize();
    LOGE("pageSize: %d", pageSize);
    if (pageSize <= 512) {
        pageSize = 1024;
    }
    //使用mmap进行映射,映射整个文件大小sb.st_size
    char *mapped = static_cast<char *>(mmap(NULL, pageSize * 4, PROT_READ | PROT_WRITE, MAP_SHARED,
                                            fd, 0));
    if (mapped == MAP_FAILED) {
        LOGE("File mmap failed\n");
        return 0;
    }

    //映射结束 关闭文件
    close(fd);
    char *str = "Hello World!";
    int len = strlen(str);
    strcpy(mapped, "Hello World!");
    strcpy(mapped + len, "Hello World!");

    env->ReleaseStringUTFChars(jpath, path);
    return 0;
}

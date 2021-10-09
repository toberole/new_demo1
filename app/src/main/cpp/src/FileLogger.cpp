#include <sys/mman.h>
#include <cstdio>
#include <fcntl.h>
#include <cstdlib>
#include <sys/stat.h>
#include <unistd.h>
#include <cstring>

#include "FileLogger.h"
#include "log.h"
#include "util.h"

FileLogger::FileLogger(std::string log_file_path)
        : log_file_path(log_file_path) {
    std::string separator = "/";
    std::string dir = "";
#ifdef WINDOWS
    separator = "\\";
    dir = log_file_path.substr(0, log_file_path.find_last_of(separator));
#else
    dir = log_file_path.substr(0, log_file_path.find_last_of(separator));
#endif
    LOGI("dir: %s", dir.c_str());

    string_replaceAll(log_file_path, "/", "\\");

    // 1.获取不带路径的文件名
    std::string::size_type iPos = log_file_path.find_last_of('\\') + 1;
    std::string filename = log_file_path.substr(iPos, log_file_path.length() - iPos);
    LOGI("filename: %s", filename.c_str());

    // 2.获取不带后缀的文件名
    std::string name = filename.substr(0, filename.rfind("."));
    LOGI("name: %s", name.c_str());

    // 3.获取后缀名
    std::string suffix_str = filename.substr(filename.find_last_of('.') + 1);
    LOGI("suffix_str: %s", suffix_str.c_str());

    temp_cache_file = dir + separator + "___" + name + "_temp_cache___.txt";
    LOGI("temp_cache_file: %s", temp_cache_file.c_str());

    FILE *fp = fopen(temp_cache_file.c_str(), "wb+");
    if (fp == NULL) {
        LOGE("文件打开失败");
        return;
    }

    int pageSize = getpagesize();
    mmap_size = pageSize * 4;
    LOGI("mmap_size: %d", mmap_size);
    fseek(fp, mmap_size - 1, SEEK_SET);
    fputc(32, fp);
    fclose(fp);

    int fd = open(temp_cache_file.c_str(), O_RDWR);
    mapped = static_cast<uint8_t *>(mmap(NULL, mmap_size, PROT_READ | PROT_WRITE, MAP_SHARED,
                                         fd, 0));
    if (mapped == MAP_FAILED) {
        LOGE("File mmap failed\n");
        return;
    }
    // 映射结束 关闭文件
    ::close(fd);
}

int FileLogger::write(uint8_t *data, size_t len) {
    if (cur_len + len >= mmap_size) {
        flushCache();
        cur_len = 0;
    }
    memcpy(mapped + cur_len, data, len);
    cur_len += len;
    return len;
}

void FileLogger::flushCache() {
    LOGI("flushCache ......");
    FILE *fp = fopen(log_file_path.c_str(), "a+");
    if (!fp) {
        LOGE("打开文件失败");
        return;
    }
    fwrite(mapped, sizeof(uint8_t), cur_len, fp);
    fclose(fp);
}

void FileLogger::close() {
    if (cur_len > 0) {
        flushCache();
    }
}

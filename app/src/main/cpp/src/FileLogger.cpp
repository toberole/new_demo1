#include <sys/mman.h>
#include <cstdio>
#include <fcntl.h>
#include <cstdlib>
#include <sys/stat.h>
#include <unistd.h>
#include <cstring>

#include "log.h"
#include "util.h"
#include "FileLogger.h"

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

    temp_cache_file = dir + separator + name + ".cache";
    LOGI("temp_cache_file: %s", temp_cache_file.c_str());
}

bool FileLogger::init() {
    FILE *fp = fopen(temp_cache_file.c_str(), "wb+");
    if (fp == NULL) {
        LOGE("fopen file fail: %s", temp_cache_file.c_str());
        return false;
    }

    int pageSize = getpagesize();
    if (pageSize <= 0) {
        pageSize = 1024;
    }
    mmap_size = pageSize * 16;
    LOGI("mmap size: %d", mmap_size);
    // 生成一个空文件
    fseek(fp, mmap_size - 1, SEEK_SET);
    // 写一个空格到文件末尾
    fputc(' ', fp);
    fclose(fp);

    int fd = open(temp_cache_file.c_str(), O_RDWR);
    mapped = static_cast<uint8_t *>(
            mmap(NULL, mmap_size,
                 PROT_READ | PROT_WRITE,
                 MAP_SHARED,
                 fd, 0)
    );

    // 映射结束 关闭文件
    ::close(fd);
    if (mapped == MAP_FAILED) {
        LOGE("file mmap failed");
        return false;
    }
    return true;
}

int FileLogger::write(uint8_t *data, size_t len) {
    if (cur_cache_len + len >= mmap_size) {
        flushCacheFile();
        cur_cache_len = 0;
    }
    memcpy(mapped + cur_cache_len, data, len);
    cur_cache_len += len;
    return len;
}

void FileLogger::close() {
    if (cur_cache_len > 0) {
        flushCacheFile();
        cur_cache_len = 0;
    }
}

void FileLogger::flushCacheFile() {
    if (cur_cache_len > 0) {
        FILE *fp = fopen(log_file_path.c_str(), "a+");
        if (!fp) {
            LOGE("打开文件失败");
            return;
        }
        fwrite(mapped, sizeof(uint8_t), cur_cache_len, fp);
        fclose(fp);
    }
}
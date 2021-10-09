#ifndef NEW_DEMO1_FILELOGGER_H
#define NEW_DEMO1_FILELOGGER_H

#include <string>
#include <stdint.h>

class FileLogger {
public:
    std::string log_file_path;
private:
    std::string temp_cache_file;
    uint8_t *mapped = nullptr;
    int32_t mmap_size = 0;
    int32_t cur_len = 0;
public:
    FileLogger(std::string file_dir);

    int read(uint8_t **buf);

    int write(uint8_t *data, size_t len);

    void flushCache();

    void close();
};


#endif //NEW_DEMO1_FILELOGGER_H

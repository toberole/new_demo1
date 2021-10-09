#include "util.h"

/**
 * 字符串替换 将str中所有的src替换为dst
 */
void string_replaceAll(std::string &str, const std::string &src, const std::string &dst) {
    std::string::size_type pos = 0;
    std::string::size_type srclen = src.size();
    std::string::size_type dstlen = dst.size();

    while ((pos = str.find(src, pos)) != std::string::npos) {
        str.replace(pos, srclen, dst);
        pos += dstlen;
    }
}
#include <sys/socket.h>
#include <string>
#include <cstdlib>
#include <unistd.h>
#include <arpa/inet.h>

#include "stream_send_receive.h"

/**
 * TCP粘包处理
 *
 * 几种解决方案：
 * 1、使用标准的应用层协议（比如：http、https）来封装要传输的不定长的数据包
 * 2、在每条数据的尾部添加特殊字符，如果遇到特殊字符，代表当条数据接收完毕了
 *  有缺陷：效率低，需要一个字节一个字节接收，接收一个字节判断一次，判断是不是那个特殊字符串
 * 3、在发送数据块之前，在数据块最前边添加一个固定大小的数据头，这时候数据由两部分组成：数据头 + 数据块
 *  数据头：存储当前数据包的总字节数，接收端先接收数据头，然后在根据数据头接收对应大小的字节
 *  数据块：当前数据包的内容
 *
 *  完整数据包=包头四子节长度[标识包体长度] + 包体
 */

/**
 * 发送端 对于发送端来说，数据的发送分为 4 步：
 * 1、根据待发送的数据长度 N 动态申请一块固定大小的内存：N+4（4 是包头占用的字节数）
 * 2、将待发送数据的总长度写入申请的内存的前四个字节中，此处需要将其转换为网络字节序（大端）
 * 3、将待发送的数据拷贝到包头后边的地址空间中，将完整的数据包发送出去（字符串没有字节序问题）
 * 4、释放申请的堆内存。
 * 由于发送端每次都需要将这个数据包完整的发送出去，因此可以设计一个发送函数，如果当前数据包中的数据没有发送完就让它一直发送
 */
/*
函数描述: 发送指定的字节数
函数参数:
    - fd: 通信的文件描述符(套接字)
    - msg: 待发送的原始数据
    - size: 待发送的原始数据的总字节数
函数返回值: 函数调用成功返回发送的字节数, 发送失败返回-1
*/
int writen(int fd, const char *msg, int size) {
    const char *buf = msg;
    int count = size;
    while (count > 0) {
        int len = send(fd, buf, count, 0);
        if (len == -1) {
            close(fd);
            return -1;
        } else if (len == 0) {
            continue;
        }
        buf += len;
        count -= len;
    }
    return size;
}

/*
函数描述: 发送带有数据头的数据包
函数参数:
    - cfd: 通信的文件描述符(套接字)
    - msg: 待发送的原始数据
    - len: 待发送的原始数据的总字节数
函数返回值: 函数调用成功返回发送的字节数, 发送失败返回-1
*/
int sendMsg(int cfd, char *msg, int len) {
    if (msg == NULL || len <= 0 || cfd <= 0) {
        return -1;
    }
    // 申请内存空间: 数据长度 + 包头4字节(存储数据长度)
    char *data = (char *) malloc(len + 4);
    // 字符串没有字节序问题，但是数据头不是字符串是整形，因此需要从主机字节序转换为网络字节序再发送。
    int bigLen = htonl(len);
    memcpy(data, &bigLen, 4);
    memcpy(data + 4, msg, len);
    // 发送数据
    int ret = writen(cfd, data, len + 4);
    // 释放内存
    free(data);
    return ret;
}

/**
 * 接收端 具体过程如下：
 * 1、首先接收 4 字节数据，并将其从网络字节序转换为主机字节序，这样就得到了即将要接收的数据的总长度
 * 2、根据得到的长度申请固定大小的堆内存，用于存储待接收的数据
 * 3、根据得到的数据块长度接收固定数目的数据保存到申请的堆内存中
 * 4、处理接收的数据
 * 释放存储数据的堆内存
 **/

/*
函数描述: 接收指定的字节数
函数参数:
    - fd: 通信的文件描述符(套接字)
    - buf: 存储待接收数据的内存的起始地址
    - size: 指定要接收的字节数
函数返回值: 函数调用成功返回发送的字节数, 发送失败返回-1
*/
int readn(int fd, char *buf, int size) {
    char *pt = buf;
    int count = size;
    while (count > 0) {
        int len = recv(fd, pt, count, 0);
        if (len == -1) {
            return -1;
        } else if (len == 0) {
            return size - count;
        }
        pt += len;
        count -= len;
    }
    return size;
}

/*
函数描述: 接收带数据头的数据包
函数参数:
    - cfd: 通信的文件描述符(套接字)
    - msg: 一级指针的地址，函数内部会给这个指针分配内存，用于存储待接收的数据，这块内存需要使用者释放
函数返回值: 函数调用成功返回接收的字节数, 发送失败返回-1
*/
int recvMsg(int cfd, char **msg) {
    // 接收数据
    // 1. 读数据头
    int len = 0;
    readn(cfd, (char *) &len, 4);
    len = ntohl(len);
    printf("数据块大小: %d\n", len);

    // 根据读出的长度分配内存，+1 -> 这个字节存储\0
    char *buf = (char *) malloc(len + 1);
    int ret = readn(cfd, buf, len);
    if (ret != len) {
        close(cfd);
        free(buf);
        return -1;
    }
    buf[len] = '\0';
    *msg = buf;

    return ret;
}
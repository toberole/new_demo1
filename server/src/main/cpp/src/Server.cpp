#include "Server.h"
#include <memory>
#include <endian.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <thread>

#define INADDR_ANY 0

Server::Server(int port) : port(port) {

}

int Server::start() {
    // 设置本地地址结构体
    struct sockaddr_in my_addr;
    bzero(&my_addr, sizeof(my_addr));           // 清空,保证最后8字节为0
    my_addr.sin_family = AF_INET;               // ipv4
    my_addr.sin_port = htons(port);           // 端口
    my_addr.sin_addr.s_addr = htonl(INADDR_ANY);// ip，INADDR_ANY为通配地址其值为0

    // 绑定
    int err_log = bind(server_fd, (struct sockaddr *) &my_addr, sizeof(my_addr));
    if (err_log != 0) {
        perror("binding");
        close(server_fd);
        exit(-1);
    }

    std::thread();

    char buff[1024] = {0};
    while (1) {
        int connfd = 0;
        if ((connfd = accept(server_fd, (struct sockaddr *) NULL, NULL)) == -1) {
            printf("accept socket error: %s(errno: %d)", strerror(errno), errno);
            continue;
        }
        int n = recv(connfd, buff, 1024, 0);
        buff[n] = '\0';
        printf("recv msg from client: %s\n", buff);
        close(connfd);
    }
}

void Server::stop() {

}

Server::~Server() {

}
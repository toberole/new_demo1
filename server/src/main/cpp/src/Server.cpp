#include "Server.h"
#include <memory>
#include <endian.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <thread>

Server::Server(int port) : port(port) {

}

int Server::start() {
    // socket(int __af, int __type, int __protocol)
   socket()
}

void Server::stop() {
    isStop = 1;
}

Server::~Server() {
    isStop = 1;
}
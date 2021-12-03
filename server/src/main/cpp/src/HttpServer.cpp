#include "HttpServer.h"
#include "log.h"

HttpServer::HttpServer(int port) {
    this->server_ptr = std::make_shared<Server>(port);
}

int HttpServer::start() {
    return 0;
}

void HttpServer::stop() {

}

HttpServer::~HttpServer() {
    LOGI("~HttpServer()");
}
#ifndef NEW_DEMO1_HTTPSERVER_H
#define NEW_DEMO1_HTTPSERVER_H

#include <memory>
#include "Server.h"

class HttpServer {
private:
    std::shared_ptr<Server> server_ptr;
public:
    HttpServer(int port);

    ~HttpServer();

    int start();

    void stop();
};


#endif //NEW_DEMO1_HTTPSERVER_H

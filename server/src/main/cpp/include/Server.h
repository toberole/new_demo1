#ifndef NEW_DEMO1_SERVER_H
#define NEW_DEMO1_SERVER_H

class Server {
private:
    int port;
    int server_fd;
public:
    Server(int port);

    ~Server();

    int start();

    void stop();
};


#endif //NEW_DEMO1_SERVER_H

#ifndef NEW_DEMO1_SERVER_H
#define NEW_DEMO1_SERVER_H

class Server {
private:
    int port = 0;
    int server_fd = 0;
    int isStop = 0;
public:
    Server(int port);

    ~Server();

    int start();

    void stop();
};


#endif //NEW_DEMO1_SERVER_H

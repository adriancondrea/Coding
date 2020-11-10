#ifdef _WIN32
#define _WINSOCK_DEPRECATED_NO_WARNINGS
#endif

// exists on all platforms
#include <stdio.h>

// this section will only be compiled on NON Windows platforms

#ifndef _WIN32
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
// for inet_ntoa
#include <arpa/inet.h>
// for close
#include <unistd.h> 
#define closesocket close
typedef int SOCKET;
#else

// on Windows include and link these things
#include<WinSock2.h>
// for uint16_t 
#include<cstdint>
// for inet_ntoa
#include<wsipv6ok.h>
// this is how we can link a library directly from the source code with the VC++ compiler �
// otherwise got o project settings and link to it explicitly
#pragma comment(lib,"Ws2_32.lib")

typedef int socklen_t;
#endif

bool belongs(uint16_t value, uint16_t arraySize, uint16_t array[]){
    for(uint16_t i = 0 ; i < arraySize; i++)
        if(array[i] == value)
            return true;
    return false;
}

int main() {

    SOCKET s;
    struct sockaddr_in server, client;
    int c, l, err;

    // initialize the Windows Sockets LIbrary only when compiled on Windows
#ifdef WIN32
    WSADATA wsaData;
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) < 0) {
        printf("Error initializing the Windows Sockets LIbrary");
        return -1;
    }
#endif
    //  AF_INET - communication domain
    // SOCK_STREAM - communication type
    // 0 - protocol (IP)
    s = socket(AF_INET, SOCK_STREAM, 0);

    if (s < 0) {
        printf("Error at creating server socket!\n");
        return 1;
    }

    memset(&server, 0, sizeof(server));
    server.sin_port = htons(1234);
    server.sin_family = AF_INET;
    // INADDR_ANY, as defined in netinet/in.h, 
    // the caller is requesting that the socket be bound to all network interfaces on the host.
    server.sin_addr.s_addr = INADDR_ANY;

    // bind() assigns the address specified by server to the socket referred to by the file descriptor s
    if (bind(s, (struct sockaddr*) & server, sizeof(server)) < 0) {
        perror("Bind error:");
        return 1;
    }

    // puts the server socket in a passive mode, 
    // where it waits for the client to approach the server to make a connection.
    listen(s, 5);
    l = sizeof(client);
    memset(&client, 0, sizeof(client));

    while (1) {

        uint16_t size1, array1[100], size2, array2[100], size3, array3[100];
        printf("Listening for incomming connections\n");
        // first connection request on the queue of pending connections for the listening socket, s, 
        // creates a new connected socket c, and returns a new file descriptor referring to that socket.
        c = accept(s, (struct sockaddr*) & client, (socklen_t*)&l);

        err = errno;
#ifdef WIN32
        err = WSAGetLastError();
#endif

        if (c < 0) {
            printf("accept error: %d", err);
            continue;
        }

        // converts the Internet host address , given in network byte order, to a string in IPv4 dotted-decimal notation.
        printf("Incomming connected client from: %s:%d\n", 
            inet_ntoa(client.sin_addr), ntohs(client.sin_port));

        // read the size of array1
        int res = recv(c, (char*)&size1, sizeof(size1), 0);
        //check we got an unsigned short value
        if (res != sizeof(size1)) 
            printf("Error receiving array1 size!\n");

        //decode the value to the local representation
        printf("size1 = %hu\n", size1);
        size1 = ntohs(size1);
        printf("size1 after decoding = %hu\n", size1);

        //read the elements of array1
        for(uint16_t i = 0; i < size1; i++){
            res = recv(c, (char*)&array1[i], sizeof(array1[i]), 0);
            if(res != sizeof(array1[i]))
                printf("Error receiving array1 elements!\n");
            array1[i] = ntohs(array1[i]);
        }

        // read the size of array2
        res = recv(c, (char*)&size2, sizeof(size2), 0);
        //check we got an unsigned short value
        if (res != sizeof(size2)) 
            printf("Error receiving array2 size!\n");

        //decode the value to the local representation
        size2 = ntohs(size2);
        
        //read the elements of array2
        for(uint16_t i = 0; i < size2; i++){
            res = recv(c, (char*)&array2[i], sizeof(array2[i]), 0);
            if(res != sizeof(array2[i]))
                printf("Error receiving elements of array2!\n");
            array2[i] = ntohs(array2[i]);
        }
        
        size3 = 0;
        for(uint16_t i=0; i < size1; i++){
            if(!belongs(array1[i], size2, array2))
                array3[size3++] = array1[i];
        }

        size3 = htons(size3);
        res = send(c, (char*)&size3, sizeof(size3), 0);
        if(res != sizeof(size3))
            printf("Error sending the number of elements!\n");
        size3 = ntohs(size3);

        for(uint16_t i = 0; i < size3; i++){
            array3[i] = htons(array3[i]);
            res = send(c, (char*)&array3[i], sizeof(array3[i]), 0);
            if(res != sizeof(array3[i]))
                printf("Error sending element\n!");
        }
        //on Linux closesocket does not exist but was defined above as a define to close
        closesocket(c);

    }


#ifdef WIN32
    // Release the Windows Sockets Library
    WSACleanup();
#endif

}


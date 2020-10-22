
#include <stdio.h>
#ifndef _WIN32

#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
// for inet_ntoa
#include <arpa/inet.h>
// for close
#include <unistd.h> 
#define closesocket close
typedef int SOCKET;
#else
#define _WINSOCK_DEPRECATED_NO_WARNINGS
// on Windows include and link these things
#include<WinSock2.h>
// for uint16_t 
#include<cstdint>
// for inet_ntoa
#include<wsipv6ok.h>
// this is how we can link a library directly from the source code with the VC++ compiler �
// otherwise got o project settings and link to it explicitly
#pragma comment(lib,"Ws2_32.lib")

#endif

#include <string.h>


int main() {

// initialize the Windows Sockets Library only when compiled on Windows
#ifdef WIN32
    WSADATA wsaData;
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) < 0) {
        printf("Error initializing the Windows Sockets Library");
        return -1;
    }
#endif

    int c;

   
    uint16_t number, divisors[100], nrDivisors = 0;

    //  AF_INET - communication domain
    // SOCK_STREAM - communication type
    // 0 - protocol (IP)
    c = socket(AF_INET, SOCK_STREAM, 0);
    if (c < 0) {
        printf("Error at creating client socket!\n");
        return 1;
    }


    struct sockaddr_in server;
    memset(&server, 0, sizeof(server));

    server.sin_port = htons(1234);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = inet_addr("192.168.100.4");



    if (connect(c, (struct sockaddr*) & server, sizeof(server)) < 0) {
        printf("Error at connecting to the server!\n");
        return 1;
    }


    printf("number = ");
#ifdef WIN32
    scanf_s("%hu", &number);
#else
    scanf("%hu", &number);
#endif

    number = htons(number);

    send(c, (char*)&number, sizeof(number), 0);

    recv(c, (char*)&nrDivisors, sizeof(nrDivisors), 0);
    nrDivisors = ntohs(nrDivisors);
    for(uint16_t i = 0; i < nrDivisors; i++) {
        recv(c, (char*)&divisors[i], sizeof(divisors[i]), 0);
        divisors[i] = ntohs(divisors[i]);
    }
    printf("number of divisors: %hu\n", nrDivisors);
    for(uint16_t i = 0; i < nrDivisors; i++)
        printf("%hu\n", divisors[i]);
    getchar();
    getchar();

#ifdef WIN32
    // Release the Windows Sockets Library
    WSACleanup();
    closesocket(c);
#else
    close(c);
#endif
}




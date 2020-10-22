
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

   
    uint16_t size1, array1[100], size2, array2[100], size3, array3[100];

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


    printf("size1 = ");
#ifdef WIN32
    scanf_s("%hu", &size1);
#else
    scanf("%hu", &size1);
#endif
    for(uint16_t i = 0; i < size1; i++){
    #ifdef WIN32
        scanf_s("%hu", &array1[i]);
    #else
        scanf("%hu", &array1[i]);
    #endif
    }

    printf("size2 = ");
#ifdef WIN32
    scanf_s("%hu", &size2);
#else
    scanf("%hu", &size2);
#endif
    for(uint16_t i = 0; i < size2; i++){
    #ifdef WIN32
        scanf_s("%hu", &array2[i]);
    #else
        scanf("%hu", &array2[i]);
    #endif
    }

    //sending first array size, then its elements
    size1 = htons(size1);
    send(c, (char*)&size1, sizeof(size1), 0);
    //convert size1 back to host
    size1 = ntohs(size1);
    for(uint16_t i = 0; i < size1; i++){
        array1[i] = htons(array1[i]);
        send(c, (char*)&array1[i], sizeof(array1[i]), 0);
        array1[i] = ntohs(array1[i]);
    }

    //sending second array size, then its elements
    size2 = htons(size2);
    send(c, (char*)&size2, sizeof(size2), 0);
    size2 = ntohs(size2);
    for(uint16_t i = 0; i < size2; i++){
        array2[i] = htons(array2[i]);
        send(c, (char*)&array2[i], sizeof(array2[i]), 0);
        array2[i] = ntohs(array2[i]);
    }

    //receive array3 size, then its elements
    recv(c, (char*)&size3, sizeof(size3), 0);
    size3 = ntohs(size3);
    for(uint16_t i = 0; i < size3; i++) {
        recv(c, (char*)&array3[i], sizeof(array3[i]), 0);
        array3[i] = ntohs(array3[i]);
    }

    printf("number of elements of the difference: %hu\n", size3);
    for(uint16_t i = 0; i < size3; i++)
        printf("%hu\n", array3[i]);
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




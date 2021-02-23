"""4.   The clients send an integer number N and an array of N float values. The server will merge sort the numbers
received from all clients until it gets an empty array of floats (N=0). The server returns to each client the size of
the merge-sorted array followed by the merge-sorted arrays of all floats from all clients. """

__author__ = 'adi'

import random
import socket
import struct
import time

if __name__ == '__main__':
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket.error as msg:
        print("Error: ", msg.strerror)
        exit(-1)

    data = s.recv(1024)
    print(data.decode('ascii'))
    try:
        n = int(input('n= '))
        s.sendall(struct.pack('!i', n))
        for i in range(n):
            num = float(input('num= '))
            s.sendall(struct.pack('!f', num))

        finalArraySize = s.recv(4)
        finalArraySize = struct.unpack('!i', finalArraySize)[0]
        print('final array size: ' + str(finalArraySize))
        for e in range(finalArraySize):
            element = s.recv(4)
            element = struct.unpack('!f', element)[0]
            print(element, sep=', ')
    except Exception as ex:
        print(ex)
    s.close()
